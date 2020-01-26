package com.reaver.testlab.room.history;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM history") // вывод всех данных
    LiveData<List<History>> getAll();


    @Query("DELETE FROM history")
    void deleteTable();


    @Query("SELECT * FROM history WHERE id = :id") //поиск по id
    History getById(long id);

    @Insert
    void insert(History history); //внесение данных

    @Update
    void update(History history); //обновление данных


    @Delete
    void delete(History history); //удаление данных
}
