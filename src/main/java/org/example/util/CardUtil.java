package org.example.util;

import org.example.container.ComponentContainer;
import org.example.dto.Card;
import org.example.repository.CardRepository;
import org.example.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CardUtil {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
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

}
