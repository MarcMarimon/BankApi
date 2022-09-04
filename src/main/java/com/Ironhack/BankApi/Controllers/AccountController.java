package com.Ironhack.BankApi.Controllers;

import com.Ironhack.BankApi.Models.Account;
import com.Ironhack.BankApi.Models.Checking;
import com.Ironhack.BankApi.Models.CreditCard;
import com.Ironhack.BankApi.Models.Savings;
import com.Ironhack.BankApi.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AccountController {
@Autowired
    AccountService accountService;

    @PostMapping("/Account/create-checking-account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckingAccount(@RequestBody Checking accountInfo,@RequestParam Long accountHolderId){
        return accountService.createCheckingAccount(accountInfo,accountHolderId);
    }
    @PostMapping("/Account/create-saving-account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createSavingAccount(@RequestBody Savings accountInfo, @RequestParam Long accountHolderId ){
        return accountService.createSavingsAccount(accountInfo,accountHolderId);
    }

    @PostMapping("/Account/create-credit-card")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCreditCard(@RequestBody CreditCard cardInfo,@RequestParam Long accountHolderId){
        return accountService.createCreditCard(cardInfo,accountHolderId);
    }
    @DeleteMapping("/Account/delete-account/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount (@PathVariable Long accountId){
        accountService.deleteAccount(accountId);
    }
    @GetMapping("/Account/check-my-balance/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal checkMyBalance(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long accountId){
        return accountService.checkMyBalance(userDetails,accountId);
    }
    @GetMapping("/Account/check-balance/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal checkBalance(@PathVariable Long accountId){
        return accountService.checkBalance(accountId);
    }
    @PatchMapping("/Account/add-money")
    @ResponseStatus(HttpStatus.OK)
    public void addMoney (@RequestParam Long accountId,@RequestParam BigDecimal quantity){
        accountService.addMoney(accountId,quantity);
    }
    @PatchMapping("/Account/rest-money")
    @ResponseStatus(HttpStatus.OK)
    public void restMoney(@RequestParam Long accountId,@RequestParam BigDecimal quantity){
        accountService.restMoney(accountId,quantity);
    }
    @PatchMapping("/Account/make-transfer")
    @ResponseStatus(HttpStatus.OK)
    public void makeTransfer(@AuthenticationPrincipal UserDetails userDetails,@RequestParam Long accountId1,@RequestParam BigDecimal quantity,@RequestParam Long accountId2){
        accountService.makeTransfer(userDetails,accountId1,quantity,accountId2);
    }
    @PatchMapping("/Account/third-party-transfer")
    @ResponseStatus(HttpStatus.OK)
    public void thirdPartyTransfer(@AuthenticationPrincipal UserDetails userDetails,@RequestParam Long accountId, @RequestHeader String hashKey, @RequestParam String secretKey,@RequestParam BigDecimal quantity){
        accountService.thirdPartyTransfer(userDetails,accountId,secretKey,hashKey,quantity);
    }
}
