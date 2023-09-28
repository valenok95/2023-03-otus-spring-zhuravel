package com.example.homework02.service.impl;

import com.example.homework02.service.IOService;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IOServiceStream implements IOService {
    private final Scanner in;
    private final PrintStream out;

    public IOServiceStream(@Value("#{ T(java.lang.System).out}") PrintStream out,
                           @Value("#{ T(java.lang.System).in}") InputStream in) {
        this.in = new Scanner(in);
        this.out = out;
    }

    @Override
    public void printString(String message) {
        out.println(message);
    }

    @Override
    public String readString() {
        return in.next();
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        printString(prompt);
        return readString();
    }
}