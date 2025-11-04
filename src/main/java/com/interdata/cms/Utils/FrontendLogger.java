package com.interdata.cms.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FrontendLogger {

    @Value("${frontend.url}")
    private String frontendUrl;

    @EventListener(ApplicationReadyEvent.class)
    public void logFrontendUrl() {
        System.out.println("\n Frontend is running at : " + frontendUrl + "\n");
    }
}
