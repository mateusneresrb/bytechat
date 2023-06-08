package dev.mateusneres.bytechat.server.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.net.ServerSocket;

@RequiredArgsConstructor
@Data
public class ServerInfo {

    private final String name;
    private final int port;
    private ServerSocket serverSocket;

}
