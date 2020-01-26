package com.reaver.testlab.room.account;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AccountDao {

    @Query("SELECT * FROM Account") // вывод всех данных
    LiveData<List<Account>> getAll();


    @Query("DELETE FROM account")
    void deleteTable();


    @Query("SELECT * FROM account WHERE id = :id") //поиск по id
    Account getById(long id);

    @Insert
    void insert(Account account); //внесение данных

    @Update
    void update(Account account); //обновление данных


    @Delete
    void delete(Account account); //удаление данных

}
