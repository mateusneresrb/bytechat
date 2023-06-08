package dev.mateusneres.bytechat.server;

import dev.mateusneres.bytechat.server.controller.ServerSetupController;
import dev.mateusneres.bytechat.server.view.ServerSetup;

import javax.swing.*;

public class ByteChatServer {
    
    public static void main(String[] args) {
        setupFlatLafTheme();

        SwingUtilities.invokeLater(() -> {
            ServerSetup serverSetup = new ServerSetup();
            new ServerSetupController(serverSetup);
        });
    }

    private static void setupFlatLafTheme() {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}