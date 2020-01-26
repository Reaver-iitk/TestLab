package com.reaver.testlab.room.account;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.reaver.testlab.room.history.History;
import com.reaver.testlab.room.history.HistoryDao;

@Database(entities = {Account.class, History.class}, version = 1)
public abstract class AccountDatabase extends RoomDatabase {
    public static AccountDatabase instance;
    public abstract AccountDao accountDao();
    public abstract HistoryDao historyDao();

    public static synchronized AccountDatabase getInstance(Context context){
        if (instance==null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AccountDatabase.class, "account")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new CurrencyDbAsyncTask(instance).execute();
        }
    };
    public static class CurrencyDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private AccountDao accountDao;
        private HistoryDao historyDao;

        private CurrencyDbAsyncTask(AccountDatabase db){
            accountDao = db.accountDao();
            historyDao = db.historyDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            accountDao.insert(new Account("БАНК", "20000"));
            accountDao.insert(new Account("НАЛИЧНЫЕ", "1000"));
            historyDao.insert(new History("БАНК", "26 Jan 2020, 12:25", "Оплата", "-10",
                    "$", "интернет покупки", "20000"));
            return null;
        }
    }

}
