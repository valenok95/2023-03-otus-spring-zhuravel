package com.example.homework03.config;

import com.example.homework03.configuration.TestServiceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TestServiceProperties.class)
public class ApplicationTestConfig {
}
