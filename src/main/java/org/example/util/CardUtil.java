package org.example.util;

import org.example.container.ComponentContainer;
import org.example.dto.Card;
import org.example.dto.Profile;
import org.example.dto.Terminal;
import org.example.dto.Transaction;
import org.example.enums.TransactionType;
import org.example.repository.CardRepository;
import org.example.service.CardService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Scanner;

public class CardUtil {
    private CardRepository cardRepository;
    private CardService cardService;
    public void createAttoCard(){

        System.out.println("Enter number");
        Long number = ComponentContainer.intScanner.nextLong();

        Card temp = cardRepository.getCardByNumber(number);
        if(temp != null){
            System.out.println("This card already exists");
            return;
        }

        System.out.println("Enter exp_date");
        String exp_date = ComponentContainer.stringScanner.next();

        Card card = new Card();
        card.setNumber(number);
        card.setExp_date(exp_date);

        cardService.checkAttoCardForCreate(card);
    }

    public void createCard(){

        System.out.println("Enter number");
        Long number = ComponentContainer.intScanner.nextLong();

        Card temp = cardRepository.getCardByNumber(number);
        if(temp != null){
            System.out.println("This card already exists");
            return;
        }

        System.out.println("Enter exp_date");
        String exp_date = ComponentContainer.stringScanner.next();

        Card card = new Card();
        card.setNumber(number);
        card.setExp_date(exp_date);

        cardService.checkCardForCreate(card);
    }

    public void addCard() {
        System.out.println("Enter number!");
        Long number = ComponentContainer.intScanner.nextLong();

        cardService.addCard(number);
    }



    public void changeCardStatus() {
        System.out.println("Enter number!");
        Long number = ComponentContainer.intScanner.nextLong();

        cardService.changeCardStatus(number);
    }

    public void updateCard() {
        System.out.println("Enter number!");
        Long number = ComponentContainer.intScanner.nextLong();

        System.out.println("Enter new exp_date!");
        String exp_date = ComponentContainer.stringScanner.next();

        cardService.updateCard(number, exp_date);
    }

    public void deleteCard() {
        cardService.allCardList();

        System.out.println("Enter number!");
        Long number = ComponentContainer.intScanner.nextLong();

        cardService.deleteCard(number);
    }

    public CardService getCardService() {
        return cardService;
    }

    public void setCardService(CardService cardService) {
        this.cardService = cardService;
    }

    public CardRepository getCardRepository() {
        return cardRepository;
    }

    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
}
