package dev.mateusneres.bytechat.common.model;

import dev.mateusneres.bytechat.common.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Payload<T> {

    private final MessageType messageType;
    private final T content;

}
