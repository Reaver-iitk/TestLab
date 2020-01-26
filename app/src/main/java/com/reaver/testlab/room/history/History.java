package com.reaver.testlab.room.history;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "history")
public class History {


    @PrimaryKey(autoGenerate = true)
    public int id;

    public String historyName; //Наименование счёта

    public String historyDate; //Дата транзакции

    public String historyVid; //Вид транзакции

    public String historyCurrency; //Наименование счёта

    public String historyVal; //Наименование валюты

    public String historyComm; //Комментарий

    public String historyNow; //Остаток на счету

    public History(String historyName, String historyDate,
                   String historyVid, String historyCurrency,
                   String historyVal, String historyComm, String historyNow ) {
        this.historyName = historyName;
        this.historyDate = historyDate;
        this.historyVid = historyVid;
        this.historyCurrency = historyCurrency;
        this.historyVal = historyVal;
        this.historyComm = historyComm;
        this.historyNow = historyNow;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String gethistoryName() {
        return historyName;
    }

    public String gethistoryDate() {
        return historyDate;
    }

    public String gethistoryVid() {
        return historyVid;
    }

    public String gethistoryCurrency() {
        return historyCurrency;
    }
    public String gethistoryVal() {
        return historyVal;
    }

    public String gethistoryComm() {
        return historyComm;
    }
    public String gethistoryNow() {
        return historyNow;
    }
}

