package com.msnishan.wire.wire;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.VerificationException;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import com.github.tomakehurst.wiremock.standalone.JsonFileMappingsSource;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

/**
 * Created by ksaleh on 1/12/17.
 */
@Component
@Profile("stub")
public class WireConfig {

    private WireMockServer wireMockServer;
    private WireMock wireMock;

    public WireConfig() throws IOException {
        wireMockServer = new WireMockServer(options().port(8099).mappingSource(new JsonFileMappingsSource(
                new ClasspathFileSource("scr/main/resources")
        )));
        wireMockServer.start();
        wireMock = new WireMock("localhost", 8099);
        registerBehavior();
    }


    @PreDestroy
    public void stopServer() {
        wireMockServer.stop();
    }

    public void registerBehavior() throws IOException {
        wireMock.register(
                WireMock.get(WireMock.urlEqualTo("/api/random"))
                .willReturn(WireMock.aResponse().withHeader("Content-Type", "application/json")
                            .withBody("{\"status\":\"ok\"}"))
        );

        wireMock.register(StubMapping.buildFrom(new String(Files.readAllBytes(Paths.get("src/main/resources/template.json")))));
    }

    public boolean verify(RequestPatternBuilder requestPatternBuilder) {
        boolean result = false;
        try {
            wireMock.verifyThat(requestPatternBuilder);
            return true;
        } catch (VerificationException e) {}
        return result;
    }
}
