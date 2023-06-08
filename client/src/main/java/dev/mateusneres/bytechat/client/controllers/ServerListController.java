package dev.mateusneres.bytechat.client.controllers;

import dev.mateusneres.bytechat.client.models.ServerStatus;
import dev.mateusneres.bytechat.client.utils.ServerScanner;
import dev.mateusneres.bytechat.client.views.screens.HomeScreen;
import dev.mateusneres.bytechat.client.views.screens.ServerListScreen;
import dev.mateusneres.bytechat.common.model.UserInfo;

import javax.swing.*;
import java.util.List;

public class ServerListController {

    private final UserInfo userInfo;
    private final List<ServerStatus> serverStatusList;
    private final ServerListScreen serverListScreen;

    public ServerListController(ServerListScreen serverListScreen, UserInfo userInfo) {
        this.userInfo = userInfo;
        this.serverListScreen = serverListScreen;
        this.serverStatusList = ServerScanner.getAvailableServers();

        loadServerList();
        handleListeners();
    }

    private void loadServerList() {
        int count = 0;
        for (ServerStatus serverStatus : serverStatusList) {
            count++;

            String serverName = serverStatus.getName();
            int serverPort = serverStatus.getPort();
            int onlineUsers = serverStatus.getOnlineUsers();
            int totalUsers = serverStatus.getTotalUsers();

            serverListScreen.getListModel().addElement(count + ". " + serverName + " (" + onlineUsers + "/" + totalUsers + ") - IP: 127.0.0.1:" + serverPort);
        }
    }

    private void handleListeners() {
        onClickServerEvent();
    }

    public void onClickServerEvent() {
        serverListScreen.getButton().addActionListener(e -> {
            String selectedItem = serverListScreen.getJList().getSelectedValue();

            if (selectedItem == null || selectedItem.isEmpty()) {
                JOptionPane.showMessageDialog(serverListScreen.getJList(), "Please, select a server!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String serverName = selectedItem.split("\\(")[0].split("\\.")[1].substring(1);
            int serverPort = Integer.parseInt(selectedItem.split(" - IP: ")[1].split(":")[1]);
            ServerStatus serverStatus = serverStatusList.stream().filter(server -> server.getPort() == serverPort).findFirst().orElse(null);
            if (serverStatus == null) {
                JOptionPane.showMessageDialog(serverListScreen.getJList(), "Server not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            serverListScreen.dispose();
            HomeScreen homeScreen = new HomeScreen(serverName);
            new HomeController(new ClientController(serverStatus, userInfo), homeScreen);
        });
    }
}
