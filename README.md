
## 거래소 API

[업비트](https://docs.upbit.com/reference/ticker%ED%98%84%EC%9E%AC%EA%B0%80-%EC%A0%95%EB%B3%B4)
```shell
trade_price	종가(현재가)
opening_price	시가	Double
low_price	저가	Double
high_price	고가	Double
acc_trade_volume  누적 거래량(UTC 0시 기준)	Double
```

[빗썸](https://apidocs.bithumb.com/reference/%ED%98%84%EC%9E%AC%EA%B0%80-%EC%A0%95%EB%B3%B4-%EC%A1%B0%ED%9A%8C)
```shell
closing_price	종가 00시 기준	Number (String) <-
opening_price	시가 00시 기준	Number (String)
min_price	저가 00시 기준	Number (String)
max_price	고가 00시 기준	Number (String)
units_traded	거래량 00시 기준	Number (String)
```

[코인원](https://docs.coinone.co.kr/reference/ticker)
```shell
-last	NumberString	종가 (24시간 기준) <-
-first	NumberString	시가 (24시간 기준)
-low	NumberString	저가 (24시간 기준)
-high	NumberString	고가 (24시간 기준)
target_volume	NumberString	24시간 기준 종목 체결량 (종목)
```

[코빗](https://apidocs.korbit.co.kr/ko/#b5b542c8be)
```shell
last	최종 체결 가격. <-
open	최근 24시간 시작 가격.
low	최저가. 최근 24시간 동안의 체결 가격 중 가장 낮 가격.
high	최고가. 최근 24시간 동안의 체결 가격 중 가장 높은 가격.
volume	거래량.
```

[고팍스](https://gopax.github.io/API/index.html#24-2)
```shell
"close": 397200,                    # 현재 가격 (1분마다 갱신됨)
"open": 394200,                     # 24시간 전 가격
"low": 388700,                      # 최근 24시간 동안 최저가
"high": 409900,                     # 최근 24시간 동안 최고가
"volume": 4017.90526766,            # 최근 24시간 누적 거래량 (base 자산 단위로 이 예시에서는 BTC)
```
