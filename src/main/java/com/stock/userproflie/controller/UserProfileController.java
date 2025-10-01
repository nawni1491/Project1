package com.stock.userproflie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.stock.userproflie.model.UserProfileDto;
import com.stock.userproflie.model.UserProfiledata;
import com.stock.userproflie.service.UserdataService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/userprofile")
public class UserProfileController {

    private final UserdataService userdataService;

    @Autowired
    public UserProfileController(UserdataService userdataService) {
        this.userdataService = userdataService;
    }

    // Show create user profile page
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("userProfileDto", new UserProfileDto());
        return "userprofile/createUserProfile"; 
    }

    // Save user profile
    @PostMapping("/create")
    public String createUserProfile(@Valid @ModelAttribute UserProfileDto userProfileDto, BindingResult result) {
        if (result.hasErrors()) {
            return "userprofile/createUserProfile";
        }
        try {
            userdataService.saveUserdata(userProfileDto, userProfileDto.getImagePath());
        } catch (Exception e) {
            e.printStackTrace();
            result.rejectValue("imageFile", "error.imageFile", "Failed to save the profile.");
            return "userprofile/createUserProfile";
        }
        return "redirect:/userprofile/listUserProfile";
    }
    
    // Show list of profiles
    @GetMapping("/listUserProfile")
    public String listUserProfiles(Model model) {
        List<UserProfiledata> users = userdataService.getAllUserdata();
        model.addAttribute("users", users);
        return "userprofile/listUserProfile";  // ✅ maps to listUserProfile.html
    }

    // Show edit page
    @GetMapping("/edit")
    public String showEditPage(@RequestParam long id, Model model) {
    	UserProfiledata userProfiledata = userdataService.getUserdataById(id);
        UserProfileDto userProfileDto = new UserProfileDto();

        userProfileDto.setId(userProfiledata.getId());
        userProfileDto.setFullName(userProfiledata.getFullName());
        userProfileDto.setEmail(userProfiledata.getEmail());
        userProfileDto.setPhone(userProfiledata.getPhone());
        userProfileDto.setGender(userProfiledata.getGender());
        userProfileDto.setAddress(userProfiledata.getAddress());

        model.addAttribute("userProfileDto", userProfileDto);
        return "userprofile/editUserProfile";
    }

    // Update user profile
    @PostMapping("/edit/{id}")
    public String editUserProfile(@PathVariable long id,
                                  @Valid @ModelAttribute UserProfileDto userProfileDto,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "userprofile/editUserProfile";
        }
        try {
            userdataService.updateUserdata(id, userProfileDto, userProfileDto.getImagePath());
        } catch (Exception e) {
            e.printStackTrace();
            result.rejectValue("imageFile", "error.imageFile", "Failed to update the profile.");
            return "userprofile/editUserProfile";
        }
        return "redirect:/userprofile/listUserProfile";
    }

    // Delete user profile
    @GetMapping("/delete")
    public String deleteUserdata(@RequestParam long id) {
        try {
            userdataService.deleteUserdata(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/userprofile/listUserProfile";
    }

    // Save user profile (alternate POST method)
    @PostMapping("/save")
    public String userProfileSave(@ModelAttribute("userProfileDto") UserProfileDto userProfileDto) {
        try {
            userdataService.saveUserdata(userProfileDto, userProfileDto.getImagePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/userprofile/list";
    }
}
