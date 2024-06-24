package org.example.stablecoinchecker.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.example.stablecoinchecker.domain.cryptoticker.CryptoTicker;
import org.example.stablecoinchecker.domain.exchangeRate.ExchangeRate;
import org.example.stablecoinchecker.domain.exchangeRate.KimchiPremiumCalculationService;
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
    private final KimchiPremiumCalculationService kimchiPremiumCalculationService;

    @Scheduled(cron = "${schedule.cron}")
    @SchedulerLock(
            name = "scheduledSendMessageTask",
            lockAtLeastFor = "4m",
            lockAtMostFor = "4m"
    )
    public void sendMessage() {
        ExchangeRate exchangeRate = exchangeRateService.save();
        List<CryptoTicker> cryptoTicker = cryptoTickerService.saveAll();

        StringBuffer sb = new StringBuffer();
        sb.append(MessageFormatter.formatExchangeRateMessage(exchangeRate));
        sb.append(MessageFormatter.formatStablecoinMessage(
                cryptoTicker,
                exchangeRate,
                kimchiPremiumCalculationService
        ));

        messagingServiceProvider.sendMessage(sb.toString());
    }
}
