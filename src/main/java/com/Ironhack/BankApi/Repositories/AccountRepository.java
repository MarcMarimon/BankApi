package com.Ironhack.BankApi.Repositories;

import com.Ironhack.BankApi.Models.Account;
import com.Ironhack.BankApi.Models.AccountHolder;
import com.Ironhack.BankApi.Models.Checking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    public Optional<Checking> findBySecretKey(String secretKey);
}
