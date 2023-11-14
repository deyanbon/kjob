package com.dbon.job.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@Order(0)
public class KJobAppListener implements ApplicationListener<ApplicationReadyEvent> {

    Logger logger = LoggerFactory.getLogger(KJobAppListener.class);

    @Value("${request.daemon.url}")
    String dpath;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        String input = "db_"+ UUID.randomUUID();
        HttpEntity<String> entity = new HttpEntity<String>(input);

        String response;

        try {
            response = restTemplate.exchange(dpath, HttpMethod.POST, entity, String.class).getBody();
        }
        catch (RestClientException e) {
            logger.error("Failure when call the daemon...", e);
            response = "Error: " + e.getLocalizedMessage();
        }

        logger.info("Job {} run: [{}]", input,  response);
    }
}