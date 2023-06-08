package dev.mateusneres.bytechat.server.threads;

import dev.mateusneres.bytechat.common.utils.PortUtil;
import dev.mateusneres.bytechat.server.controller.ServerConsoleController;
import dev.mateusneres.bytechat.server.controller.ServerController;
import dev.mateusneres.bytechat.server.model.User;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@RequiredArgsConstructor
public class ServerThread implements Runnable {

    private final ServerConsoleController serverConsoleController;
    private final ServerController serverController;
    private boolean running = true;

    @Override
    public void run() {
        int waitingMessageSeconds = 10;

        try (ServerSocket serverSocket = new ServerSocket(PortUtil.getAvailablePort())) {
            setServerSocket(serverSocket);

            sendConsoleMessage("Server started on port: " + serverSocket.getLocalPort(), Color.MAGENTA);
            while (running) {
                Socket socketClient = serverSocket.accept();
                socketClient.setSoTimeout(waitingMessageSeconds * 1000);
                handleConnection(socketClient);
            }

        } catch (IOException e) {
            sendConsoleMessage("Error on start server: " + e.getMessage(), Color.RED);
        }
    }

    private void handleConnection(Socket clientSocket) throws IOException {
        Thread thread = new Thread(new ServerClientThread(new User(clientSocket), serverConsoleController, serverController));
        thread.start();
    }

    private void sendConsoleMessage(String message, Color color) {
        serverConsoleController.getServerConsole().printMessage(message, color);
    }

    private void setServerSocket(ServerSocket serverSocket) {
        serverController.getServerInfo().setServerSocket(serverSocket);
    }

    public void stop() {
        running = false;
    }
}
