package org.example.stablecoinchecker.infra.cex;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.stablecoinchecker.infra.cex.updit.dto.UpbitWebSocketResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JsonUtilsTest {

    @Test
    void 타입이_맞지않는경우_empty_를_반환한다() {
        final JsonUtils jsonUtils = new JsonUtils(new ObjectMapper());
        String message = "{\"status\":\"UP\"}";
        Optional<UpbitWebSocketResponse> deserialize = jsonUtils.deserialize(message, UpbitWebSocketResponse.class);

        Assertions.assertThat(deserialize).isNotPresent();
    }

}
