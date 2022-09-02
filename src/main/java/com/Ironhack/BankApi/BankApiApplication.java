package com.Ironhack.BankApi;

import com.Ironhack.BankApi.Models.*;
import com.Ironhack.BankApi.Repositories.AccountHolderRepository;
import com.Ironhack.BankApi.Repositories.AdministratorRepository;
import com.Ironhack.BankApi.Repositories.ThirdPartyRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class BankApiApplication implements CommandLineRunner {
	@Autowired
	AdministratorRepository administratorRepository;
	@Autowired
	AccountHolderRepository accountHolderRepository;
	@Autowired
	ThirdPartyRepository thirdPartyRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(BankApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		administratorRepository.save(new Administrator("Admin1",passwordEncoder.encode("1234")));
		accountHolderRepository.save(new AccountHolder("Juan Lopez",passwordEncoder.encode("1234"),LocalDate.of(1995,06,23),new Address("Spain","Barcelona","Central Street"), null));
		thirdPartyRepository.save(new ThirdParty("Pepe Lopez",passwordEncoder.encode("1234"),"abcd"));

	}
}
