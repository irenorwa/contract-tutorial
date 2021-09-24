package com.ck.lab.contract.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AreaService {
  private final CalculateClient calculateClient;

  public Double calculateSquareArea(Double width){
    return calculateClient.multiply(width,width).getValue();
  }

  public Double calculateRectangleArea(Double width,Double height){
    return calculateClient.multiply(width,height).getValue();
  }

  public Double calculateCircleArea(Double radial){
    var pi = calculateClient.pi().getValue();
    var radialPower = calculateClient.power(radial,2D);
    return calculateClient.multiply(pi,radialPower.getValue()).getValue();
  }


}
