package org.example.service;

import org.example.container.ComponentContainer;
import org.example.controller.AdminController;
import org.example.controller.MainController;
import org.example.controller.UserController;
import org.example.dto.Profile;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private MainController mainController;
    @Autowired
    private AdminController adminController;
    @Autowired
    private UserController userController;
    @Autowired
    private ProfileRepository profileRepository;

    public void addProfile(Profile profile) {
        if (!checkName(profile.getName())){
            System.out.println("Name invalid");
            return;
        }

        if (!checkName(profile.getSurname())){
            System.out.println("Surname invalid");
            return;
        }

        if (!checkPhone(profile.getPhone())){
            System.out.println("Phone invalid");
            return;
        }
        boolean result = profileRepository.addProfile(profile);
        if (result){
            mainController.mainPage();
        }

    }

    public boolean checkName(String name){
        boolean bool = true;
        char[] arr = name.toCharArray();

        if (name.charAt(0) < 64 || name.charAt(0) > 91) {
            bool = false;
        }

        if (name.length()< 3){
            bool = false;
        }

        for (char c : arr) {
            if (c > 31 && c < 58) {
                bool = false;
                break;
            }
        }
        return bool;
    }

    public static boolean checkPhone(String phone){
        boolean bool = true;
        char[] arr = phone.toCharArray();

        if (phone.length() != 13 || !phone.startsWith("+998")){
            bool = false;
        }

        for (int i = 1; i< arr.length; i++) {
            if (arr[i] < 48 || arr[i] > 57) {
                bool = false;
                break;
            }
        }
        return bool;
    }

    public void checkProfile(Profile profile){
//        System.out.println(profile.toString());
        ComponentContainer.profile = profile;
//        System.out.println(this.profile.toString());
        if (profile.getRole().equals(ProfileRole.USER)) {
            userController.userPage();
        }else if (profile.getRole().equals(ProfileRole.ADMIN)){
            adminController.adminPage();
        }
    }

    public void showProfileList() {
        List<Profile> profileList = profileRepository.getProfileList();
        if (profileList.size() == 0){
            System.out.println("Profile list is empty");
            return;
        }
        profileList.forEach(System.out::println);
    }

    public void checkProfileByPhone(String phone) {
        Profile profile = profileRepository.getProfileByPhone(phone);
        if (profile == null){
            System.out.println("Profile not found");
            return;
        }

        ProfileStatus status;
        if (profile.getStatus().equals(ProfileStatus.ACTIVE)){
            System.out.println("""
                    Card status is ACTIVE
                    Do you want to BLOCK
                    1. Yes""");
            int action =  ComponentContainer.intScanner.nextInt();
            if(action != 1){
                return;
            }
            status = ProfileStatus.BLOCK;
        }else if (profile.getStatus().equals(ProfileStatus.BLOCK)){
            System.out.println("""
                    Card status is BLOCK
                    Do you want to ACTIVE
                    1. Yes""");
            int action = ComponentContainer.intScanner.nextInt();
            if(action != 1){
                return;
            }
            status = ProfileStatus.ACTIVE;
        }else {
            System.out.println("Card status is NOT_ACTIVE");
            return;
        }

        boolean result = profileRepository.changeProfileStatus(phone, status);
        if(result){
            System.out.println("Status changed");
        }else {
            System.out.println("Status not changed");
        }
    }

}
