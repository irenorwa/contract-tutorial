package com.ck.lab.contract.consumer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureStubRunner(
    ids = "com.ck.lab:producer:+:stubs:8081",
    stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class FiguresCalculatorControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  FiguresCalculatorController figuresCalculatorController;

  @Test
  void shouldCalculateSquareArea() throws Exception {
    mockMvc.perform(get("/figures/calculate/square/area")
            .param("width", "2"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldCalculateCircleArea() throws Exception {
    mockMvc.perform(get("/figures/calculate/circle/area")
            .param("radial", "2"))
        .andExpect(status().isOk());
  }

}
