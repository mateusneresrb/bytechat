package dev.mateusneres.bytechat.client.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServerStatus {

    private String name;
    private int port;
    private int onlineUsers;
    private int totalUsers;

}
