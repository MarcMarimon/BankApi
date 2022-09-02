package com.Ironhack.BankApi.Models;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.List;
@Entity
public class ThirdParty extends User{
    private String HashKey;


    public ThirdParty() {
    }

    public ThirdParty(String name, String password, String hashKey) {
        super(name, password);
        super.setRoles(new HashSet<>(List.of(new Roles("TP",this))));
        HashKey = hashKey;
    }

    public String getHashKey() {
        return HashKey;
    }

    public void setHashKey(String hashKey) {
        HashKey = hashKey;
    }
}
