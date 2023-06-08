package dev.mateusneres.bytechat.client;

import dev.mateusneres.bytechat.client.controllers.IdentificationController;
import dev.mateusneres.bytechat.client.views.screens.IdentificationScreen;
import dev.mateusneres.bytechat.client.views.screens.SplashScreen;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ByteChatClient {

    public static void main(String[] args) {
        setupFlatLafTheme();

        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen();
            splash.showSplashScreen();

            AtomicInteger progress = new AtomicInteger();
            //TODO FIX TIMER IN 20
            Timer timer = new Timer(1, e -> {
                splash.setProgress(progress.addAndGet(1));

                if (progress.get() > 100) {
                    ((Timer) e.getSource()).stop();
                    splash.closeSplashScreen();

                    IdentificationScreen identification = new IdentificationScreen();
                    new IdentificationController(identification);
                }
            });

            timer.start();
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