package ru.otus.otusspringstudy.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.otus.otusspringstudy.domain.Question;
import ru.otus.otusspringstudy.exception.ReadResourceException;

@Slf4j
@Repository
public class QuestionDaoSimple implements QuestionDao {
    private final Resource fileResource;

    public QuestionDaoSimple(@Value("${csv-source}") String filePath) {
        fileResource = new ClassPathResource(filePath);
    }

    @Override
    public List<Question> readQuestions() {
        List<Question> resultList = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        log.info("here's fileResource " + fileResource);
        try (
                Reader reader = new InputStreamReader(fileResource.getInputStream());
                CSVReader csvReader = new CSVReaderBuilder(reader)
                        .withCSVParser(parser)
                        .build();
        ) {
            List<String[]> strings = csvReader.readAll();
            strings.forEach(s -> {
                resultList.add(parseStringArrayToQuestion(s));
            });
        } catch (CsvException | IOException e) {
            log.error(e.getMessage());
            throw new ReadResourceException(e.getMessage());
        }
        return resultList;
    }

    private Question parseStringArrayToQuestion(String[] strings) {
        Question question = new Question();
        question.setText(strings[0]);
        List<String> ansList = new ArrayList<>();
        if (strings.length > 2) {
            for (int i = 1; i < strings.length - 2; i++) {
                ansList.add(strings[i]);
            }
            question.setAns(ansList);
            int indexOfRightAnswer = Integer.parseInt(strings[strings.length-2]);
            question.setIndexOfRightAnswer(indexOfRightAnswer);
        }
        return question;
    }

}
