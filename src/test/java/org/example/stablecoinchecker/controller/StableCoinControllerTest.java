package org.example.stablecoinchecker.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class StableCoinControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 조건에_맞는_영역_차트_데이터를_반환할_수_있다() {
        // given
        String 검색_조건을_만족하는_쿼리_1 =
                "INSERT INTO stable_coin (exchange_rate, cex, symbol, current_price, open, low, high, created_at, volume) "
                        + "VALUES (1401, 'UPBIT', 'USDT', 1443, 1443, 1441, 1446, 900, 9639895);";
        String 검색_조건을_만족하는_쿼리_2 =
                "INSERT INTO stable_coin (exchange_rate, cex, symbol, current_price, open, low, high, created_at, volume) "
                        + "VALUES (1404, 'UPBIT', 'USDT', 1445, 1443, 1441, 1446, 1800, 9639895);";
        String 검색_조건을_만족하지_않는_쿼리_1 =
                "INSERT INTO stable_coin (exchange_rate, cex, symbol, current_price, open, low, high, created_at, volume) "
                        + "VALUES (1401, 'UPBIT', 'USDT', 1444, 1444, 1440, 1445, 2700, 9639895);";
        String 검색_조건을_만족하지_않는_쿼리_2 =
                "INSERT INTO stable_coin (exchange_rate, cex, symbol, current_price, open, low, high, created_at, volume) "
                        + "VALUES (1403, 'UPBIT', 'USDT', 1448, 1444, 1440, 1445, 1000, 9639895);";
        jdbcTemplate.batchUpdate(
                검색_조건을_만족하는_쿼리_1,
                검색_조건을_만족하는_쿼리_2,
                검색_조건을_만족하지_않는_쿼리_1,
                검색_조건을_만족하지_않는_쿼리_2
        );

        // when
        List<List<Long>> result = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/chart/area?"
                        + "cex=UPBIT&"
                        + "symbol=USDT&"
                        + "interval=900&"
                        + "limit=20&"
                        + "to=2000")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath().getList(".");

        //then
        Assertions.assertThat(result).isEqualTo(List.of(List.of(900, 1443), List.of(1800, 1445)));
    }
}
