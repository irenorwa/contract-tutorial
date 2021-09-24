package com.ck.lab.contract.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "calculator", url = "http://localhost:8081")
public interface CalculateClient {

  @RequestMapping(method = RequestMethod.GET, value = "/calculate/add")
  Result add(@RequestParam("first")  Double first, @RequestParam("second") Double second);

  @RequestMapping(method = RequestMethod.GET, value = "/calculate/subtract")
  Result subtract(@RequestParam("first") Double first,@RequestParam("second")  Double second);

  @RequestMapping(method = RequestMethod.GET, value = "/calculate/multiply")
  Result multiply(@RequestParam("first") Double first,@RequestParam("second")  Double second);

  @RequestMapping(method = RequestMethod.GET, value = "/calculate/divide")
  Result divide(@RequestParam("first") Double first,@RequestParam("second")  Double second);

  @RequestMapping(method = RequestMethod.GET, value = "/advanced-calculate/sqrt")
  Result sqrt(@RequestParam("value") Double value);

  @RequestMapping(method = RequestMethod.GET, value = "/advanced-calculate/power")
  Result power(@RequestParam("base") Double base,@RequestParam("exponent")  Double exponent);

  @RequestMapping(method = RequestMethod.GET, value = "/advanced-calculate/pi")
  Result pi();

}
