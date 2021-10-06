package com.ck.lab.contract.consumer;

import static org.assertj.core.api.Assertions.assertThat;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

@ExtendWith(PactConsumerTestExt.class)
class ConsumerPactTest {

  @Pact(consumer = "consumer", provider = "producer")
  RequestResponsePact add_2and2_pact(PactDslWithProvider builder) {
    return builder.given("add")
        .uponReceiving("add 2 and 2")
        .method("GET")
        .path("/calculate/add")
        .matchQuery("first", "2\\.0")
        .matchQuery("second", "2\\.0")
        .willRespondWith()
        .status(200)
        .headers(headers())
        .body("{ \"value\":4.0 }")
        .toPact();
  }

  @Pact(consumer = "consumer", provider = "producer")
  RequestResponsePact subtract_2from4_pact(PactDslWithProvider builder) {
    return builder.given("subtract")
        .uponReceiving("subtract 2 from 4")
        .method("GET")
        .path("/calculate/subtract")
        .matchQuery("first", "4\\.0")
        .matchQuery("second", "2\\.0")
        .willRespondWith()
        .status(200)
        .headers(headers())
        .body("{ \"value\":2.0 }")
        .toPact();
  }

  @Pact(consumer = "consumer", provider = "producer")
  RequestResponsePact multiply_4and2_pact(PactDslWithProvider builder) {
    return builder.given("multiply")
        .uponReceiving("multiply 4 and 2")
        .method("GET")
        .path("/calculate/multiply")
        .matchQuery("first", "4\\.0")
        .matchQuery("second", "2\\.0")
        .willRespondWith()
        .status(200)
        .headers(headers())
        .body("{ \"value\":8.0 }")
        .toPact();
  }

  @Pact(consumer = "consumer", provider = "producer")
  RequestResponsePact divide_4by2_pact(PactDslWithProvider builder) {
    return builder.given("multiply")
        .uponReceiving("divide 4 by 2")
        .method("GET")
        .path("/calculate/divide")
        .matchQuery("first", "4\\.0")
        .matchQuery("second", "2\\.0")
        .willRespondWith()
        .status(200)
        .headers(headers())
        .body("{ \"value\":2.0 }")
        .toPact();
  }

  @Pact(consumer = "consumer", provider = "producer")
  RequestResponsePact sqrt_4_pact(PactDslWithProvider builder) {
    return builder.given("sqrt")
        .uponReceiving("sqrt 4")
        .method("GET")
        .path("/advanced-calculate/sqrt")
        .matchQuery("value", "4\\.0")
        .willRespondWith()
        .status(200)
        .headers(headers())
        .body("{ \"value\":2.0 }")
        .toPact();
  }

  @Pact(consumer = "consumer", provider = "producer")
  RequestResponsePact power_2to2_pact(PactDslWithProvider builder) {
    return builder.given("sqrt")
        .uponReceiving("sqrt 4")
        .method("GET")
        .path("/advanced-calculate/power")
        .matchQuery("base", "2\\.0")
        .matchQuery("exponent", "2\\.0")
        .willRespondWith()
        .status(200)
        .headers(headers())
        .body("{ \"value\":4.0 }")
        .toPact();
  }

  @Pact(consumer = "consumer", provider = "producer")
  RequestResponsePact pi_pact(PactDslWithProvider builder) {
    return builder.given("pi")
        .uponReceiving("pi")
        .method("GET")
        .path("/advanced-calculate/pi")
        .willRespondWith()
        .status(200)
        .headers(headers())
        .body("{ \"value\": " + Math.PI + " }")
        .toPact();
  }

  @Disabled
  @Pact(consumer = "consumer", provider = "producer")
  RequestResponsePact power_2and2_multiply4andPi(PactDslWithProvider builder) {
    return builder.given("multiply")
        .uponReceiving("multiply pi and 4")
        .method("GET")
        .path("/calculate/multiply")
        .matchQuery("first", "3\\.141592653589793")
        .matchQuery("second", "4\\.0")
        .willRespondWith()
        .status(200)
        .headers(headers())
        .body("{ \"value\":12.5663706144 }")

        .uponReceiving("power 2 and 2")
        .method("GET")
        .path("/advanced-calculate/power")
        .matchQuery("base", "2\\.0")
        .matchQuery("exponent", "2\\.0")
        .willRespondWith()
        .status(200)
        .headers(headers())
        .body("{ \"value\":4.0 }")

        .uponReceiving("get pi")
        .method("GET")
        .path("/advanced-calculate/pi")
        .willRespondWith()
        .status(200)
        .headers(headers())
        .body("{ \"value\": 3.141592653589793}")
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "add_2and2_pact")
  void addTest(MockServer mockServer) {
    var calculateClient = createClient(mockServer.getUrl());
    var actual = calculateClient.add(2.0, 2.0);
    assertThat(actual.getValue()).isEqualTo(4.0);
  }

  @Test
  @PactTestFor(pactMethod = "subtract_2from4_pact")
  void subtractTest(MockServer mockServer) {
    var calculateClient = createClient(mockServer.getUrl());
    var actual = calculateClient.subtract(4.0, 2.0);
    assertThat(actual.getValue()).isEqualTo(2.0);
  }

  @Test
  @PactTestFor(pactMethod = "multiply_4and2_pact")
  void multiplyTest(MockServer mockServer) {
    var calculateClient = createClient(mockServer.getUrl());
    var actual = calculateClient.multiply(4.0, 2.0);
    assertThat(actual.getValue()).isEqualTo(8.0);
  }

  @Test
  @PactTestFor(pactMethod = "divide_4by2_pact")
  void divideTest(MockServer mockServer) {
    var calculateClient = createClient(mockServer.getUrl());
    var actual = calculateClient.divide(4.0, 2.0);
    assertThat(actual.getValue()).isEqualTo(2.0);
  }

  @Test
  @PactTestFor(pactMethod = "sqrt_4_pact")
  void sqrtTest(MockServer mockServer) {
    var calculateClient = createClient(mockServer.getUrl());
    var actual = calculateClient.sqrt(4.0);
    assertThat(actual.getValue()).isEqualTo(2.0);
  }

  @Test
  @PactTestFor(pactMethod = "power_2to2_pact")
  void powerTest(MockServer mockServer) {
    var calculateClient = createClient(mockServer.getUrl());
    var actual = calculateClient.power(2.0, 2.0);
    assertThat(actual.getValue()).isEqualTo(4.0);
  }

  @Test
  @PactTestFor(pactMethod = "pi_pact")
  void piTest(MockServer mockServer) {
    var calculateClient = createClient(mockServer.getUrl());
    var actual = calculateClient.pi();
    assertThat(actual.getValue()).isEqualTo(Math.PI);
  }

  private Map<String, String> headers() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    return headers;
  }

  private CalculateClient createClient(String baseUrl) {
    return Feign.builder()
        .client(new OkHttpClient())
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .contract(new SpringMvcContract())
        .target(CalculateClient.class, baseUrl);
  }
}
