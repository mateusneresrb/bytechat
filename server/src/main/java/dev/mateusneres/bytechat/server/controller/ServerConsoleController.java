package dev.mateusneres.bytechat.server.controller;

import dev.mateusneres.bytechat.server.view.ServerConsole;
import lombok.Getter;

@Getter
public class ServerConsoleController {

    private final ServerConsole serverConsole;
    private final ServerController serverController;

    public ServerConsoleController(ServerConsole serverConsole, ServerController serverController) {
        this.serverConsole = serverConsole;
        this.serverController = serverController;

        handleListeners();
    }


    private void handleListeners() {
        //???
        //
    }


}
