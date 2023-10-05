package com.example.homework03.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TestServiceProperties.class)
public class ApplicationConfig {
}
