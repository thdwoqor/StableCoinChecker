package org.example.stablecoinchecker;

import lombok.RequiredArgsConstructor;
import org.example.stablecoinchecker.infra.bithumb.BithumbClient;
import org.example.stablecoinchecker.infra.bithumb.dto.BithumbPriceResponse;
import org.example.stablecoinchecker.infra.coinmarketcap.CoinMarketCapClient;
import org.example.stablecoinchecker.infra.coinmarketcap.dto.CategoryResponse;
import org.example.stablecoinchecker.infra.coinmarketcap.dto.Token;
import org.example.stablecoinchecker.infra.coinone.CoinoneClient;
import org.example.stablecoinchecker.infra.gopax.GopaxClient;
import org.example.stablecoinchecker.infra.korbit.KorbitClient;
import org.example.stablecoinchecker.infra.telegram.TelegramClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Schedule {

    private static final String CATEGORY_ID = "625d04fa57c0560770d004e1";
    private static final String ACCEPT = "application/json";

    @Value("${telegram.token}")
    private String token;
    @Value("${telegram.chatId}")
    private String chatId;
    @Value("${coinmarketcap.secret}")
    private String secret;
    private final TelegramClient telegramClient;
    private final BithumbClient bithumbClient;
    private final CoinoneClient coinoneClient;
    private final GopaxClient gopaxClient;
    private final KorbitClient korbitClient;
    private final CoinMarketCapClient coinMarketCapClient;

    @Scheduled(cron = "${schedule.cron}")
    public void execute() {
        if (token == null || chatId == null) {
            throw new IllegalArgumentException("텔레그램 작업을 수행하기 위해 필요한 토큰 및 채팅 ID가 설정되지 않았습니다.");
        }
        CategoryResponse category = coinMarketCapClient.getCategory(secret, ACCEPT, CATEGORY_ID, 15);
        telegramClient.sendMessage(token, chatId, formatMessage(category), "MarkdownV2");
    }

    private String formatMessage(final CategoryResponse response) {
        String coinconePrice = coinoneClient.getTokenPrice().tickers().get(0).last();
        String gopaxPrice = gopaxClient.getTokenPrice().price();
        String korbitPrice = korbitClient.getTokenPrice().last();
        String bithumbPrice = bithumbClient.getTokenPrice().data().closingPrice();

        StringBuilder sb = new StringBuilder();
        sb.append("*• 국내 스테이블 코인 가격*\n");
        sb.append("```복사\n");
        sb.append(String.format("%-8s(USDT) : %,d원\n", "Bithumb", Integer.parseInt(bithumbPrice)));
        sb.append(String.format("%-8s(USDT) : %,d원\n", "Coinone", Integer.parseInt(coinconePrice.split("\\.")[0])));
        sb.append(String.format("%-8s(USDC) : %,d원\n", "Korbit", Integer.parseInt(korbitPrice)));
        sb.append(String.format("%-8s(USDC) : %,d원\n", "Gopax", Integer.parseInt(gopaxPrice)));
        sb.append("```\n");
        sb.append("*• 해외 스테이블 코인 가격*\n");
        sb.append("```복사\n");
        for (Token coin : response.data().coins()) {
            double price = coin.quote().USD().price();
            sb.append(String.format("%-6s : %.4f$\n", coin.symbol(), price));
        }
        sb.append("```\n");
        return sb.toString();
    }
}
