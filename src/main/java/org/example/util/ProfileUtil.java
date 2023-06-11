package org.example.util;

import org.example.container.ComponentContainer;
import org.example.dto.Profile;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileUtil {
    @Autowired
    private ProfileService profileService;

    public void createUser(){
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
        profile.setStatus(ProfileStatus.NOT_ACTIVE);
        profile.setRole(ProfileRole.USER);

        profileService.addProfile(profile);
    }

    public void createAdmin(){
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
        profile.setStatus(ProfileStatus.NOT_ACTIVE);
        profile.setRole(ProfileRole.ADMIN);

        profileService.addProfile(profile);
    }


    public void changeProfileStatus() {
        System.out.println("Enter Profile phone"); // or login
        String phone = ComponentContainer.stringScanner.next();

        profileService.checkProfileByPhone(phone);
    }
}
