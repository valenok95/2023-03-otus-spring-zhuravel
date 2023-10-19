package com.example.homework02.dao;

import com.example.homework02.exception.MissingQuestionsException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.Resource;

public class QuestionDaoImplTest {

    @Test
    void getAllTest() throws IOException {
        Resource resource = Mockito.mock(Resource.class);
        InputStream inputStreamIn = new ByteArrayInputStream(
                ("id,title,question,answer,option1,option2,option3\n" +
                        "1,HEAD,BODY,ANSWER,WRONG_ANSWER,ANSWER,NOT_ANSWER").getBytes()
        );
        Mockito.when(resource.getInputStream()).thenReturn(inputStreamIn);

        QuestionDao questionDao = new QuestionDaoImpl(resource);
        var result = questionDao.getAll();
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void getAllThrowMissingQuestionsExceptionTest() throws IOException {
        Resource resource = Mockito.mock(Resource.class);
        Mockito.when(resource.getInputStream()).thenReturn(null);

        QuestionDao questionDao = new QuestionDaoImpl(resource);
        Assertions.assertThrows(MissingQuestionsException.class, questionDao::getAll);
    }
}
