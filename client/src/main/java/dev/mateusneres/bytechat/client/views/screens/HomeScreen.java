package dev.mateusneres.bytechat.client.views.screens;

import dev.mateusneres.bytechat.client.utils.AvatarUtil;
import dev.mateusneres.bytechat.client.utils.IconUtil;
import dev.mateusneres.bytechat.client.views.components.MCircleStatus;
import dev.mateusneres.bytechat.common.components.MImage;
import dev.mateusneres.bytechat.common.model.Message;
import dev.mateusneres.bytechat.common.model.UserInfo;
import lombok.Getter;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class HomeScreen extends JFrame {

    private final JLabel userDetailsPhoto;
    private final JLabel userDetailsName;
    private JPanel messagesPanel;
    private JPanel sidebarPanel;
    private JPanel usersPanel;
    private final List<JPanel> userPanelList;
    private JTextField inputArea;
    private JButton sendButton;

    public HomeScreen(String serverName) {
        super("ByteChat");
        IconUtil.setIcon(this);

        userPanelList = new ArrayList<>();
        userDetailsPhoto = new JLabel();
        userDetailsName = new JLabel();

        initComponents(serverName);
    }

    private void initComponents(String serverName) {
        JPanel contentPane = new JPanel(new BorderLayout());

        sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setBackground(Color.decode("#3C3C4C"));
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
        sidebarPanel.setPreferredSize(new Dimension(150, getHeight()));

        JPanel sideBarHeader = new JPanel(new VerticalLayout());
        sideBarHeader.setPreferredSize(new Dimension(getWidth(), 50));
        sideBarHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        JLabel headerTitle = new JLabel("Chats");
        headerTitle.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        headerTitle.setFont(new Font("Arial", Font.BOLD, 16));
        headerTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel headerServer = new JLabel(serverName);
        headerServer.setBorder(BorderFactory.createEmptyBorder(2, 0, 10, 0));
        headerServer.setFont(new Font("Arial", Font.PLAIN, 12));
        headerServer.setHorizontalAlignment(SwingConstants.CENTER);

        sideBarHeader.add(headerTitle);
        sideBarHeader.add(headerServer);
        sidebarPanel.add(sideBarHeader, BorderLayout.NORTH);

        usersPanel = new JPanel();
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));

        JScrollPane usersScrollPanae = new JScrollPane(usersPanel);
        sidebarPanel.add(usersScrollPanae, BorderLayout.CENTER);
        contentPane.add(sidebarPanel, BorderLayout.WEST);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel userSection = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userSection.setBackground(Color.decode("#3C3C4C"));
        userSection.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        userSection.setPreferredSize(new Dimension(getWidth(), 50));

        JPanel userSectionDetails = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userSectionDetails.setBackground(Color.decode("#3C3C4C"));

        userDetailsName.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        userDetailsName.setFont(new Font("Arial", Font.BOLD, 18));
        userSectionDetails.add(userDetailsPhoto);
        userSectionDetails.add(userDetailsName);

        userSection.add(userSectionDetails);

        messagesPanel = new JPanel();
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
        messagesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(messagesPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        inputArea = new JTextField();
        inputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        sendButton = new JButton("Send");

        formPanel.add(inputArea, BorderLayout.CENTER);
        formPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(userSection, BorderLayout.PAGE_START);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(formPanel, BorderLayout.PAGE_END);
        contentPane.add(mainPanel, BorderLayout.CENTER);

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setAlwaysOnTop(true);
    }

    public void openChat(UserInfo selectedUser) {
        userDetailsName.setText(selectedUser.getName());
        userDetailsPhoto.setIcon(AvatarUtil.getAvatarByName(selectedUser.getAvatar(), 32, 32).getIcon());
        inputArea.requestFocus();
    }

    public void setUserList(UserInfo myUser, List<UserInfo> userList, UserInfo selectedUser) {
        userPanelList.clear();
        usersPanel.removeAll();
        userList.forEach(userInfo -> {
            if (userInfo.getUserID().equals(myUser.getUserID())) return;
            JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            userPanel.setToolTipText(userInfo.getUserID().toString());
            userPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            userPanel.setMaximumSize(new Dimension(getWidth(), 35));
            userPanel.setBorder(BorderFactory.createLineBorder(selectedUser == userInfo ? Color.ORANGE : Color.GRAY, 1));

            MImage image = AvatarUtil.getAvatarByName(userInfo.getAvatar(), 16, 16);
            JLabel userNickname = new JLabel(userInfo.getName());

            MCircleStatus userStatus = new MCircleStatus(userInfo.isOnline());

            userPanel.add(image, FlowLayout.LEFT);
            userPanel.add(userNickname, FlowLayout.CENTER);
            userPanel.add(userStatus, FlowLayout.RIGHT);

            userPanelList.add(userPanel);
            usersPanel.add(userPanel);
            usersPanel.add(Box.createVerticalStrut(2));
        });

        usersPanel.revalidate();
    }

    public void setMessageChat(UserInfo ownerChat, List<Message> messages) {
        if (messages == null || messages.isEmpty()) return;
        messagesPanel.removeAll();

        int maxBorderMargin = 480;
        messages.forEach(message -> {
            boolean isSent = ownerChat.getUserID().equals(message.getSenderUserID());

            JPanel messagePanel = new JPanel(new BorderLayout());

            if (!isSent) {
                messagePanel.setBackground(new Color(30, 30, 30));
            }

            JTextArea messageLabel = new JTextArea(message.getText());
            messageLabel.setBorder(new EmptyBorder(5, 10, 0, 10));
            messageLabel.setEditable(false);
            messageLabel.setLineWrap(true);

            int min = maxBorderMargin - message.getText().length();
            if (min < 0) min = 0;
            if (isSent) {
                messagePanel.setBorder(BorderFactory.createEmptyBorder(0, min, 0, 0));
                messagePanel.setBackground(new Color(62, 79, 109));
            } else {
                messagePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, min));
                messagePanel.setBackground(new Color(54, 54, 72));
            }

            messagePanel.add(messageLabel, BorderLayout.CENTER);

            JLabel timeLabel = new JLabel(getCurrentTime());
            timeLabel.setFont(timeLabel.getFont().deriveFont(Font.ITALIC, 10f));
            timeLabel.setBorder(new EmptyBorder(0, 10, 5, 10));

            if (isSent) {
                timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            } else {
                timeLabel.setHorizontalAlignment(SwingConstants.LEFT);
            }

            messagePanel.add(timeLabel, BorderLayout.SOUTH);
            messagesPanel.add(messagePanel);
            messagesPanel.add(Box.createVerticalStrut(10));

            JScrollBar verticalScrollBar = ((JScrollPane) messagesPanel.getParent().getParent()).getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        });

        messagesPanel.revalidate();
    }

    private String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return now.format(formatter);
    }

}
