package com.Ironhack.BankApi.Repositories;

import com.Ironhack.BankApi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
 public Optional<User> findByName(String name);
}
