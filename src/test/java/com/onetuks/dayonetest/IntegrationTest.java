package com.onetuks.dayonetest;

import com.redis.testcontainers.RedisContainer;
import jakarta.transaction.Transactional;
import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.junit.Ignore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@Ignore // 부모클래스인 IntergraionTest 는 테스트를 실행할 필요가 없으므로 @Ignore 어노테이션을 붙여준다.
@Transactional
@SpringBootTest
@ContextConfiguration(initializers = IntegrationTest.IntegrationTestIntializer.class) // 이 어노테이션으로 ApplicatinoContext 초기화 시 커스텀한 설정으로 생성된다
public class IntegrationTest {

    static DockerComposeContainer rdbms;
    static RedisContainer redis;
    static LocalStackContainer aws;

    static {
        rdbms = new DockerComposeContainer(new File("infra/test/docker-compose.yaml"))
                .withExposedService(
                        "local-db",
                        3306,
                        Wait.forLogMessage(".*ready for connections.*", 1)
                                .withStartupTimeout(Duration.ofSeconds(300))
                )
                .withExposedService(
                        "local-db-migrate",
                        0,
                        Wait.forLogMessage("(.*Successfully applied.*)|(.*Successfully validated.*)", 1)
                                .withStartupTimeout(Duration.ofSeconds(300))
                );

        rdbms.start();

        redis = new RedisContainer(RedisContainer.DEFAULT_IMAGE_NAME.withTag("6"));
        redis.start();

        aws = (new LocalStackContainer())
                .withServices(LocalStackContainer.Service.S3)
                .withStartupTimeout(Duration.ofSeconds(600));
        aws.start();
    }

    // ApplicationContext 가 처음 초기화될 때 TestContainers 속성이 아니라, application.properties 설정값을 사용하기 때문에 DB 연결 안 되는 문제 해결하려는 목적
    // ApplicationContext 생성 시 TestContainers 를 통해 만든 rdbms 호스트, 포트를 사용하여 테스트용 DB에 연결한다.
    static class IntegrationTestIntializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            Map<String, String> properties = new HashMap<>();

            String rdbmsHost = rdbms.getServiceHost("local-db", 3306);
            int rdbmsPort = rdbms.getServicePort("local-db", 3306);

            properties.put("spring.datasource.url", "jdbc:mysql://" + rdbmsHost + ":" + rdbmsPort + "/score");

            String redistHost = redis.getHost();
            int redisPort = redis.getFirstMappedPort();

            properties.put("spring.data.redis.host", redistHost);
            properties.put("spring.data.redis.port", String.valueOf(redisPort));

            try {
                aws.execInContainer(
                        "awslocal",
                        "s3api",
                        "create-bucket",
                        "--bucket",
                        "test-bucket"
                );

                properties.put("aws.endpoint", aws.getEndpoint().toString());
            } catch (Exception e) {
                // ignore
            }

            TestPropertyValues.of(properties)
                    .applyTo(applicationContext);
        }
    }

}
