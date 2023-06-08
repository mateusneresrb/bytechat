package dev.mateusneres.bytechat.server.view;

import lombok.Getter;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Getter
public class ServerConsole extends JFrame {

    private final JLabel titleLabel;
    private JTextPane consoleTextPane;
    private JTextField commandTextField;
    private JButton sendButton;

    public ServerConsole(String serverName) {
        setTitle("ByteChat Server");

        titleLabel = new JLabel("Server: " + serverName);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        consoleTextPane = new JTextPane();
        consoleTextPane.setBackground(Color.BLACK);
        consoleTextPane.setForeground(Color.WHITE);
        consoleTextPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        consoleTextPane.setEditable(false);

        StyleContext styleContext = new StyleContext();
        Style style = styleContext.addStyle("CustomStyle", null);
        StyleConstants.setForeground(style, Color.WHITE);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        headerPanel.add(titleLabel);

        JScrollPane scrollPane = new JScrollPane(consoleTextPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        DefaultCaret caret = (DefaultCaret) consoleTextPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JPanel commandPanel = new JPanel(new BorderLayout());
        commandTextField = new JTextField();
        commandTextField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        sendButton = new JButton("Enviar");
        commandPanel.add(commandTextField, BorderLayout.CENTER);
        commandPanel.add(sendButton, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(commandPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void printMessage(String message) {
        printMessage(message, Color.WHITE);
    }

    public void printMessage(String message, Color color) {
        StyledDocument doc = consoleTextPane.getStyledDocument();
        StyleContext styleContext = new StyleContext();
        Style style = styleContext.addStyle("CustomStyle", null);
        StyleConstants.setForeground(style, color);

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'['HH:mm:ss']' ");
        String formattedTime = currentTime.format(formatter);

        try {
            consoleTextPane.getDocument().insertString(doc.getLength(), formattedTime + message + "\n", style);
        } catch (BadLocationException e) {
            Logger.getGlobal().severe("Error on add message in jpane: " + e.getMessage());
        }
    }


}
