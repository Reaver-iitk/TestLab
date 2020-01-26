package com.reaver.testlab.room.history;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.reaver.testlab.room.account.AccountDatabase;

import java.util.List;

public class HistoryRepository {
    private HistoryDao historyDao;
    private LiveData<List<History>> getAll;
    public HistoryRepository(Application application){
        AccountDatabase database = AccountDatabase.getInstance(application);
        historyDao = database.historyDao();
        getAll = historyDao.getAll();
    }

    public void insert(History history){
        new HistoryRepository.InsertHistoryAsyncTask(historyDao).execute(history);

    }

    public void update(History history){
        new HistoryRepository.UpdateHistoryAsyncTask(historyDao).execute(history);

    }

    public void delete(History history){
        new HistoryRepository.DeleteHistoryAsyncTask(historyDao).execute(history);
    }

    public void deleteTable(){
        new HistoryRepository.DeleteTableHistoryAsyncTask(historyDao).execute();
    }

    public LiveData<List<History>> getAll(){
        return getAll;
    }

    private static class InsertHistoryAsyncTask extends AsyncTask<History, Void, Void > { //добавление записи в таблицу

        private HistoryDao historyDao;

        private InsertHistoryAsyncTask(HistoryDao historyDao){
            this.historyDao = historyDao;
        }
        @Override
        protected Void doInBackground(History... histories) {
            historyDao.insert(histories[0]);
            return null;
        }
    }
    private static class UpdateHistoryAsyncTask extends AsyncTask<History, Void, Void >{ //изменение записи в таблице

        private HistoryDao historyDao;

        private UpdateHistoryAsyncTask(HistoryDao historyDao){
            this.historyDao = historyDao;
        }
        @Override
        protected Void doInBackground(History... histories) {
            historyDao.update(histories[0]);
            return null;
        }
    }
    private static class DeleteHistoryAsyncTask extends AsyncTask<History, Void, Void >{ //удаление записи в таблице

        private HistoryDao historyDao;

        private DeleteHistoryAsyncTask(HistoryDao historyDao){
            this.historyDao = historyDao;
        }
        @Override
        protected Void doInBackground(History... histories) {
            historyDao.delete(histories[0]);
            return null;
        }
    }
    private static class DeleteTableHistoryAsyncTask extends AsyncTask<Void, Void, Void >{ //удаление таблиц

        private HistoryDao historyDao;

        private DeleteTableHistoryAsyncTask(HistoryDao historyDao){
            this.historyDao = historyDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            historyDao.deleteTable();
            return null;
        }
    }
}


