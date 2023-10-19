package com.example.homework02.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:applicationTest.properties")
public class ApplicationTestConfig {
}
