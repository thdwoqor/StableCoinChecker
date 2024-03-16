package org.example.stablecoinchecker.infra.telegram.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class TelegramResponse {

    private final String message;

    private TelegramResponse(final String message) {
        this.message = message;
    }

    public static TelegramResponse of(final List<Message> messages) {
        StringBuffer sb = new StringBuffer();
        for (Message message : messages) {
            sb.append(message.getMessage());
        }
        return new TelegramResponse(sb.toString());
    }
}
