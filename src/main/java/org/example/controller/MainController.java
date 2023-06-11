package org.example.controller;

import org.example.container.ComponentContainer;
import org.example.dto.Profile;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.repository.CardRepository;
import org.example.repository.DBRepository;
import org.example.repository.ProfileRepository;
import org.example.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MainController{
    @Autowired
    private ProfileRepository profileRepository ;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ProfileService profileService;

    public void start(){
        DBRepository dbRepository = new DBRepository();
        dbRepository.createProfileTable();
        dbRepository.createCardTable();
        dbRepository.createTerminalTable();
        dbRepository.createTransactionTable();
        dbRepository.createMakeTransactionProducer();
//        ComponentContainer.cardUtil.createAttoCard();
        ComponentContainer.attoCard = cardRepository.getAttoCard();
        mainPage();
    }

    public void mainPage(){
        while (true){
            mainMenu();
            int action = getAction();
            switch (action){
                case 1 -> login();
                case 2 -> registration();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Wrong selection");
            }
        }
    }

    public void login(){
        System.out.println("Enter login");
        String login = ComponentContainer.stringScanner.next();

        System.out.println("Enter password");
        String password = ComponentContainer.stringScanner.next();

        Profile profile = profileRepository.getProfileByLogin(login);
        if (profile == null){
            System.out.println("Login not found");
            return;
        }

        if (!profile.getPassword().equals(password)){
            System.out.println("Wrong password!");
            return;
        }

        profile.setStatus(ProfileStatus.ACTIVE);
        profileService.checkProfile(profile);
    }

    public void registration(){
        System.out.println("Enter name");
        String name = ComponentContainer.stringScanner.next();

        System.out.println("Enter surname");
        String surname = ComponentContainer.stringScanner.next();

        System.out.println("Enter phone");
        String phone = ComponentContainer.stringScanner.next();

        System.out.println("Enter login");
        String login = ComponentContainer.stringScanner.next();

        System.out.println("Enter password");
        String password = ComponentContainer.stringScanner.next();

        Profile profile = new Profile();
        profile.setName(name);
        profile.setSurname(surname);
        profile.setPhone(phone);
        profile.setLogin(login);
        profile.setPassword(password);
        profile.setStatus(ProfileStatus.BLOCK);
        profile.setRole(ProfileRole.USER);

        profileService.addProfile(profile);
    }

    public static int getAction() {
        try {
            return ComponentContainer.intScanner.nextInt();
        }catch (Exception e){
            return 0;
        }
    }

    public void mainMenu(){
        System.out.println("""
                1. Log In
                2. Registration
                0. Exit
                
                Choose!
                """);
    }

}
