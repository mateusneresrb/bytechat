package dev.mateusneres.bytechat.client.views.screens;

import dev.mateusneres.bytechat.client.utils.IconUtil;
import dev.mateusneres.bytechat.common.components.MImage;
import lombok.Getter;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;

@Getter
public class ServerListScreen extends JFrame {

    private final MImage appImage;
    private final JLabel chooseServerLabel;
    private final JScrollPane scrollPane;
    private final JList<String> jList;
    private final DefaultListModel<String> listModel;
    private final JButton button;


    public ServerListScreen() {
        super("ByteChat - Server List");
        IconUtil.setIcon(this);

        appImage = new MImage(new ImageIcon(getClass().getResource("/logo_white.png")), 350, 92);
        appImage.setHorizontalAlignment(SwingConstants.CENTER);

        chooseServerLabel = new JLabel("Choose a server...");
        chooseServerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        listModel = new DefaultListModel<>();
        jList = new JList<>(listModel);

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(250, 150));

        button = new JButton("CONNECT A SERVER!");
        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new VerticalLayout());

        appImage.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
        contentPane.add(appImage);
        contentPane.add(chooseServerLabel);

        JPanel serverListPane = new JPanel(new VerticalLayout());
        serverListPane.setBorder(BorderFactory.createEmptyBorder(7, 100, 0, 100));

        scrollPane.setViewportView(jList);
        serverListPane.add(scrollPane);
        serverListPane.add(button);

        contentPane.add(serverListPane);

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 450));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setAlwaysOnTop(true);
    }
}