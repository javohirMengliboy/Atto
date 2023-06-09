package org.example;

import org.example.container.ComponentContainer;
import org.example.controller.MainController;
import org.example.util.CardUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        MainController mainController = (MainController) context.getBean("mainController");
        mainController.start();
    }
}