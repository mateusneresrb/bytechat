package dev.mateusneres.bytechat.common.components;

import javax.swing.*;
import java.awt.*;

public class MImage extends JLabel {

    public MImage(ImageIcon imageIcon) {
        setIcon(imageIcon);
    }

    public MImage(ImageIcon imageIcon, int width, int height) {
        ImageIcon resizeImageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        setIcon(resizeImageIcon);
    }

}
