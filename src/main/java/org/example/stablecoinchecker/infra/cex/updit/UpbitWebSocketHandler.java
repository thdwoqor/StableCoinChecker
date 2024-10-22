package org.example.stablecoinchecker.infra.cex.updit;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stablecoinchecker.infra.cex.JsonUtils;
import org.example.stablecoinchecker.infra.cex.CryptoExchangeTickerEvent;
import org.example.stablecoinchecker.infra.cex.updit.dto.UpbitTicketRequest;
import org.example.stablecoinchecker.infra.cex.updit.dto.UpbitWebSocketRequest;
import org.example.stablecoinchecker.infra.cex.updit.dto.UpbitWebSocketResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpbitWebSocketHandler extends BinaryWebSocketHandler {

    private final ApplicationEventPublisher publisher;
    private final JsonUtils jsonUtils;

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage(jsonUtils.serialize(
                List.of(
                        new UpbitTicketRequest(UUID.randomUUID().toString()),
                        new UpbitWebSocketRequest("ticker", List.of("KRW-USDT","KRW-BTC"))
                )
        )));
    }

    @Override
    public void handleMessage(final WebSocketSession session, final WebSocketMessage<?> message) {
        jsonUtils.deserialize((ByteBuffer) message.getPayload(), UpbitWebSocketResponse.class)
                .ifPresent(this::dispatchTickerEvent);
    }

    private void dispatchTickerEvent(final UpbitWebSocketResponse response) {
        publisher.publishEvent(
                new CryptoExchangeTickerEvent(
                        "UPBIT",
                        response.getCode().split("-")[1],
                        response.getTradePrice(),
                        response.getTimestamp()
                ));
    }
}
