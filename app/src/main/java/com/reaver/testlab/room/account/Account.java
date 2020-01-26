package com.reaver.testlab.room.account;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "account")
public class Account {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String accountName; //Наименование счёта


    public String currency; //Валюта на счету

    public Account(String accountName, String currency) {
        this.accountName = accountName;
        this.currency = currency;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getCurrency() {
        return currency;
    }
}
