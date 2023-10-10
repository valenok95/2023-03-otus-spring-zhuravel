package com.example.homework03.dao;

import com.example.homework03.configuration.TestServiceProperties;
import com.example.homework03.domain.Answer;
import com.example.homework03.domain.Question;
import com.example.homework03.exception.MissingQuestionsException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDaoImpl implements QuestionDao {
    private final TestServiceProperties props;


    public QuestionDaoImpl(TestServiceProperties props) {
        this.props = props;
    }

    @Override
    public List<Question> getAll() {
        try (InputStream inputStream = props.getResource().getInputStream()) {
            return readLinesItems(inputStream).stream()
                    .filter(item -> item.get(0).equals("question"))
                    .map(item -> {
                                Long id = Long.valueOf(item.get(1));
                                String title = item.get(2);
                                String text = item.get(3);
                                String task = item.get(4);
                                String correctAnswer = item.get(5);
                                List<Answer> answers = new ArrayList<>();
                                for (int i = 6; i < item.size(); i++) {
                                    String value = item.get(i);
                                    var answer = new Answer(value, correctAnswer.equalsIgnoreCase(value));
                                    answers.add(answer);
                                }
                                return new Question(id, title, text, task, answers);
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
