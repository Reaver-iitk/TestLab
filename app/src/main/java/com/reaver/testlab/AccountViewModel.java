package com.reaver.testlab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.reaver.testlab.room.account.Account;
import com.reaver.testlab.room.account.AccountRepository;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private AccountRepository repository;
    private LiveData<List<Account>> getAll;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        repository = new AccountRepository(application);
        getAll = repository.getAll();

    }

    public void insert(Account account){
        repository.insert(account);
    }

    public void update(Account account){
        repository.update(account);

    }

    public void delete(Account account){
        repository.delete(account);
    }

    public void deleteTable(Account account){
        repository.deleteTable();
    }

    public LiveData<List<Account>> getAll(){
        return getAll;
    }
}
