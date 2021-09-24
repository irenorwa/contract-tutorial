package com.ck.lab.contract.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/figures/calculate")
public class FiguresCalculatorController {

  private final AreaService areaService;

  @GetMapping(value = "/square/area", produces = "application/json")
  public ResponseEntity<Double> squareArea(@RequestParam("width") Double width) {
    return ResponseEntity.ok(areaService.calculateSquareArea(width));
  }

  @GetMapping(value = "/circle/area", produces = "application/json")
  public ResponseEntity<Double> circleArea(@RequestParam("radial") Double radial) {
    return ResponseEntity.ok(areaService.calculateCircleArea(radial));
  }

  @GetMapping(value = "/rectangle/area", produces = "application/json")
  public ResponseEntity<Double> rectangleArea(@RequestParam("width") Double width,@RequestParam("height") Double height) {
    return ResponseEntity.ok(areaService.calculateRectangleArea(width,height));
  }

}
