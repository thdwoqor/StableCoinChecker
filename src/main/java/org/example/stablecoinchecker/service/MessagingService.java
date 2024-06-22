package org.example.stablecoinchecker.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.example.stablecoinchecker.domain.cryptoticker.CryptoTicker;
import org.example.stablecoinchecker.infra.telegram.MessagingServiceProvider;
import org.example.stablecoinchecker.service.dto.MessageFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagingService {

    private final MessagingServiceProvider messagingServiceProvider;
    private final ExchangeRateService exchangeRateService;
    private final CryptoTickerService cryptoTickerService;

    @Scheduled(cron = "${schedule.cron}")
    @SchedulerLock(
            name = "scheduledSendMessageTask",
            lockAtLeastFor = "4m",
            lockAtMostFor = "4m"
    )
    public void sendMessage() {
        BigDecimal exchangeRate = exchangeRateService.getExchangeRate();
        List<CryptoTicker> cryptoTicker = cryptoTickerService.saveAll(exchangeRate);

        StringBuffer sb = new StringBuffer();
        sb.append(MessageFormatter.formatExchangeRateMessage(exchangeRate));
        sb.append(MessageFormatter.formatStablecoinMessage(cryptoTicker));

        messagingServiceProvider.sendMessage(sb.toString());
    }
}
