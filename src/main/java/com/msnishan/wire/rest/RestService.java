package com.msnishan.wire.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by ksaleh on 1/12/17.
 */
@Service
public class RestService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rest.url}")
    private String url;

    public <T> T invokeGetRest(Class<T> clazz) {
        return restTemplate.getForObject(url, clazz);
    }
}
