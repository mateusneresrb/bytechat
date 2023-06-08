package dev.mateusneres.bytechat.client.utils;

import dev.mateusneres.bytechat.common.components.MImage;

import javax.swing.*;

public class AvatarUtil {

    private static String[] avatars = new String[]{"baby", "buster", "charlie", "garfield", "kitty", "sophie"};

    public static MImage getRandomAvatar() {
        String avatar = avatars[(int) (Math.random() * avatars.length)];
        MImage randomAvatar = getAvatarByName(avatar, 82, 82);
        randomAvatar.setToolTipText("Click to change to: " + avatar);

        return randomAvatar;
    }

    public static MImage getAvatarByName(String name, int height, int width) {
        return new MImage(new ImageIcon(AvatarUtil.class.getResource("/avatars/" + name + ".png")), height, width);
    }

}
