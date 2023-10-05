package com.example.homework03.dao;

import com.example.homework03.configuration.TestServiceProperties;
import com.example.homework03.exception.MissingQuestionsException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
public class QuestionDaoImplTest {
    TestServiceProperties props;

    public QuestionDaoImplTest(@Autowired TestServiceProperties props) {
        this.props = props;
    }

    @Test
    void getAllTest() throws IOException {
        Resource resource = Mockito.mock(Resource.class);
        InputStream inputStreamIn = new ByteArrayInputStream(
                ("id,title,question,answer,option1,option2,option3\n" +
                        "question,1,question.title.addition,question.text,2+2?,4,3,4,6,7").getBytes()
        );
        Mockito.when(resource.getInputStream()).thenReturn(inputStreamIn);

        QuestionDao questionDao = new QuestionDaoImpl(props);
        var result = questionDao.getAll();
        Assertions.assertEquals(1, result.size());
    }
}
