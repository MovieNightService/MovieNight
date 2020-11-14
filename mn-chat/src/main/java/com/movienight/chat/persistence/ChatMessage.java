package com.movienight.chat.persistence;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private MessageType type;
    private String content;
    private String sender;
}
