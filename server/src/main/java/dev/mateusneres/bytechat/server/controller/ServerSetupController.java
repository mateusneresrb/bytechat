package dev.mateusneres.bytechat.server.controller;

import dev.mateusneres.bytechat.common.utils.PortUtil;
import dev.mateusneres.bytechat.server.model.ServerInfo;
import dev.mateusneres.bytechat.server.view.ServerConsole;
import dev.mateusneres.bytechat.server.view.ServerSetup;

import javax.swing.*;

public class ServerSetupController {

    private final ServerSetup serverSetup;

    public ServerSetupController(ServerSetup serverSetup) {
        this.serverSetup = serverSetup;
        handleListeners();
    }

    private void handleListeners() {
        onCreateServerEvent();
    }

    private void onCreateServerEvent() {
        serverSetup.getButton().addActionListener(e -> {
            String serverName = serverSetup.getTextField().getText();
            int availablePort = PortUtil.getAvailablePort();

            if(serverName.isEmpty()){
                JOptionPane.showMessageDialog(serverSetup.getTextField(), "Server name is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (serverName.length() >= 25) {
                JOptionPane.showMessageDialog(serverSetup.getTextField(), "Server name is too long! Max.: 25 chars", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (availablePort == -1) {
                JOptionPane.showMessageDialog(serverSetup.getTextField(), "Not available port found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            serverSetup.dispose();
            ServerConsole serverConsole = new ServerConsole(serverName);
            ServerController serverController = new ServerController(new ServerInfo(serverName, availablePort));

            ServerConsoleController serverConsoleController = new ServerConsoleController(serverConsole, serverController);
            serverController.initServer(serverConsoleController);
        });
    }


}
