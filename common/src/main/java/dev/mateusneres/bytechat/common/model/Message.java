package dev.mateusneres.bytechat.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Message {

    private UUID senderUserID;
    private UUID receiverUserID;
    private String text;
    private Instant date;

}
