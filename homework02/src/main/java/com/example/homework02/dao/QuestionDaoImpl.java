package com.example.homework02.dao;

import com.example.homework02.domain.Answer;
import com.example.homework02.domain.Question;
import com.example.homework02.exception.MissingQuestionsException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final Resource questionFile;

    public QuestionDaoImpl(@Value("${questions.fileName}") Resource questionFile) {
        this.questionFile = questionFile;
    }

    @Override
    public List<Question> getAll() {
        try (InputStream inputStream = questionFile.getInputStream()) {
            return readLinesItems(inputStream).stream()
                    .skip(1)
                    .map(item -> {
                                Long id = Long.valueOf(item.get(0));
                                String title = item.get(1);
                                String text = item.get(2);
                                String correctAnswer = item.get(3);
                                List<Answer> answers = new ArrayList<>();
                                for (int i = 4; i < item.size(); i++) {
                                    String value = item.get(i);
                                    var answer = new Answer(value, correctAnswer.equalsIgnoreCase(value));
                                    answers.add(answer);
                                }
                                return new Question(id, title, text, answers);
                            }
                    ).collect(Collectors.toList());
        } catch (Exception e) {
            throw new MissingQuestionsException("Missing list of questions", e);
        }

    }

    private List<List<String>> readLinesItems(InputStream inputStream) {
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException("Wrong file structure", e);
        }
        return records;
    }
}
