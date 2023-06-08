package dev.mateusneres.bytechat.client.threads;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.mateusneres.bytechat.client.controllers.ClientController;
import dev.mateusneres.bytechat.client.controllers.HomeController;
import dev.mateusneres.bytechat.common.enums.ConnectionType;
import dev.mateusneres.bytechat.common.enums.MessageType;
import dev.mateusneres.bytechat.common.model.Connection;
import dev.mateusneres.bytechat.common.model.Message;
import dev.mateusneres.bytechat.common.model.Payload;
import dev.mateusneres.bytechat.common.model.UserInfo;
import dev.mateusneres.bytechat.common.utils.GsonUtil;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ClientThread implements Runnable {

    private final HomeController homeController;
    private final ClientController clientController;
    private boolean running = true;

    @Override
    public void run() {
        try (Socket socket = new Socket("127.0.0.1", clientController.getServerInfo().getPort());
             DataInputStream receiver = new DataInputStream(socket.getInputStream());
             DataOutputStream sender = new DataOutputStream(socket.getOutputStream())) {
            clientController.setUserDataOutput(sender);

            if (!connectInServer(sender, receiver)) {
                JOptionPane.showMessageDialog(null, "Error on validade connection in server!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            while (running) {
                handleMessages(receiver);
            }

        } catch (IOException e) {
            if (e instanceof EOFException) {
                JOptionPane.showMessageDialog(homeController.getHomeScreen(), "Connection closed!", "Error", JOptionPane.ERROR_MESSAGE);
                homeController.getHomeScreen().dispose();
                return;
            }

            e.printStackTrace();
        }
    }

    private boolean connectInServer(DataOutputStream dataOutputStream, DataInputStream dataInputStream) throws IOException {
        Connection connection = new Connection(clientController.getUserInfo().getName(), clientController.getUserInfo().getAvatar(), ConnectionType.IDENTIFICATION);
        dataOutputStream.writeUTF(new Gson().toJson(connection));

        Gson gson = GsonUtil.getBuilderList();
        Payload<List<UserInfo>> payload = gson.fromJson(dataInputStream.readUTF(), new TypeToken<Payload<List<UserInfo>>>() {
        }.getType());
        if (payload.getMessageType() != MessageType.USER_LIST) return false;
        updateUserList(payload.getContent());
        return true;
    }

    private void handleMessages(DataInputStream dataInputStream) throws IOException {
        Gson gson = GsonUtil.getBuilderList();
        String message = dataInputStream.readUTF();

        Payload<?> payload = gson.fromJson(message, new TypeToken<Payload<?>>() {
        }.getType());

        Object content = payload.getContent();
        if (payload.getMessageType() == MessageType.USER_LIST && content instanceof List<?>) {
            Type listType = new TypeToken<List<UserInfo>>() {
            }.getType();
            List<UserInfo> userList = gson.fromJson(gson.toJson(content), listType);
            updateUserList(userList);
            return;
        }

        if (payload.getMessageType() == MessageType.MESSAGE) {
            Message messageData = gson.fromJson(gson.toJson(content), Message.class);
            clientController.addUsersMessages(messageData);
            homeController.updateMessageList();
        }

        if (payload.getMessageType() == MessageType.NOTIFICATION) {
            String notification = gson.fromJson(gson.toJson(content), String.class);
            JOptionPane.showMessageDialog(homeController.getHomeScreen(), notification, "Notification", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void updateUserList(List<UserInfo> userInfoList) {
        userInfoList.forEach(userInfo -> {
            if (clientController.getUserInfo().getUserID() == null && userInfo.getName().equals(clientController.getUserInfo().getName())) {
                clientController.getUserInfo().setUserID(userInfo.getUserID());
            }

            if (clientController.getUserInfo(userInfo.getUserID()) == null) {
                userInfo.setMessages(new ArrayList<>());
                clientController.getUsersList().add(userInfo);
                return;
            }

            clientController.getUserInfo(userInfo.getUserID()).setAvatar(userInfo.getAvatar());
            clientController.getUserInfo(userInfo.getUserID()).setOnline(userInfo.isOnline());
        });

        homeController.updateUsersList();
    }

}
