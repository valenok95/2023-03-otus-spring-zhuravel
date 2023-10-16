package com.example.homework04.dao;

import com.example.homework04.configuration.ResourceProvider;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class QuestionDaoImplTest {
    ResourceProvider resourceProvider;

    public QuestionDaoImplTest(@Autowired ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    @Test
    void getAllTest() throws IOException {
        Resource resource = Mockito.mock(Resource.class);
        InputStream inputStreamIn = new ByteArrayInputStream(
                ("id,title,question,answer,option1,option2,option3\n" +
                        "question,1,question.title.addition,question.text,2+2?,4,3,4,6,7").getBytes()
        );
        Mockito.when(resource.getInputStream()).thenReturn(inputStreamIn);

        QuestionDao questionDao = new QuestionDaoImpl(resourceProvider);
        var result = questionDao.getAll();
        Assertions.assertEquals(1, result.size());
    }
}
