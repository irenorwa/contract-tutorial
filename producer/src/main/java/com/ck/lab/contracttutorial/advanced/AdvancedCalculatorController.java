package com.ck.lab.contracttutorial.advanced;

import com.ck.lab.contracttutorial.calculator.CalculateController.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advanced-calculate")
public class AdvancedCalculatorController {

  @GetMapping(value = "/sqrt", produces = "application/json")
  public ResponseEntity<Result> sqrt(@RequestParam("value") Double value) {
    return ResponseEntity.ok(new Result(Math.sqrt(value)));
  }

  @GetMapping(value = "/power", produces = "application/json")
  public ResponseEntity<Result> divide(
      @RequestParam("base") Double base,
      @RequestParam("exponent") Double exponent) {
    return ResponseEntity.ok(new Result(Math.pow(base,exponent)));
  }

  @GetMapping(value = "/pi", produces = "application/json")
  public ResponseEntity<Result> pi() {
    return ResponseEntity.ok(new Result(Math.PI));
  }
}
