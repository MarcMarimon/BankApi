package com.Ironhack.BankApi.Models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class Savings extends Account{
  private String secretKey;
  private BigDecimal minimumBalance;
  private BigDecimal interestRate;
  private LocalDate creationDate;
  @Enumerated(EnumType.STRING)
  private Status status;
  private LocalDate lastApplyInterest;

    public Savings() {
    }

    public Savings(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, BigDecimal minimumBalance, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
        this.creationDate = LocalDate.now();
        this.status = Status.ACTIVE;
        this.lastApplyInterest = LocalDate.now();
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        if (minimumBalance == null) {
            this.minimumBalance = BigDecimal.valueOf(1000);
        } if ( minimumBalance.compareTo(BigDecimal.valueOf(1000))>0 || minimumBalance.compareTo(BigDecimal.valueOf(100))< 0) {
            throw new IllegalArgumentException("Minimum balance should be between 1000 and 100");
        }
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate==null){
            this.interestRate=BigDecimal.valueOf(0.0025);
        }if(interestRate.compareTo(BigDecimal.valueOf(0.5)) > 0){
            throw new IllegalArgumentException("Maximum interest rate is 0.5");
     }
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public BigDecimal getBalance() {
        interestApply();
        return super.getBalance();
    }

    @Override
    public void setBalance(BigDecimal balance) {
        interestApply();
        super.setBalance(balance);
    }

    private void interestApply(){
        if(Period.between(lastApplyInterest,LocalDate.now()).getYears()>1){
            setBalance(getBalance().multiply((interestRate)).add(getBalance()).multiply(BigDecimal.valueOf(Period.between(lastApplyInterest,LocalDate.now()).getYears())));
            lastApplyInterest.plusYears(Period.between(lastApplyInterest,LocalDate.now()).getYears());
        }
    }
}
