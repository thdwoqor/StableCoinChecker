package org.example.stablecoinchecker.infra.cex.bithumb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Getter
public class BithumbWebSocketResponse {

    private Content content;

    public BithumbWebSocketResponse(
            @JsonProperty(value = "content", required = true)
            final Content content
    ) {
        this.content = content;
    }
}
