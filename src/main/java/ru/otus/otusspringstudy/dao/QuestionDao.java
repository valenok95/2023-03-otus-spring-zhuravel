package ru.otus.otusspringstudy.dao;

import java.io.IOException;
import java.util.List;
import ru.otus.otusspringstudy.domain.Question;

public interface QuestionDao {

    List<Question> readQuestions() throws IOException;
}
