package org.example.stablecoinchecker.infra.telegram;

public interface MessagingServiceProvider {

    void sendMessage(String message);
}
