package org.example.controller;

import org.example.container.ComponentContainer;
import org.example.service.CardService;
import org.example.service.ProfileService;
import org.example.service.TerminalService;
import org.example.service.TransactionService;
import org.example.util.CardUtil;
import org.example.util.ProfileUtil;
import org.example.util.TerminalUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AdminController {
    private CardUtil cardUtil ;
    private CardService cardService ;
    private TerminalUtil terminalUtil;
    private TerminalService terminalService;
    private ProfileService profileService;
    private ProfileUtil profileUtil;
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

    public TerminalUtil getTerminalUtil() {
        return terminalUtil;
    }

    public void setTerminalUtil(TerminalUtil terminalUtil) {
        this.terminalUtil = terminalUtil;
    }

    public TerminalService getTerminalService() {
        return terminalService;
    }

    public void setTerminalService(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    public ProfileService getProfileService() {
        return profileService;
    }

    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    public ProfileUtil getProfileUtil() {
        return profileUtil;
    }

    public void setProfileUtil(ProfileUtil profileUtil) {
        this.profileUtil = profileUtil;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
