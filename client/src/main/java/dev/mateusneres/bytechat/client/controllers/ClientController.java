package dev.mateusneres.bytechat.client.controllers;

import com.google.gson.Gson;
import dev.mateusneres.bytechat.client.models.ServerStatus;
import dev.mateusneres.bytechat.client.threads.ClientThread;
import dev.mateusneres.bytechat.common.enums.MessageType;
import dev.mateusneres.bytechat.common.model.Message;
import dev.mateusneres.bytechat.common.model.Payload;
import dev.mateusneres.bytechat.common.model.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Getter
public class ClientController {

    private final ServerStatus serverInfo;
    private final UserInfo userInfo;
    private final List<UserInfo> usersList;
    @Setter
    private DataOutputStream userDataOutput;

    public ClientController(ServerStatus serverStatus, UserInfo userInfo) {
        this.serverInfo = serverStatus;
        this.userInfo = userInfo;
        this.usersList = new ArrayList<>();
    }

    public void initClient(HomeController homeController) {
        Thread thread = new Thread(new ClientThread(homeController, this));
        thread.start();
    }

    public UserInfo getUserInfo(UUID userID) {
        return usersList.stream().filter(user -> user.getUserID().equals(userID)).findFirst().orElse(null);
    }

    public void addUsersMessages(Message message) {
        UserInfo receiverUser = getUserInfo(message.getReceiverUserID());
        UserInfo senderUser = getUserInfo(message.getSenderUserID());
        if (receiverUser != null && senderUser != null) {
            receiverUser.getMessages().add(message);
            senderUser.getMessages().add(message);
        }
    }

    public List<Message> getMessages(UUID userID, UUID targetUserID) {
        UserInfo user = getUserInfo(userID);
        return new ArrayList<>(user.getMessages().stream().filter(message -> message.getSenderUserID().equals(targetUserID) || message.getReceiverUserID().equals(targetUserID)).collect(Collectors.toList()));
    }

    public void sendMessageTo(UUID uuid, String content) {
        Message message = new Message(userInfo.getUserID(), uuid, content, Instant.now());
        addUsersMessages(message);

        Payload<?> payload = new Payload<>(MessageType.MESSAGE, message);

        try {
            userDataOutput.writeUTF(new Gson().toJson(payload));
        } catch (IOException e) {
            Logger.getGlobal().severe("Error on send server message: " + e.getCause());
        }
    }

}
