package org.example.service;

import org.example.container.ComponentContainer;
import org.example.dto.Card;
import org.example.dto.Profile;
import org.example.enums.CardStatus;
import org.example.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository ;

    public void checkAttoCardForCreate(Card card){
        String temp = String.valueOf(card.getNumber());
        if(temp.length() != 16){
            System.out.println("Card number invalid");
            return;
        }

        if (card.getExp_date().length() != 4){
            System.out.println("Exp_date invalid");
            return;
        }

        card.setStatus(CardStatus.ATTO);
        boolean isCreate = cardRepository.createAttoCard(card);
        if (isCreate){
            System.out.println("Atto Card created");
        }else {
            System.out.println("Atto Card not created");
        }

    }
    public void checkCardForCreate(Card card){
        String temp = String.valueOf(card.getNumber());
        if(temp.length() != 16){
            System.out.println("Card number invalid");
            return;
        }

        if (card.getExp_date().length() != 4){
            System.out.println("Exp_date invalid");
            return;
        }

        card.setStatus(CardStatus.BLOCK);
        boolean isCreate = cardRepository.createCard(card);
        if (isCreate){
            System.out.println("Card created");
        }else {
            System.out.println("Card not created");
        }

    }

    public void addCard(Long number) {
        Card card = cardRepository.getCardByNumber(number);
        if (card == null){
            System.out.println("Card not found");
            return;
        }

        if (card.getProfile_id() != null){
            System.out.println("This card already busy");
            return;
        }

        card.setProfile_id(ComponentContainer.profile.getId());
        card.setPhone(ComponentContainer.profile.getPhone());
        card.setStatus(CardStatus.ACTIVE);
        card.setCreated_date(LocalDateTime.now());
        int result = cardRepository.addCard(card, number);
        if (result == 1){
            System.out.println("Card added");
        }else {
            System.out.println("Card not added");
        }
    }

    public void cardList(Profile profile) {
        List<Card> cardList = cardRepository.getCardList(profile);
        if (cardList.size() == 0) {
            System.out.println("List empty");
            return;
        }
        cardList.forEach((n)->{
            System.out.println(n.getNumber()+" "+ n.getExp_date() + " "+n.getBalance());
        });

    }

    public void allCardList() {
        List<Card> cardList = cardRepository.getAllCardList();
        if (cardList.size() == 0) {
            System.out.println("List empty");
            return;
        }
        cardList.forEach((n)->{
            System.out.println(n.getNumber()+" "+ n.getExp_date() + " "+n.getBalance());
        });
    }

    public void changeCardStatus(Long number) {
        Card card = cardRepository.getCardByNumber(number);
        if (card == null){
            System.out.println("Card not found");
            return;
        }

        CardStatus status;
        if (card.getStatus().equals(CardStatus.ACTIVE)){
            System.out.println("""
                    Card status is ACTIVE
                    Do you want to BLOCK
                    1. Yes""");
            int action = ComponentContainer.intScanner.nextInt();
            if(action != 1){
                return;
            }
            status = CardStatus.BLOCK;
        }else if (card.getStatus().equals(CardStatus.BLOCK)){
            System.out.println("""
                    Card status is BLOCK
                    Do you want to ACTIVE
                    1. Yes""");
            int action = ComponentContainer.intScanner.nextInt();
            if(action != 1){
                return;
            }
            status = CardStatus.ACTIVE;
        }else {
            System.out.println("Card status is INVISIBLE");
            return;
        }

        boolean result = cardRepository.changeCardStatus(number, status);
        if(result){
            System.out.println("Status changed");
        }else {
            System.out.println("Status not changed");
        }
    }

    public void updateCard(Long number, String exp_date) {
        Card card = cardRepository.getCardByNumber(number);
        if (card == null){
            System.out.println("Card not found");
            return;
        }
        boolean bool = cardRepository.cardUpdate(number, exp_date);
        if (bool){
            System.out.println("Card Update");
        }else {
            System.out.println("Card not update");
        }
    }

    public void deleteCard(Long number) {
        Card card = cardRepository.getCardByNumber(number);
        if (card == null){
            System.out.println("Card not found");
            return;
        }
        boolean bool = cardRepository.deleteCard(number);
        if (bool){
            System.out.println("Card deleted");
        }else {
            System.out.println("Card not deleted");
        }
    }

    public void deleteCardByUser(Profile profile) {
        List<Card> cardList = cardRepository.getCardList(profile);
        if (cardList.size() == 0){
            System.out.println("Card not found");
            return;
        }

        int index;
        do {
            int n = 1;
            for (Card card : cardList) {
                System.out.println((n++) + ". " + card.toString());
            }
            System.out.println("Choose card!");
            index = ComponentContainer.intScanner.nextInt();
            System.out.println("Wrong");
        } while (index > cardList.size() || index <= 0);

        Long number = cardList.get(index).getNumber();
        boolean bool = cardRepository.deleteCardByUser(number);
        if (bool){
            System.out.println("Card deleted");
        }else {
            System.out.println("Card not deleted");
        }
    }

    public void setAttoCard(){
        Double balance = cardRepository.getAttoCard().getBalance();
        System.out.println("Atto Balance = "+balance);
    }



}
