package com.ck.lab.contract.consumer;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

class CalculateClientTest {

  private WireMockServer wireMockServer;

  private CalculateClient calculateClient;

  @BeforeEach
  void setUp() {
    wireMockServer = new WireMockServer(options().dynamicPort());
    wireMockServer.start();

    calculateClient = Feign.builder()
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .contract(new SpringMvcContract())
        .target(CalculateClient.class, wireMockServer.baseUrl());
  }

  @AfterEach
  void tearDown() {
    wireMockServer.stop();
  }

  @Test
  void addTest() {
    wireMockServer.stubFor(get(urlPathEqualTo("/calculate/add"))
        .withQueryParam("first", equalTo("2.0"))
        .withQueryParam("second", equalTo("2.0"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-type", "application/json")
            .withBody("{\"value\":4.0}")
        )
    );
    var actual = calculateClient.add(2.0, 2.0);
    assertThat(actual.getValue()).isEqualTo(4.0);
  }

  @Test
  void subtractTest() {
    wireMockServer.stubFor(get(urlPathEqualTo("/calculate/subtract"))
        .withQueryParam("first", equalTo("4.0"))
        .withQueryParam("second", equalTo("2.0"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-type", "application/json")
            .withBody("{\"value\":2.0}")
        )
    );
    var actual = calculateClient.subtract(4.0, 2.0);
    assertThat(actual.getValue()).isEqualTo(2.0);
  }

  @Test
  void multiplyTest() {
    wireMockServer.stubFor(get(urlPathEqualTo("/calculate/multiply"))
        .withQueryParam("first", equalTo("4.0"))
        .withQueryParam("second", equalTo("2.0"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-type", "application/json")
            .withBody("{\"value\":8.0}")
        )
    );
    var actual = calculateClient.multiply(4.0, 2.0);
    assertThat(actual.getValue()).isEqualTo(8.0);
  }

  @Test
  void divideTest() {
    wireMockServer.stubFor(get(urlPathEqualTo("/calculate/divide"))
        .withQueryParam("first", equalTo("4.0"))
        .withQueryParam("second", equalTo("2.0"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-type", "application/json")
            .withBody("{\"value\":2.0}")
        )
    );
    var actual = calculateClient.divide(4.0, 2.0);
    assertThat(actual.getValue()).isEqualTo(2.0);
  }

  @Test
  void sqrtTest() {
    wireMockServer.stubFor(get(urlPathEqualTo("/advanced-calculate/sqrt"))
        .withQueryParam("value", equalTo("4.0"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-type", "application/json")
            .withBody("{\"value\":2.0}")
        )
    );
    var actual = calculateClient.sqrt(4.0);
    assertThat(actual.getValue()).isEqualTo(2.0);
  }

  @Test
  void powerTest() {
    wireMockServer.stubFor(get(urlPathEqualTo("/advanced-calculate/power"))
        .withQueryParam("base", equalTo("2.0"))
        .withQueryParam("exponent", equalTo("2.0"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-type", "application/json")
            .withBody("{\"value\":4.0}")
        )
    );
    var actual = calculateClient.power(2.0, 2.0);
    assertThat(actual.getValue()).isEqualTo(4.0);
  }

  @Test
  void piTest() {
    wireMockServer.stubFor(get(urlPathEqualTo("/advanced-calculate/pi"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-type", "application/json")
            .withBody("{\"value\": " + Math.PI + "}")
        )
    );
    var actual = calculateClient.pi();
    assertThat(actual.getValue()).isEqualTo(Math.PI);
  }

}