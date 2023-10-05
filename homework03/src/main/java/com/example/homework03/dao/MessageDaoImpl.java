package com.example.homework03.dao;

import com.example.homework03.configuration.TestServiceProperties;
import com.example.homework03.domain.Message;
import com.example.homework03.exception.MissingQuestionsException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDaoImpl implements MessageDao {
    private final TestServiceProperties props;

    public MessageDaoImpl(TestServiceProperties props) {
        this.props = props;
    }

    @Override
    public Message getMessage() {
        try (InputStream inputStream = props.getMessageSource().getInputStream()) {
            return readLinesItems(inputStream).stream()
                    .filter(item -> item.get(0).equals("message"))
                    .findFirst()
                    .map(item -> (new Message(item.get(1), item.get(2),
                            item.get(3),
                            item.get(4), item.get(5), item.get(6), item.get(7),
                            item.get(8), item.get(9), item.get(10), item.get(11),
                            item.get(12), item.get(13)))).orElseThrow();
        } catch (Exception e) {
            throw new MissingQuestionsException("Missing message params", e);
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
