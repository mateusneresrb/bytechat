package dev.mateusneres.bytechat.client.views.components;

import javax.swing.*;
import java.awt.*;

public class MCircleStatus extends JLabel {

    private boolean online;

    public MCircleStatus(boolean online) {
        this.online = online;
        setPreferredSize(new Dimension(12, 12));
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color statusColor = online ? Color.GREEN : Color.RED;
        g.setColor(statusColor);

        int circleSize = Math.min(getWidth(), getHeight()) - 4;
        int x = (getWidth() - circleSize) / 2;
        int y = (getHeight() - circleSize) / 2;

        g.fillOval(x, y, circleSize, circleSize);
    }

}
