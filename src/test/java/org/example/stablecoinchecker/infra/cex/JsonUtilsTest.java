package org.example.stablecoinchecker.infra.cex;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.stablecoinchecker.infra.cex.updit.dto.UpbitWebSocketResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JsonUtilsTest {

    @Autowired
    private JsonUtils jsonUtils;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 타입이_맞지않는경우_empty_를_반환한다() {
        String message = "{\"status\":\"UP\"}";

        Optional<UpbitWebSocketResponse> deserialize = jsonUtils.deserialize(message, UpbitWebSocketResponse.class);

        Assertions.assertThat(deserialize).isNotPresent();
    }

    @Test
    void 기존_ObjectMapper_에는_영향을주지않는다() throws Exception {
        String message = "{\"status\":\"UP\"}";
        UpbitWebSocketResponse response = objectMapper.readValue(message, UpbitWebSocketResponse.class);
        Assertions.assertThat(response).hasAllNullFieldsOrProperties();
    }
}
