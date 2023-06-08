package dev.mateusneres.bytechat.client.controllers;

import dev.mateusneres.bytechat.client.views.screens.HomeScreen;
import dev.mateusneres.bytechat.common.model.UserInfo;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

public class HomeController {

    @Setter
    @Getter
    private UserInfo selectedUser;
    @Getter
    private final HomeScreen homeScreen;
    private final ClientController clientController;

    public HomeController(ClientController clientController, HomeScreen homeScreen) {
        this.clientController = clientController;
        this.homeScreen = homeScreen;

        clientController.initClient(this);
        handleListeners();
    }

    public void handleListeners() {
        onSendMessageEvent();
    }

    private void onSelectedChatEvent() {
        homeScreen.getUserPanelList().forEach(jPanel -> jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof JPanel) {
                    JPanel userPanel = (JPanel) component;
                    UUID userID = UUID.fromString(userPanel.getToolTipText());

                    setSelectedUser(clientController.getUserInfo(userID));
                    updateUsersList();
                    updateMessageList();
                }
            }
        }));
    }

    private void onSendMessageEvent() {
        homeScreen.getSendButton().addActionListener(e -> {
            String inputText = homeScreen.getInputArea().getText();

            if (inputText.isEmpty()) {
                JOptionPane.showMessageDialog(homeScreen, "Please, type a message!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedUser == null) {
                JOptionPane.showMessageDialog(homeScreen, "Please, open a user chat!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            homeScreen.getInputArea().setText("");
            clientController.sendMessageTo(selectedUser.getUserID(), inputText);
            updateMessageList();
        });
    }

    public void updateMessageList() {
        if (selectedUser == null) return;
        homeScreen.openChat(selectedUser);
        homeScreen.setMessageChat(clientController.getUserInfo(), clientController.getMessages(clientController.getUserInfo().getUserID(), selectedUser.getUserID()));
    }

    public void updateUsersList() {
        homeScreen.setUserList(clientController.getUserInfo(), clientController.getUsersList(), selectedUser);
        onSelectedChatEvent();
    }

}
