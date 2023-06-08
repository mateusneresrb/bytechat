package dev.mateusneres.bytechat.server.model;

import lombok.Data;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.UUID;

@Data
public class User {

    private UUID userID;
    private String name;
    private String avatarName;
    private Socket socket;
    private DataOutputStream output;

    public User(Socket socket) {
        this.userID = UUID.randomUUID();
        this.socket = socket;
    }

}
