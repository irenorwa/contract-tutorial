package com.ck.lab.contracttutorial.calculator;

import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculate")
public class CalculateController {

  @Value
  public static class Result {
    double value;
  }

  @GetMapping(value = "/add", produces = "application/json")
  public ResponseEntity<Result> add(
      @RequestParam("first") Double first,
      @RequestParam("second") Double second) {
    return ResponseEntity.ok(new Result(first + second));
  }

  @GetMapping(value = "/subtract", produces = "application/json")
  public ResponseEntity<Result> subtrack(
      @RequestParam("first") Double first,
      @RequestParam("second") Double second) {
    return ResponseEntity.ok(new Result(first - second));
  }

  @GetMapping(value = "/multiply", produces = "application/json")
  public ResponseEntity<Result> multiply(
      @RequestParam("first") Double first,
      @RequestParam("second") Double second) {
    return ResponseEntity.ok(new Result(first * second));
  }

  @GetMapping(value = "/divide", produces = "application/json")
  public ResponseEntity<Result> divide(
      @RequestParam("first") Double first,
      @RequestParam("second") Double second) {
    return ResponseEntity.ok(new Result(first / second));
  }

}
