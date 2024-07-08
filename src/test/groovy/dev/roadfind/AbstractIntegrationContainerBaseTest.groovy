package dev.roadfind;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import spock.lang.Specification;

@SpringBootTest
abstract class AbstractIntegrationContainerBaseTest extends Specification {

    // Redis 는 testcontainer 의존성 모듈로 지원하지 않아서
    // test container 에서 제공하는 GenericContainer 를 이용해 연결
    static final GenericContainer MY_REDIS_CONTAINER

    static {
        MY_REDIS_CONTAINER = new GenericContainer<>("redis:7")
        .withExposedPorts(6379)
        MY_REDIS_CONTAINER.start()
        System.setProperty("spring.data.redis.host", MY_REDIS_CONTAINER.getHost())
        System.setProperty("spring.data.redis.port", MY_REDIS_CONTAINER.getMappedPort(6379).toString())
    }

}
