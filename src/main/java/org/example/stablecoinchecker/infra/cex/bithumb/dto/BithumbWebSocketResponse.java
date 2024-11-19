package org.example.stablecoinchecker.infra.cex.bithumb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BithumbWebSocketResponse {

    private Content content;

    public BithumbWebSocketResponse(
            final Content content
    ) {
        this.content = content;
    }
}
