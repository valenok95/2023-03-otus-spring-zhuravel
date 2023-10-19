package com.example.homework03.service;

import com.example.homework03.configuration.ResourceProvider;
import com.example.homework03.configuration.TestServiceProperties;
import com.example.homework03.dao.QuestionDao;
import com.example.homework03.dao.QuestionDaoImpl;
import com.example.homework03.dao.UserDao;
import com.example.homework03.service.impl.IOServiceStream;
import com.example.homework03.service.impl.LocalizeServiceImpl;
import com.example.homework03.service.impl.TestingServiceImpl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
class TestingServiceTest {
    @Autowired
    UserDao userDao;
    @Autowired
    TestServiceProperties props;
    @Autowired
    LocalizeServiceImpl localizeService;
    @Autowired
    ResourceProvider resourceProvider;

    @Test
    void testingTest() throws IOException {
        Resource resource = Mockito.mock(Resource.class);
        InputStream inputStreamIn = new ByteArrayInputStream(
                ("id,title,question,answer,option1,option2,option3\n" +
                        "1,HEAD,BODY,ANSWER,WRONG_ANSWER,ANSWER,NOT_ANSWER").getBytes()
        );
        Mockito.when(resource.getInputStream()).thenReturn(inputStreamIn);

        QuestionDao questionDao = new QuestionDaoImpl(resourceProvider);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        InputStream inputStream = new ByteArrayInputStream("Valentin\nPetrov\n3".getBytes());
        IOService ioService = new IOServiceStream(printStream, inputStream);

        TestingService testingService = new TestingServiceImpl(props, questionDao,
                userDao,
                ioService, localizeService);
        testingService.testing();

        String resultOutput = outputStream.toString();
        List<String> lines = resultOutput.lines().toList();
        System.out.println(lines);

        Assertions.assertTrue(lines.contains("Please, enter your first name:"));
        Assertions.assertTrue(lines.contains("Please, enter your last name:"));
        Assertions.assertTrue(lines.contains("Hello, Valentin Petrov , welcome to test"));
        Assertions.assertTrue(lines.contains("We have 1 questions for you"));
        Assertions.assertTrue(lines.contains("You need to do at least 3 right answers"));
        Assertions.assertTrue(lines.contains("Result for Valentin Petrov"));
        Assertions.assertTrue(lines.contains("You have answered at 0/1 questions."));
        Assertions.assertTrue(lines.contains("Test failed..."));
    }
}
