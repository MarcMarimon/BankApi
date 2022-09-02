package com.Ironhack.BankApi.Repositories;

import com.Ironhack.BankApi.Models.ThirdParty;
import com.Ironhack.BankApi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty,Long> {
    public Optional<ThirdParty> findByName(String name);
}
