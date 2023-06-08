package dev.mateusneres.bytechat.server.controller;

import com.google.gson.Gson;
import dev.mateusneres.bytechat.common.enums.MessageType;
import dev.mateusneres.bytechat.common.model.Message;
import dev.mateusneres.bytechat.common.model.Payload;
import dev.mateusneres.bytechat.common.model.UserInfo;
import dev.mateusneres.bytechat.common.utils.GsonUtil;
import dev.mateusneres.bytechat.server.model.ServerInfo;
import dev.mateusneres.bytechat.server.model.User;
import dev.mateusneres.bytechat.server.threads.ServerThread;
import lombok.Data;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Data
public class ServerController {

    private final ServerInfo serverInfo;
    private List<User> userList;
    private List<Message> tempMessages;

    public ServerController(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
        this.userList = new ArrayList<>();
        this.tempMessages = new ArrayList<>();
    }

    public void initServer(ServerConsoleController serverConsoleController) {
        Thread thread = new Thread(new ServerThread(serverConsoleController, this));
        thread.start();
    }

    public List<UserInfo> getUserInfoList() {
        List<UserInfo> userInfoList = new ArrayList<>();
        userList.forEach(user -> userInfoList.add(new UserInfo(user.getUserID(), user.getName(), user.getAvatarName(), user.getSocket() != null, new ArrayList<>())));
        return userInfoList;
    }

    public int getOnlineUsers() {
        return userList.stream().filter(user -> user.getSocket() != null).toArray().length;
    }

    public void onlineUser(User user) {
        if (userList.stream().noneMatch(user1 -> user1.getUserID().equals(user.getUserID()))) {
            userList.add(user);
        }
        updateUserList(user);
    }

    public void disconnectUser(User user) {
        userList.stream().filter(user1 -> user1.getUserID() == user.getUserID()).forEach(user1 -> user1.setSocket(null));
        updateUserList(user);
    }

    public void updateUserList(User user) {
        userList.stream().filter(user1 -> user1.getUserID() != user.getUserID()).forEach(this::updateUsersInfo);
    }

    public void sendMessageToUser(Message message) {
        User user = userList.stream().filter(user1 -> user1.getUserID().equals(message.getReceiverUserID())).findFirst().orElse(null);
        if (user == null) return;

        if (user.getSocket() == null) {
            tempMessages.add(message);
            return;
        }

        Payload<?> payload = new Payload<>(MessageType.MESSAGE, message);

        try {
            sendClientMessage(user.getOutput(), new Gson().toJson(payload));
        } catch (IOException e) {
            Logger.getGlobal().severe("Error on send client message: " + e.getMessage());
        }
    }

    private void updateUsersInfo(User user) {
        Gson gson = GsonUtil.getBuilderList();
        Payload<?> payload = new Payload<>(MessageType.USER_LIST, getUserInfoList());

        try {
            sendClientMessage(user.getOutput(), gson.toJson(payload));
        } catch (IOException e) {
            Logger.getGlobal().severe("Error on send client message: " + e.getMessage());
        }
    }

    public void sendClientMessage(DataOutputStream dataOutputStream, String message) throws IOException {
        dataOutputStream.writeUTF(message);
    }

}
