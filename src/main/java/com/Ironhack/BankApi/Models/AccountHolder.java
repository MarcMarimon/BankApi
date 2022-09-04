package com.Ironhack.BankApi.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Entity
public class AccountHolder extends User{
    private LocalDate dateOfBirth;
    @Embedded
    private Address primaryAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="country",column = @Column(name="scondaryCountry")),
            @AttributeOverride(name = "city",column = @Column(name="secondaryCity")),
            @AttributeOverride(name="street",column = @Column(name="secondaryStreet"))
    })
    private  Address secondaryAddress;
    @OneToMany(mappedBy = "primaryOwner")
    @JsonIgnore
    private  List<Account> listAccount;
    @OneToMany(mappedBy = "secondaryOwner")
    @JsonIgnore
    private  List<Account> listAccount2;
    public AccountHolder() {
    }

    public AccountHolder(String name, String password, LocalDate dateOfBirth, Address primaryAddress, Address secondaryAddress) {
        super( name, password);
        super.setRoles(new HashSet<>(List.of(new Roles("HOLDER",this))));
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.secondaryAddress = secondaryAddress;
        this.listAccount = new ArrayList<>();
        this.listAccount2 = new ArrayList<>();
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getSecondaryAddress() {
        return secondaryAddress;
    }

    public void setSecondaryAddress(Address secondaryAddress) {
        this.secondaryAddress = secondaryAddress;
    }

    public List<Account> getListAccount() {
        return listAccount;
    }

    public void setListAccount(List<Account> listAccount) {
        this.listAccount = listAccount;
    }

    public List<Account> getListAccount2() {
        return listAccount2;
    }

    public void setListAccount2(List<Account> listAccount2) {
        this.listAccount2 = listAccount2;
    }
}
