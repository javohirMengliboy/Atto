package org.example.controller;

import org.example.container.ComponentContainer;
import org.example.service.CardService;
import org.example.service.ProfileService;
import org.example.service.TerminalService;
import org.example.service.TransactionService;
import org.example.util.CardUtil;
import org.example.util.ProfileUtil;
import org.example.util.TerminalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdminController {
    @Autowired
    private CardUtil cardUtil ;
    @Autowired
    private CardService cardService ;
    @Autowired
    private TerminalUtil terminalUtil;
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileUtil profileUtil;
    @Autowired
    private TransactionService transactionService;
    public void adminPage(){
        while (true){
            adminMenu();
            String action = ComponentContainer.stringScanner.next();
            switch (action){
                /* Card Control */
                case "1" -> cardUtil.createCard();
                case "2" -> cardService.allCardList();
                case "3" -> cardUtil.updateCard();
                case "4" -> cardUtil.changeCardStatus();
                case "5" -> cardUtil.deleteCard();

                /* Terminal */
                case "6" -> terminalUtil.createTerminal();
                case "7" -> terminalService.getTerminalList();
                case "8" -> terminalUtil.updateTerminal();
                case "9" -> terminalUtil.changeTerminalStatus();
                case "10" -> terminalUtil.deleteTerminal();

                /* Profile Control */
                case "11" -> profileService.showProfileList();
                case "12" -> profileUtil.changeProfileStatus();

                /* Transaction */
                case "13" -> transactionService.showTransactionList();
                case "14" -> cardService.setAttoCard();
                case "15" -> transactionService.todayPayments();
                case "16" -> transactionService.dailyPayments();
                case "17" -> transactionService.showIntermediateTransactions();
                case "18" -> transactionService.showTransactionListByTerminal();
                case "19" -> transactionService.showTransactionListByCard();
                case "*" -> {
                    adminMenu2();
                    String action2 = ComponentContainer.stringScanner.next();
                    switch (action2){
                        case "11" -> profileService.showProfileList();
                        case "12" -> profileUtil.changeProfileStatus();
                        /* Transaction */
                        case "13" -> transactionService.showTransactionList();
                        case "14" -> cardService.setAttoCard();
                        case "15" -> transactionService.todayPayments();
                        case "16" -> transactionService.dailyPayments();
                        case "17" -> transactionService.showIntermediateTransactions();
                        case "18" -> transactionService.showTransactionListByTerminal();
                        case "19" -> transactionService.showTransactionListByCard();
                        case "#" -> adminMenu();
                        default -> {
                            return;
                        }
                    }
                }

                case "0" -> {
                    return;
                }
                default -> System.out.println("Wrong selection");

            }
        }
    }

    private void adminMenu2() {
        System.out.println("""
                11. Profile List
                12. Change Profile Status
                13. Transaction List
                14. Company Card Balance
                15. Today Payments
                16. Daily Payments
                17. Intermediate Payments
                18. Transactions by Terminal
                19. Transaction By Card
                # . <- Prev Page
                """);
    }

    public void adminMenu(){
        System.out.println("""
                1. Create Card
                2. Card List
                3. Update Card
                4. Change Card status
                5. Delete Card
                6. Create Terminal
                7. Terminal List
                8. Update Terminal
                9. Change Terminal Status
                10. Delete Terminal
                * .  Next Page ->
                
                """);
    }

}
