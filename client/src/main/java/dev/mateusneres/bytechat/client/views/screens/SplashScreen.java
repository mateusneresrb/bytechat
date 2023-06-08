package dev.mateusneres.bytechat.client.views.screens;

import dev.mateusneres.bytechat.client.utils.IconUtil;
import dev.mateusneres.bytechat.common.components.MImage;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    private final JProgressBar jProgressBar;

    public SplashScreen() {
        IconUtil.setIcon(this);

        MImage splashImage = new MImage(new ImageIcon(getClass().getResource("/logo_white.png")));

        jProgressBar = new JProgressBar();
        jProgressBar.setStringPainted(true);
        jProgressBar.setFont(new Font("Arial", Font.BOLD, 17));

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.decode("#130d27"));
        contentPane.add(splashImage, BorderLayout.CENTER);
        contentPane.add(jProgressBar, BorderLayout.SOUTH);

        setContentPane(contentPane);
        pack();

        setSize(470, 150);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
    }

    public void showSplashScreen() {
        setVisible(true);
    }

    public void setProgress(int progress) {
        jProgressBar.setValue(progress);
    }

    public void closeSplashScreen() {
        setVisible(false);
        dispose();
    }

}
