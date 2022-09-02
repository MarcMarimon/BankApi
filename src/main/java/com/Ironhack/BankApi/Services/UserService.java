package com.Ironhack.BankApi.Services;

import com.Ironhack.BankApi.Models.AccountHolder;
import com.Ironhack.BankApi.Models.ThirdParty;
import com.Ironhack.BankApi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
@Autowired
    UserRepository userRepository;

    public ThirdParty addThirdParty(ThirdParty thirdParty){
        return userRepository.save(thirdParty);
    }
    public AccountHolder createAccountHolder(AccountHolder accountHolder){
        return userRepository.save(accountHolder);
    }
}
