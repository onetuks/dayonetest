package com.onetuks.dayonetest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

  public void process(String message) {
    log.info("processing ... " + message);
  }
}
