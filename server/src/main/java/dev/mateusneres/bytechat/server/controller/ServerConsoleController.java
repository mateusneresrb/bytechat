package dev.mateusneres.bytechat.server.controller;

import dev.mateusneres.bytechat.server.view.ServerConsole;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class ServerConsoleController {

    private final ServerConsole serverConsole;
    private final ServerController serverController;

    public ServerConsoleController(ServerConsole serverConsole, ServerController serverController) {
        this.serverConsole = serverConsole;
        this.serverController = serverController;

        handleListeners();
    }

    private void handleListeners() {
        serverConsole.getSendButton().addActionListener(e -> {
            String command = serverConsole.getCommandTextField().getText();

            if (command.isEmpty()) {
                JOptionPane.showMessageDialog(serverConsole, "TextField is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] args = command.split(" ");
            if (args.length > 0 && args[0].equalsIgnoreCase("!alert")) {
                serverController.sendAlertAllUsers(command.replace("!alert ", ""));
            }

            serverConsole.printMessage("[SERVER] " + command, Color.BLUE);
        });
    }

}
