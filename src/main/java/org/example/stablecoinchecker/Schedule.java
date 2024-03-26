package org.example.stablecoinchecker;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.telegram.TelegramClient;
import org.example.stablecoinchecker.infra.telegram.dto.Message;
import org.example.stablecoinchecker.infra.telegram.dto.TelegramResponse;
import org.example.stablecoinchecker.service.CoinService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Schedule {

    @Value("${telegram.token}")
    private String token;
    @Value("${telegram.chatId}")
    private String chatId;
    private final TelegramClient telegramClient;
    private final CoinService coinService;

    @Scheduled(cron = "${schedule.cron}")
    public void execute() {
        if (token == null || chatId == null) {
            throw new IllegalArgumentException("텔레그램 작업을 수행하기 위해 필요한 토큰 및 채팅 ID가 설정되지 않았습니다.");
        }
        telegramClient.sendMessage(token, chatId, toTelegramResponse().getMessage(), "MarkdownV2");
    }

    private TelegramResponse toTelegramResponse() {
        BigDecimal exchangeRate = coinService.getExchangeRate();
        return TelegramResponse.of(List.of(
                Message.exchangeRateOf(exchangeRate),
                Message.estimatedPriceOf(coinService.getEstimatedStableCoin()),
                Message.stableCoinPricesOf(coinService.getStableCoin())
        ));
    }
}
