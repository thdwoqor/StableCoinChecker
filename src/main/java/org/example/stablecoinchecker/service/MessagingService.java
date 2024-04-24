package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.example.stablecoinchecker.infra.telegram.MessagingServiceProvider;
import org.example.stablecoinchecker.service.dto.MessageFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagingService {

    private final MessagingServiceProvider messagingServiceProvider;
    private final ExchangeRateService exchangeRateService;
    private final StableCoinService stableCoinService;
    private final UpbitService upbitService;

    @Scheduled(cron = "${schedule.cron}")
    @SchedulerLock(
            name = "scheduledSendMessageTask",
            lockAtLeastFor = "4m",
            lockAtMostFor = "4m"
    )
    public void sendMessage() {
        BigDecimal exchangeRate = exchangeRateService.getExchangeRate();

        StringBuffer sb = new StringBuffer();
        sb.append(MessageFormatter.createExchangeRateMessage(exchangeRate));
        sb.append(MessageFormatter.createConvertedUsdtPriceMessage(upbitService.convertBtcToUsdt(exchangeRate)));
        sb.append(MessageFormatter.createStableCoinPricesMessage(stableCoinService.findStableCoin(exchangeRate)));
        messagingServiceProvider.sendMessage(sb.toString());
    }
}
