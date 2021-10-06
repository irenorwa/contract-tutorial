package com.ck.lab.contract.consumer;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class FiguresCalculatorControllerTest {

  @Autowired
  MockMvc mockMvc;

  private WireMockServer wireMockServer;

  @Autowired
  FiguresCalculatorController figuresCalculatorController;

  @BeforeEach
  void setUp() {
    wireMockServer = new WireMockServer(8081);
    wireMockServer.start();
  }

  @AfterEach
  void tearDown() {
    wireMockServer.stop();
  }

  @Test
  void shouldCalculateSquareArea() throws Exception {
    // given
    wireMockServer.stubFor(WireMock.get(urlPathEqualTo("/calculate/multiply"))
        .withQueryParam("first", equalTo("2.0"))
        .withQueryParam("second", equalTo("2.0"))
        .willReturn(
            aResponse().withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{ \"value\" : 4 }")
        )
    );
    // when
    mockMvc.perform(get("/figures/calculate/square/area")
            .param("width", "2"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("4.0"));
  }

  @Test
  void shouldCalculateCircleArea() throws Exception {
    // given
    wireMockServer.stubFor(WireMock.get(urlPathEqualTo("/advanced-calculate/pi"))
        .willReturn(
            aResponse().withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{ \"value\" : 3.141592653589793 }")
        )
    );

    wireMockServer.stubFor(WireMock.get(urlPathEqualTo("/advanced-calculate/power"))
        .withQueryParam("base", equalTo("2.0"))
        .withQueryParam("exponent", equalTo("2.0"))
        .willReturn(
            aResponse().withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{ \"value\" : 4.0 }")
        )
    );

    wireMockServer.stubFor(WireMock.get(urlPathEqualTo("/calculate/multiply"))
        .withQueryParam("first", equalTo("3.141592653589793"))
        .withQueryParam("second", equalTo("4.0"))
        .willReturn(
            aResponse().withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{ \"value\" : 12.5663706144 }")
        )
    );
    //when
    mockMvc.perform(get("/figures/calculate/circle/area")
            .param("radial", "2"))
        .andExpect(status().isOk())
        .andExpect(content().string("12.5663706144"));
  }

}
