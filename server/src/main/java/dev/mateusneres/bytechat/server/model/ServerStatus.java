package dev.mateusneres.bytechat.server.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServerStatus {
    
    private String name;
    private int port;
    private int onlineUsers;
    private int totalUsers;

}
