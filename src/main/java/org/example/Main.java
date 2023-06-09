package org.example;

import org.example.config.SpringConfig;
import org.example.controller.MainController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        MainController mainController = (MainController) context.getBean("mainController");
        mainController.start();
    }
}