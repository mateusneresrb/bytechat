package dev.mateusneres.bytechat.common.utils;

import java.io.IOException;
import java.net.ServerSocket;

public class PortUtil {

    public static int getAvailablePort() {
        int initialPort = 52930;
        int numPortsToCheck = 10;

        for (int i = 0; i <= numPortsToCheck; i++) {
            if (isPortAvailable(initialPort)) {
                return initialPort;
            }
            initialPort++;
        }

        return -1;
    }

    public static boolean isPortAvailable(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
