package org.example.dto;

import org.example.enums.TerminalStatus;
import java.time.LocalDateTime;

public class Terminal {

    private Integer id;
    private String code;
    private String address;
    private TerminalStatus status;
    private LocalDateTime create_date;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TerminalStatus getStatus() {
        return status;
    }

    public void setStatus(TerminalStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", code='" + code + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", create_date=" + create_date ;
    }
}
