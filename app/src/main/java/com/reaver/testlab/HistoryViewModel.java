package com.reaver.testlab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.reaver.testlab.room.account.Account;
import com.reaver.testlab.room.account.AccountRepository;
import com.reaver.testlab.room.history.History;
import com.reaver.testlab.room.history.HistoryRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private HistoryRepository repository;
    private LiveData<List<History>> getAll;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new HistoryRepository(application);
        getAll = repository.getAll();

    }

    public void insert(History history){
        repository.insert(history);
    }

    public void insert(History... history){
        repository.insert(history[0]);
    }

    public void update(History history){
        repository.update(history);

    }

    public void delete(History history){
        repository.delete(history);
    }

    public void deleteTable(History history){
        repository.deleteTable();
    }

    public LiveData<List<History>> getAll(){
        return getAll;
    }
}
