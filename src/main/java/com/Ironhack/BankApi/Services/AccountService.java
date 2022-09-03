package com.Ironhack.BankApi.Services;

import com.Ironhack.BankApi.Models.*;
import com.Ironhack.BankApi.Repositories.AccountHolderRepository;
import com.Ironhack.BankApi.Repositories.AccountRepository;
import com.Ironhack.BankApi.Repositories.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;


    public Account createCheckingAccount(Checking accountInfo, Long id) {
        if (accountHolderRepository.findById(id).isPresent()) {
            AccountHolder accountHolder = accountHolderRepository.findById(id).get();

            if (Period.between(accountHolder.getDateOfBirth(), LocalDate.now()).getYears() > 24) {
                Checking checking = new Checking(accountInfo.getBalance(), accountHolder, null, accountInfo.getSecretKey());
                return accountRepository.save(checking);
            }
            StudentChecking studentChecking = new StudentChecking(accountInfo.getBalance(), accountHolder, null, accountInfo.getSecretKey());
            return accountRepository.save(studentChecking);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account holder not found");

    }

    public Savings createSavingsAccount(Savings accountInfo, Long id) {

        if (accountHolderRepository.findById(id).isPresent()) {
            AccountHolder accountHolder = accountHolderRepository.findById(id).get();
            Savings savings = new Savings(accountInfo.getBalance(), accountHolder, null, accountInfo.getSecretKey(), accountInfo.getMinimumBalance(), accountInfo.getInterestRate());
            return accountRepository.save(savings);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account holder not found");

    }
    public CreditCard createCreditCard(CreditCard cardInfo, Long id){
        if (accountHolderRepository.findById(id).isPresent()) {
            AccountHolder accountHolder = accountHolderRepository.findById(id).get();
            CreditCard creditCard = new CreditCard(cardInfo.getBalance(),accountHolder,null,cardInfo.getCreditLimit(),cardInfo.getInterestRate());
            return accountRepository.save(creditCard);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account holder not found");
    }
    public void deleteAccount (Long id){
        if(accountRepository.findById(id).isPresent()){
            accountRepository.deleteById(id);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Account don't exist");
        }
    }
    public BigDecimal checkMyBalance(UserDetails userDetails, Long accountId ){
        AccountHolder accountHolder = accountHolderRepository.findByName(userDetails.getUsername()).get();
        if(accountRepository.findById(accountId).isPresent()) {
            Account account=accountRepository.findById(accountId).get();
            if (account.getPrimaryOwner().getId()==accountHolder.getId() || account.getSecondaryOwner()!=null && account.getSecondaryOwner().getId()==accountHolder.getId()){
                return account.getBalance();
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found");
    }
    public BigDecimal checkBalance(Long accountId){
        if(accountRepository.findById(accountId).isPresent()) {
            Account account = accountRepository.findById(accountId).get();
            return account.getBalance();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found");
    }
    public BigDecimal addMoney(Long accountId,BigDecimal quantity){
        if(accountRepository.findById(accountId).isPresent()) {
            Account account = accountRepository.findById(accountId).get();
            return account.getBalance().add(quantity);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found");
    }
    public BigDecimal restMoney(Long accountId, BigDecimal quantity){
        if(accountRepository.findById(accountId).isPresent()) {
            Account account = accountRepository.findById(accountId).get();
            return account.getBalance().subtract(quantity);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found");
    }
    public void makeTransfer(UserDetails userDetails,Long accountId1,BigDecimal quantity, Long accountId2){
        AccountHolder accountHolder = accountHolderRepository.findByName(userDetails.getUsername()).get();
        if(accountRepository.findById(accountId1).isPresent()&&accountRepository.findById(accountId2).isPresent()) {
            Account account1=accountRepository.findById(accountId1).get();
            Account account2=accountRepository.findById(accountId2).get();
            if (account1.getPrimaryOwner().getId()==accountHolder.getId() || account1.getSecondaryOwner()!=null && account1.getSecondaryOwner().getId()==accountHolder.getId()){
                 if(account1.getBalance().compareTo(quantity)>0){
                     account1.setBalance(account1.getBalance().subtract(quantity));
                     account2.setBalance(account2.getBalance().add(quantity));
                 }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found");
        }
        public void thirdPartyTransfer(UserDetails userDetails, Long accountId,String hashKey, String accountSecretKey,BigDecimal quantity){
            ThirdParty thirdParty = thirdPartyRepository.findByName(userDetails.getUsername()).get();
            if(accountRepository.findById(accountId).isPresent() && accountRepository.findBySecretKey(accountSecretKey).isPresent() && accountRepository.findBySecretKey(accountSecretKey).get().getAccountID() == accountId) {
                Account account = accountRepository.findById(accountId).get();
                if (accountSecretKey.equals(hashKey)) {
                    account.setBalance(account.getBalance().add(quantity));
                }
            }
        }
    }

