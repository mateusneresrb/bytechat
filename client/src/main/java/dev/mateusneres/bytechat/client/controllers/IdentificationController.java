package dev.mateusneres.bytechat.client.controllers;

import dev.mateusneres.bytechat.client.utils.AvatarUtil;
import dev.mateusneres.bytechat.client.views.screens.IdentificationScreen;
import dev.mateusneres.bytechat.client.views.screens.ServerListScreen;
import dev.mateusneres.bytechat.common.components.MImage;
import dev.mateusneres.bytechat.common.model.UserInfo;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IdentificationController {

    private final IdentificationScreen identification;

    public IdentificationController(IdentificationScreen identification) {
        this.identification = identification;
        handleListeners();
    }

    private void handleListeners() {
        onAvatarClickEvent();
        onButtonClickEvent();
    }

    private void onAvatarClickEvent() {
        identification.getAvatar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MImage randomAvatar = AvatarUtil.getRandomAvatar();
                identification.getAvatar().setIcon(randomAvatar.getIcon());
                identification.getAvatar().setToolTipText(randomAvatar.getToolTipText());
            }
        });
    }

    private void onButtonClickEvent() {
        identification.getButton().addActionListener(e -> {
            String avatarName = identification.getAvatar().getToolTipText().split(": ")[1];
            String textArea = identification.getTextField().getText();

            if (textArea.isEmpty()) {
                JOptionPane.showMessageDialog(identification.getTextField(), "Please, enter your name!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (textArea.length() >= 14) {
                JOptionPane.showMessageDialog(identification.getTextField(), "Your name is too long! Max.: 14 char", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            identification.dispose();
            ServerListScreen serverListScreen = new ServerListScreen();
            new ServerListController(serverListScreen, new UserInfo(textArea, avatarName));
        });
    }


}
