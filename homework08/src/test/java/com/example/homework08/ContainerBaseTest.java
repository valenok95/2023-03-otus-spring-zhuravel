package com.example.homework08;

import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class ContainerBaseTest {

    @Container
    public static GenericContainer mongoDBContainer = new GenericContainer("mongo:5.0")
            .withExposedPorts(27017)
            .withEnv("MONGO_INITDB_DATABASE", "test")
            .withEnv("MONGO_INITDB_ROOT_USERNAME", "root")
            .withEnv("MONGO_INITDB_ROOT_PASSWORD", "root");

    static {
        mongoDBContainer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> mongoDBContainer.stop()));
    }

    @DynamicPropertySource
    static void mongoProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
        registry.add("spring.data.mongodb.authentication-database", () -> "admin");
        registry.add("spring.data.mongodb.database", () -> "test");
        registry.add("spring.data.mongodb.username", () -> "root");
        registry.add("spring.data.mongodb.password", () -> "root");
    }
    
    /*
      @BeforeAll
    public static void beforeAll() {
        mongoDBContainer.start();
    }
     */
}
