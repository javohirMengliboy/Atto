package org.example.controller;

import org.example.container.ComponentContainer;
import org.example.service.CardService;
import org.example.service.TransactionService;
import org.example.util.CardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    @Autowired
    private CardUtil cardUtil;
    @Autowired
    private CardService cardService;
    @Autowired
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


}
