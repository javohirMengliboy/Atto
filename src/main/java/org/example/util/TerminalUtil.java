package org.example.util;

import org.example.container.ComponentContainer;
import org.example.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TerminalUtil {
    @Autowired
    private TerminalService terminalService;
    public void createTerminal(){
        System.out.println("Enter Terminal code");
        String code = ComponentContainer.stringScanner.next();

        System.out.println("Enter Terminal address");
        String address = ComponentContainer.stringScanner.next();

        terminalService.createTerminal(code, address);
    }


    public void updateTerminal() {
        System.out.println("Enter code!");
        String code = ComponentContainer.stringScanner.next();

        System.out.println("Enter new address!");
        String address = ComponentContainer.stringScanner.next();

        terminalService.updateTerminal(code, address);
    }

    public void changeTerminalStatus() {
        System.out.println("Enter code!");
        String code = ComponentContainer.stringScanner.next();

        terminalService.changeTerminalStatus(code);
    }

    public void deleteTerminal() {
        System.out.println("Enter code!");
        String code = ComponentContainer.stringScanner.next();

        terminalService.deleteTerminal(code);
    }

}
