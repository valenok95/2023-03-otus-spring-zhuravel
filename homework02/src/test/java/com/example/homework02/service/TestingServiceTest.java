package com.example.homework02.service;

import com.example.homework02.dao.QuestionDao;
import com.example.homework02.dao.QuestionDaoImpl;
import com.example.homework02.service.impl.IOServiceStream;
import com.example.homework02.service.impl.TestingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.List;

public class TestingServiceTest {

    @Test
    void testingTest() throws IOException {
        Resource resource = Mockito.mock(Resource.class);
        InputStream inputStreamIn = new ByteArrayInputStream(
                ("id,title,question,answer,option1,option2,option3\n" +
                        "1,HEAD,BODY,ANSWER,WRONG_ANSWER,ANSWER,NOT_ANSWER").getBytes()
        );
        Mockito.when(resource.getInputStream()).thenReturn(inputStreamIn);

        QuestionDao questionDao = new QuestionDaoImpl(resource);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        InputStream inputStream = new ByteArrayInputStream("ANSWER".getBytes());
        IOService ioService = new IOServiceStream(printStream, inputStream);

        TestingService testingService = new TestingServiceImpl(3, questionDao, ioService);
        testingService.testing();

        String resultOutput = outputStream.toString();
        List<String> lines = resultOutput.lines().toList();
        System.out.println(lines);

        Assertions.assertTrue(lines.contains("You need do do at least 3 right answers"));
        Assertions.assertTrue(lines.contains("true"));
        Assertions.assertTrue(lines.contains("Result"));
        Assertions.assertTrue(lines.contains("Test failed..."));
    }
}
