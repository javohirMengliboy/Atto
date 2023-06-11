package org.example.dto;

import org.example.enums.TransactionType;
import java.time.LocalDateTime;

public class Transaction {

    private Integer id;
    private Long card_number;
    private Double amount;
    private String terminal_code;
    private TransactionType type;
    private LocalDateTime created_date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCard_number() {
        return card_number;
    }

    public void setCard_number(Long card_number) {
        this.card_number = card_number;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTerminal_code() {
        return terminal_code;
    }

    public void setTerminal_code(String terminal_code) {
        this.terminal_code = terminal_code;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime create_date) {
        this.created_date = create_date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", card_number=" + card_number +
                ", amount=" + amount +
                ", terminal_code='" + terminal_code + '\'' +
                ", type=" + type +
                ", create_date=" + created_date +
                '}';
    }
}
