package org.example.service;

import org.example.container.ComponentContainer;
import org.example.dto.Card;
import org.example.dto.Profile;
import org.example.dto.Terminal;
import org.example.dto.Transaction;
import org.example.repository.CardRepository;
import org.example.repository.TerminalRepository;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TerminalRepository terminalRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    public void reFill(Profile profile) {
        List<Card> cardList = cardRepository.getCardList(profile);
        if (cardList.size() == 0){
            System.out.println("Card not found");
            return;
        }


        Long number = getCardNumber(cardList);

        System.out.println("Enter amount !");
        Double amount = ComponentContainer.doubleScanner.nextDouble();

        boolean isReFill;
        isReFill = transactionRepository.saveReFill(number, amount);

        if (isReFill){
            System.out.println("Card ReFill");
            System.out.println("Transaction save");
        }else {
            System.out.println("Transaction not save");
        }
    }

    public void makePayment(Profile profile) {
        List<Card> cardList = cardRepository.getCardList(profile);
        long number = getCardNumber(cardList);

//        List<Terminal> terminalList = ComponentContainer.terminalRepository.getTerminalList();
//        String code = getTerminalCode(terminalList);
//
//        System.out.println("Enter amount !");
//        Double amount = ComponentContainer.doubleScanner.nextDouble();
//
//        Transaction transaction = new Transaction();
//        transaction.setCard_number(cardNumber);
//        transaction.setAmount(amount);
//        transaction.setTerminal_code(code);
//        transaction.setType(TransactionType.Payment);

//        System.out.println("Enter card number");
//        Long number = ComponentContainer.intScanner.nextLong();

        System.out.println("Enter terminal code");
        String code = ComponentContainer.stringScanner.next();

        double amount = 1400.0;
        Long atto_number = ComponentContainer.attoCard.getNumber();



        boolean bool = transactionRepository.makePayment(number,code,amount,atto_number);
        if (bool){
            System.out.println("Card Payment");
        }else {
            System.out.println("Card not Payment");
        }
    }

    private long getCardNumber(List<Card> cardList) {
        if (cardList.size() == 0){
            System.out.println("Card not found");
            return 0;
        }

        int index;
        while (true){
            int i = 1;
            for (Card card : cardList) {
                System.out.println((i++)+". "+card.toString());
            }
            System.out.println("Choose card");
            index = ComponentContainer.intScanner.nextInt();
            if (index > 0 && index <= cardList.size()) {
                break;
            }
        }

        return  cardList.get(index-1).getNumber();
    }

    private String getTerminalCode(List<Terminal> terminalList) {
        if (terminalList.size() == 0){
            return  "Terminal not found";
        }

        int index;
        do {
            int n = 1;
            for (Terminal terminal : terminalList) {
                System.out.println((n++) + ". " + terminal.toString());
            }
            System.out.println("Choose card!");
            index = ComponentContainer.intScanner.nextInt();
            System.out.println("Wrong");
        } while (index > terminalList.size() || index <= 0);

        return  terminalList.get(index).getCode();
    }

    public void getTransactionListForUser(Profile profile) {
        List<String> transactionList = transactionRepository.getTransactionListForUser(profile.getId());
        if (transactionList.size() == 0){
            System.out.println("Transaction not found");
            return;
        }

        transactionList.forEach(System.out::println);
    }

    public void showTransactionList() {
        List<Transaction> transactionList = transactionRepository.getTransactionList();
        if (transactionList.size() == 0){
            System.out.println("Transaction List is empty");
            return;
        }

        transactionList.forEach(System.out::println);
    }

    public void todayPayments() {
        LocalDate localDate = LocalDate.now();
        List<Transaction> transactionList = transactionRepository.todayTransactionsList(localDate);
        if (transactionList.size() == 0){
            System.out.println("There are no transactions today");
            return;
        }
        transactionList.forEach(System.out::println);
    }

    public void dailyPayments() {
        LocalDate localDate;
        while (true){
            try {
                System.out.println("Enter date in this format -> yyyy-mm-dd");
                String date = ComponentContainer.stringScanner.next();
                localDate = LocalDate.parse(date);
                break;
            }catch (DateTimeParseException e){
                System.out.println("Wrong format");
            }
        }

        List<Transaction> transactionList = transactionRepository.todayTransactionsList(localDate);
        if (transactionList.size() == 0){
            System.out.println("There are no transactions on this date");
            return;

        }

        transactionList.forEach(System.out::println);
    }

    public void showIntermediateTransactions() {
        LocalDate startDate;
        LocalDate endDate;
        while (true){
            try {
                System.out.println("Start date in this format -> yyyy-mm-dd");
                String start = ComponentContainer.stringScanner.next();
                startDate = LocalDate.parse(start);

                System.out.println("End date in this format -> yyyy-mm-dd");
                String end = ComponentContainer.stringScanner.next();
                endDate = LocalDate.parse(end);
                break;
            }catch (DateTimeParseException e){
                System.out.println("Wrong format");
            }
        }

        List<Transaction> transactionList = transactionRepository.getIntermediateTransactions(startDate, endDate);
        if (transactionList.size() == 0){
            System.out.println("There are no transactions on this date");
            return;

        }

        transactionList.forEach(System.out::println);
    }

    public void showTransactionListByTerminal() {
        System.out.println("Enter terminal code");
        String code = ComponentContainer.stringScanner.next();
        Terminal terminal = terminalRepository.getTerminal(code);
        if (terminal == null){
            System.out.println("Terminal not found");
            return;
        }

        List<Transaction> transactionList = transactionRepository.getTransactionListByTerminal(code);
        if (transactionList.size() == 0){
            System.out.println("There are no transactions on this terminal");
            return;

        }

        transactionList.forEach(System.out::println);
    }

    public void showTransactionListByCard() {
        System.out.println("Enter card number");
        Long number = ComponentContainer.intScanner.nextLong();
        Card card = cardRepository.getCardByNumber(number);
        if (card == null){
            System.out.println("Terminal not found");
            return;
        }

        List<Transaction> transactionList = transactionRepository.getTransactionListByCard(number);
        if (transactionList.size() == 0){
            System.out.println("There are no transactions on this card");
            return;

        }

        transactionList.forEach(System.out::println);
    }
}
