package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.example.stablecoinchecker.domain.stablecoin.StableCoin;
import org.example.stablecoinchecker.infra.telegram.MessagingServiceProvider;
import org.example.stablecoinchecker.service.dto.MessageFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagingService {

    private final MessagingServiceProvider messagingServiceProvider;
    private final ExchangeRateService exchangeRateService;
    private final StableCoinService stableCoinService;

    @Scheduled(cron = "${schedule.cron}")
    @SchedulerLock(
            name = "scheduledSendMessageTask",
            lockAtLeastFor = "4m",
            lockAtMostFor = "4m"
    )
    public void sendMessage() {
        BigDecimal exchangeRate = exchangeRateService.getExchangeRate();
        List<StableCoin> stableCoin = stableCoinService.saveAll(exchangeRate);

        StringBuffer sb = new StringBuffer();
        sb.append(MessageFormatter.formatExchangeRateMessage(exchangeRate));
        sb.append(MessageFormatter.formatStableCoinMessage(stableCoin));

        messagingServiceProvider.sendMessage(sb.toString());
    }
}
