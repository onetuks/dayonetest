package com.onetuks.dayonetest;

public class MyCalculator {

  private Double result;

  public MyCalculator() {
    this.result = 0.0;
  }

  public MyCalculator(Double result) {
    this.result = result;
  }

  public MyCalculator add(Double number) {
    this.result += number;
    return this;
  }

  public MyCalculator minus(Double number) {
    this.result -= number;
    return this;
  }

  public MyCalculator multiply(Double number) {
    this.result *= number;
    return this;
  }

  public MyCalculator divide(Double number) {
    if (number == 0) {
      throw new ZeroDevisionException("Cannot divide by zero");
    }

    this.result /= number;
    return this;
  }

  public Double getResult() {
    return this.result;
  }

  public static class ZeroDevisionException extends RuntimeException {
    public ZeroDevisionException(String message) {
      super(message);
    }
  }
}
