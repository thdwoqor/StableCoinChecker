package org.example.stablecoinchecker.infra.cex;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.stablecoinchecker.infra.cex.updit.dto.UpbitWebSocketResponse;
import org.junit.jupiter.api.Test;

class JsonUtilsTest {

    @Test
    void name() {
        String message = "{\"status\":\"UP\"}";

        JsonUtils jsonUtils = new JsonUtils(new ObjectMapper());

        Optional<UpbitWebSocketResponse> deserialize = jsonUtils.deserialize(message, UpbitWebSocketResponse.class);

        Assertions.assertThat(deserialize).isNotPresent();
    }
}
