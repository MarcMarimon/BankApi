package com.Ironhack.BankApi.Models;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class CreditCard extends Account{
    private BigDecimal creditLimit;
    private BigDecimal interestRate;
    private LocalDate lastApplyInterest;

    public CreditCard() {
    }

    public CreditCard (BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        if (creditLimit == null) {
            this.creditLimit = BigDecimal.valueOf(100);
        } if (creditLimit.compareTo(BigDecimal.valueOf(100))< 0 || creditLimit .compareTo(BigDecimal.valueOf(100000))>0) {
            throw new IllegalArgumentException("Credit limit should to be between 100 and 100000");
        }

    }
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate==null){
            this.interestRate=BigDecimal.valueOf(0.2);
        } if(interestRate.compareTo(BigDecimal.valueOf(0.1)) < 0){
            throw new IllegalArgumentException("Minimum interest rate is 0.1");
        }
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
        if(Period.between(lastApplyInterest, LocalDate.now()).getMonths()>1){
            setBalance(getBalance().multiply((interestRate.divide(BigDecimal.valueOf(12)).multiply(BigDecimal.valueOf(Period.between(lastApplyInterest,LocalDate.now()).getMonths()))).add(getBalance())));
            lastApplyInterest.plusMonths(Period.between(lastApplyInterest,LocalDate.now()).getMonths());
        }
    }
}
