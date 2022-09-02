package com.Ironhack.BankApi.Repositories;

import com.Ironhack.BankApi.Models.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Long> {

}
