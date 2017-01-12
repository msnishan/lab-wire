package com.msnishan.wire;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.msnishan.wire.config.AppConfig;
import com.msnishan.wire.rest.RestService;
import com.msnishan.wire.wire.WireConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by ksaleh on 1/12/17.
 */
public class Main  {
    public static void main(String[] args) {
        System.out.println("Loading Application Context");
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfig.class);
        applicationContext.refresh();
        System.out.println("Application Context Loaded");
        RestService restService = applicationContext.getBean(RestService.class);
        String response = restService.invokeGetRest(String.class);
        System.out.println("Response from WebService: " + response);
        if (applicationContext.getEnvironment().getActiveProfiles()[0].equals("stub")) {
            WireConfig wireMock = applicationContext.getBean(WireConfig.class);
            boolean result = wireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/api/random")));
            System.out.println("Verification Result : " + result);
        }
        applicationContext.destroy();
    }
}
