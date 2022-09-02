package com.Ironhack.BankApi.Repositories;

import com.Ironhack.BankApi.Models.AccountHolder;
import com.Ironhack.BankApi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder,Long> {
    public Optional<AccountHolder> findByName(String name);
}
