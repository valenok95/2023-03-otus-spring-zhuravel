package ru.otus.otusspringstudy.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.otusspringstudy.domain.Question;

public class QuestionDaoSimple implements QuestionDao {
    private final Resource fileResource;

    public QuestionDaoSimple(String filePath) {
        fileResource = new ClassPathResource(filePath);

    }

    @Override
    public List<Question> readQuestions() throws IOException {
        List<Question> resultList = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        System.out.println("here's fileResource " + fileResource);
        System.out.println("here's fileResource " + fileResource);
        System.out.println("here's file " + fileResource.getFilename());
        System.out.println("here's file " + fileResource.getFile());
        try (
                FileReader filereader = new FileReader(fileResource.getFile());
                CSVReader csvReader = new CSVReaderBuilder(filereader)
                        .withCSVParser(parser)
                        .build();
        ) {
            List<String[]> strings = csvReader.readAll();
            strings.forEach(s -> {
                resultList.add(parseStringArrayToQuestion(s));
            });
        } catch (CsvException | IOException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    private Question parseStringArrayToQuestion(String[] strings) {
        Question question = new Question();
        question.setText(strings[0]);
        List<String> ansList = new ArrayList<>();
        if (strings.length > 1) {
            for (int i = 1; i < strings.length - 1; i++) {
                ansList.add(strings[i]);
            }
            question.setAns(ansList);
        }
        return question;
    }

}
