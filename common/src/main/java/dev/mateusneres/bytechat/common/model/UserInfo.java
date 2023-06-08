package dev.mateusneres.bytechat.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
public class UserInfo {

    private UUID userID;
    private final String name;
    private String avatar;
    private boolean online;
    private List<Message> messages;

    public UserInfo(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
        this.messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

}
