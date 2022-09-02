package com.Ironhack.BankApi.Controllers;

import com.Ironhack.BankApi.Models.AccountHolder;
import com.Ironhack.BankApi.Models.ThirdParty;
import com.Ironhack.BankApi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/User/add-third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty addThirdParty(@RequestBody ThirdParty thirdParty){
        return userService.addThirdParty(thirdParty);
    }
    @PostMapping("/User/create-account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody AccountHolder accountHolder){
        return userService.createAccountHolder(accountHolder);
    }
}
