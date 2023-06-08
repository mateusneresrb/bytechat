package dev.mateusneres.bytechat.client.views.screens;

import dev.mateusneres.bytechat.client.utils.AvatarUtil;
import dev.mateusneres.bytechat.client.utils.IconUtil;
import dev.mateusneres.bytechat.common.components.MImage;
import lombok.Getter;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;

@Getter
public class IdentificationScreen extends JFrame {

    private final JLabel titleLabel;
    private final MImage avatar;
    private final JLabel labelTxtArea;
    private final JTextField textField;
    private final JButton button;

    public IdentificationScreen() {
        super("ByteChat - Identification");
        IconUtil.setIcon(this);

        titleLabel = new JLabel("Choose your avatar and nickname!");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(70, 0, 20, 0));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        avatar = AvatarUtil.getRandomAvatar();
        avatar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        avatar.setToolTipText("Click to change to: charlie");
        avatar.setHorizontalAlignment(SwingConstants.CENTER);

        labelTxtArea = new JLabel("Nickname:");
        labelTxtArea.setBorder(BorderFactory.createEmptyBorder(15, 0, 2, 0));
        labelTxtArea.setHorizontalAlignment(SwingConstants.CENTER);

        textField = new JTextField();

        button = new JButton("CONTINUE ->");
        button.setHorizontalAlignment(SwingConstants.CENTER);

        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new VerticalLayout());

        JPanel formPanel = new JPanel(new VerticalLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));
        formPanel.add(labelTxtArea);
        formPanel.add(textField);
        formPanel.add(button);

        contentPane.add(titleLabel);
        contentPane.add(avatar);
        contentPane.add(formPanel);

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 420));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setAlwaysOnTop(true);
    }
}
