package com.example.homework04.controller;

import com.example.homework04.service.TestingService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {
    private final TestingService testingService;

    @ShellMethod(value = "Start testing command", key = {"st", "start"})
    public void startTesting() {
        testingService.testing();
    }
}