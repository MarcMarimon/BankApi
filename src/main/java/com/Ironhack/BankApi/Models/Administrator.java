package com.Ironhack.BankApi.Models;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.List;
@Entity
public class Administrator extends User{

    public Administrator() {
    }

    public Administrator(String name, String password) {
        super(name, password);
        super.setRoles(new HashSet<>(List.of(new Roles("ADMIN",this))));
    }
}
