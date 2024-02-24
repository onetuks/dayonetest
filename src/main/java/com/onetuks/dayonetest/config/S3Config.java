package com.onetuks.dayonetest.config;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

  @Value("${aws.endpoint}")
  private String endpoint;

  /**
   * AWS 자격 증명 공급자를 생성합니다.
   *
   * @return
   */
  @Bean
  public AwsCredentialsProvider awsCredentialsProvider() {
    return AwsCredentialsProviderChain.builder()
        .reuseLastProviderEnabled(true)
        .credentialsProviders(
            List.of(
                DefaultCredentialsProvider.create(),
                StaticCredentialsProvider.create(AwsBasicCredentials.create("foo", "bar"))))
        .build();
  }

  /**
   * S3 클라이언트를 생성합니다. -> 테스트용
   *
   * @return
   */
  @Bean
  public S3Client s3Client() {
    return S3Client.builder()
        .credentialsProvider(awsCredentialsProvider())
        .region(Region.AP_NORTHEAST_2)
        .endpointOverride(URI.create(endpoint))
        .build();
  }
}
