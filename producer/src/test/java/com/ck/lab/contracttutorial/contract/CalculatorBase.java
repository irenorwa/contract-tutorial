package com.ck.lab.contracttutorial.contract;

import com.ck.lab.contracttutorial.calculator.CalculateController;
import io.restassured.config.EncoderConfig;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public abstract class CalculatorBase {

  @Autowired
  WebApplicationContext webApplicationContext;

  CalculateController calculateController = new CalculateController();

  @BeforeEach
  public void setup() {
    EncoderConfig encoderConfig = new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false);
    RestAssuredMockMvc.config = new RestAssuredMockMvcConfig().encoderConfig(encoderConfig);
    RestAssuredMockMvc.webAppContextSetup(this.webApplicationContext);

    var standAloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(calculateController);
    RestAssuredMockMvc.standaloneSetup(standAloneMockMvcBuilder);
  }

}
