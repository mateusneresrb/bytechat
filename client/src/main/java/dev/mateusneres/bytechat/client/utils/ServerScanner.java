package dev.mateusneres.bytechat.client.utils;

import com.google.gson.Gson;
import dev.mateusneres.bytechat.client.models.ServerStatus;
import dev.mateusneres.bytechat.common.enums.ConnectionType;
import dev.mateusneres.bytechat.common.model.Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ServerScanner {

    public static List<ServerStatus> getAvailableServers() {
        int initialPort = 52930;
        int numPortsToCheck = 10;

        List<ServerStatus> serverStatusList = new ArrayList<>();
        for (int i = 0; i <= numPortsToCheck; i++) {
            ServerStatus serverStatus = pingServer(initialPort);
            if (serverStatus != null) {
                serverStatusList.add(serverStatus);
            }

            initialPort++;
        }

        return serverStatusList;
    }

    public static ServerStatus pingServer(int port) {
        try (Socket socket = new Socket("127.0.0.1", port);
             DataInputStream receiver = new DataInputStream(socket.getInputStream());
             DataOutputStream sender = new DataOutputStream(socket.getOutputStream())) {

            sender.writeUTF(new Gson().toJson(new Connection(ConnectionType.PING)));
            return new Gson().fromJson(receiver.readUTF(), ServerStatus.class);
        } catch (IOException e) {
            Logger.getGlobal().warning("Error while pinging server in port: " + port + " -> " + e.getMessage());
        }

        return null;
    }

}


