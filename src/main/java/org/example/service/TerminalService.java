package org.example.service;

import org.example.container.ComponentContainer;
import org.example.dto.Terminal;
import org.example.enums.TerminalStatus;
import org.example.repository.TerminalRepository;

import java.util.List;

public class TerminalService {
    private TerminalRepository terminalRepository;
    public void createTerminal(String code, String address){
        Terminal temp = terminalRepository.getTerminal(code);
        if (temp != null){
            System.out.println("This code already exists");
            return;
        }
        Terminal terminal = new Terminal();
        terminal.setCode(code);
        terminal.setAddress(address);
        terminal.setStatus(TerminalStatus.ACTIVE);

        boolean bool = terminalRepository.createTerminal(terminal);
        if (bool){
            System.out.println("Terminal created");
        }else {
            System.out.println("Terminal not created");
        }
    }

    public void getTerminalList() {
        List<Terminal> terminalList = terminalRepository.getTerminalList();
        if (terminalList.size() == 0){
            System.out.println("Terminal list is empty");
            return;
        }
        terminalList.forEach(System.out::println);
    }

    public void updateTerminal(String code, String address) {
        Terminal terminal = terminalRepository.getTerminal(code);
        if (terminal == null){
            System.out.println("Terminal not found");
            return;
        }

        boolean bool = terminalRepository.updateTerminal(code,address);
        if (bool){
            System.out.println("Terminal update");
        }else {
            System.out.println("Terminal not update");
        }
    }

    public void changeTerminalStatus(String code) {
        Terminal terminal = terminalRepository.getTerminal(code);
        if (terminal == null){
            System.out.println("Terminal not found");
            return;
        }

        TerminalStatus status;
        if (terminal.getStatus().equals(TerminalStatus.ACTIVE)){
            System.out.println("""
                    Terminal status is ACTIVE
                    Do you want to BLOCK
                    1. Yes
                    """);
            int action = ComponentContainer.intScanner.nextInt();
            if(action != 1){
                return;
            }
            status = TerminalStatus.BLOCK;
        }else {
            System.out.println("""
                    Terminal status is BLOCK
                    Do you want to ACTIVE
                    1. Yes
                    """);
            int action = ComponentContainer.intScanner.nextInt();
            if(action != 1){
                return;
            }
            status = TerminalStatus.ACTIVE;
        }

        boolean bool = terminalRepository.changeTerminalStatus(code,status);
        if (bool){
            System.out.println("Terminal status changed");
        }else {
            System.out.println("Terminal status not changed");
        }
    }

    public void deleteTerminal(String code) {
        Terminal terminal = terminalRepository.getTerminal(code);
        if (terminal == null){
            System.out.println("Terminal not found");
            return;
        }

        boolean bool = terminalRepository.deleteTerminal(code);
        if (bool){
            System.out.println("Terminal deleted");
        }else {
            System.out.println("Terminal not deleted");
        }
    }

    public TerminalRepository getTerminalRepository() {
        return terminalRepository;
    }

    public void setTerminalRepository(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }

}
