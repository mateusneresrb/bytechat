package dev.mateusneres.bytechat.common.model;


import dev.mateusneres.bytechat.common.enums.ConnectionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Connection {

    private String name;
    private String avatar;
    private ConnectionType messageType;

    public Connection(ConnectionType messageType) {
        this.messageType = messageType;
    }

}
