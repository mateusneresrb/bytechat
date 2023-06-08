package dev.mateusneres.bytechat.server.threads;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.mateusneres.bytechat.common.enums.ConnectionType;
import dev.mateusneres.bytechat.common.enums.MessageType;
import dev.mateusneres.bytechat.common.model.Connection;
import dev.mateusneres.bytechat.common.model.Message;
import dev.mateusneres.bytechat.common.model.Payload;
import dev.mateusneres.bytechat.common.model.UserInfo;
import dev.mateusneres.bytechat.common.utils.GsonUtil;
import dev.mateusneres.bytechat.server.controller.ServerConsoleController;
import dev.mateusneres.bytechat.server.controller.ServerController;
import dev.mateusneres.bytechat.server.model.ServerStatus;
import dev.mateusneres.bytechat.server.model.User;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class ServerClientThread implements Runnable {

    private final User user;
    private final ServerConsoleController serverConsoleController;
    private final ServerController serverController;
    private boolean running = true;

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(user.getSocket().getInputStream());
             DataOutputStream output = new DataOutputStream(user.getSocket().getOutputStream())) {
            user.setOutput(output);

            while (running) {
                handleClientMessage(input, output);
            }

        } catch (IOException e) {
            /* CONNECTION TIMEOUT */
            if (e instanceof SocketTimeoutException) {
                closeClientConnection();
                serverController.disconnectUser(user);
                return;
            }

            /* CLOSED CONNECTION */
            if (e instanceof EOFException) {
                running = false;
                serverController.disconnectUser(user);
                if (user.getUserID() != null)
                    sendConsoleMessage("User " + user.getName() + " disconnected!", Color.RED);
                return;
            }

            e.printStackTrace();
        }
    }

    private void handleClientMessage(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        String message = dataInputStream.readUTF();
        if (message.isEmpty()) return;

        if (isIdentification()) {
            Connection connection = new Gson().fromJson(message, Connection.class);

            if (connection.getMessageType() == ConnectionType.PING) {
                serverController.sendClientMessage(dataOutputStream, new Gson().toJson(new ServerStatus(
                        serverController.getServerInfo().getName(), serverController.getServerInfo().getPort(),
                        serverController.getOnlineUsers(), serverController.getUserList().size())));

                closeClientConnection();
                return;
            }

            user.getSocket().setSoTimeout(0);
            user.setName(connection.getName());
            user.setAvatarName(connection.getAvatar());

            Optional<User> optionalUser = serverController.getUserList().stream().filter(user1 -> user1.getName().equalsIgnoreCase(connection.getName())).findFirst();
            optionalUser.ifPresent(value -> user.setUserID(value.getUserID()));

            serverController.onlineUser(user);
            Gson gson = GsonUtil.getBuilderList();
            Payload<List<UserInfo>> payload = new Payload<>(MessageType.USER_LIST, serverController.getUserInfoList());
            serverController.sendClientMessage(dataOutputStream, gson.toJson(payload));
            sendConsoleMessage("User " + user.getName() + " connected!", Color.GREEN);
            return;
        }

        Gson gson = GsonUtil.getBuilderList();
        Payload<Message> payload = gson.fromJson(message, new TypeToken<Payload<Message>>() {
        }.getType());

        Object content = payload.getContent();
        if (payload.getMessageType() == MessageType.MESSAGE) {
            Message messageData = gson.fromJson(gson.toJson(content), Message.class);
            serverController.sendMessageToUser(messageData);
        }
    }

    private void closeClientConnection() {
        try {
            running = false;
            user.getSocket().close();
        } catch (IOException e) {
            Logger.getGlobal().severe("Error on close client connection: " + e.getMessage());
        }
    }

    private void sendConsoleMessage(String message, Color color) {
        serverConsoleController.getServerConsole().printMessage(message, color);
    }

    private boolean isIdentification() throws SocketException {
        return user.getSocket().getSoTimeout() > 0;
    }


}
