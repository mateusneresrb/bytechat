package dev.mateusneres.bytechat.server.view;

import dev.mateusneres.bytechat.common.components.MImage;
import lombok.Getter;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;

@Getter
public class ServerSetup extends JFrame {

    private final JLabel titleLabel;
    private final MImage avatar;
    private final JTextField textField;
    private final JButton button;

    public ServerSetup() {
        super("ByteChat - Server Creator");

        titleLabel = new JLabel("Choose your server name!");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(70, 0, 20, 0));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        avatar = new MImage(new ImageIcon(getClass().getResource("/host.png")), 82, 82);
        avatar.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);

        textField = new JTextField();

        button = new JButton("CONTINUE ->");
        button.setHorizontalAlignment(SwingConstants.CENTER);

        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new VerticalLayout());

        JPanel formPanel = new JPanel(new VerticalLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));
        formPanel.add(textField);
        formPanel.add(button);

        contentPane.add(titleLabel);
        contentPane.add(avatar);
        contentPane.add(formPanel);

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 400));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setAlwaysOnTop(true);
    }

}
