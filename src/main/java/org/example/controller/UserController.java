package org.example.controller;

import org.example.container.ComponentContainer;
import org.example.service.CardService;
import org.example.service.TransactionService;
import org.example.util.CardUtil;

public class UserController {
    private CardUtil cardUtil;
    private CardService cardService;
    private TransactionService transactionService;

    public void userPage(){
        while (true){
            userMenu();
            int action = MainController.getAction();
            switch (action){
                case 1 -> cardUtil.addCard();
                case 2 -> cardService.cardList(ComponentContainer.profile);
                case 3 -> cardUtil.changeCardStatus();
                case 4 -> cardService.deleteCardByUser(ComponentContainer.profile);
                case 5 -> transactionService.reFill(ComponentContainer.profile);
                case 6 -> transactionService.getTransactionListForUser(ComponentContainer.profile);
                case 7 -> transactionService.makePayment(ComponentContainer.profile);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Wrong selection");

            }
        }
    }

    public void userMenu(){
        System.out.println("""
                1. Add Card
                2. Card List
                3. Card Change Status
                4. Delete Card
                5. ReFill
                6. Transaction List
                7. Make Payment
                """);
    }

    public CardUtil getCardUtil() {
        return cardUtil;
    }

    public void setCardUtil(CardUtil cardUtil) {
        this.cardUtil = cardUtil;
    }

    public CardService getCardService() {
        return cardService;
    }

    public void setCardService(CardService cardService) {
        this.cardService = cardService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


}
