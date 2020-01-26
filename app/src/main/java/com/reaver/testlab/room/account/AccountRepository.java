package com.reaver.testlab.room.account;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountRepository {
    private AccountDao accountDao;
    private LiveData<List<Account>> getAll;
    public AccountRepository(Application application){
        AccountDatabase database = AccountDatabase.getInstance(application);
        accountDao = database.accountDao();
        getAll = accountDao.getAll();
    }

    public void insert(Account account){
        new InsertAccountAsyncTask(accountDao).execute(account);

    }

    public void update(Account account){
        new UpdateAccountAsyncTask(accountDao).execute(account);

    }



    public void delete(Account account){
        new DeleteAccountAsyncTask(accountDao).execute(account);
    }

    public void deleteTable(){
        new DeleteTableAccountAsyncTask(accountDao).execute();
    }

    public LiveData<List<Account>> getAll(){
        return getAll;
    }

    private static class InsertAccountAsyncTask extends AsyncTask<Account, Void, Void >{ //добавление записи в таблицу

        private AccountDao accountDao;

        private InsertAccountAsyncTask(AccountDao accountDao){
            this.accountDao = accountDao;
        }
        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.insert(accounts[0]);
            return null;
        }
    }
    private static class UpdateAccountAsyncTask extends AsyncTask<Account, Void, Void >{ //изменение записи в таблице

        private AccountDao accountDao;

        private UpdateAccountAsyncTask(AccountDao accountDao){
            this.accountDao = accountDao;
        }
        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.update(accounts[0]);
            return null;
        }
    }
    private static class DeleteAccountAsyncTask extends AsyncTask<Account, Void, Void >{ //удаление записи в таблице

        private AccountDao accountDao;

        private DeleteAccountAsyncTask(AccountDao accountDao){
            this.accountDao = accountDao;
        }
        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.delete(accounts[0]);
            return null;
        }
    }
    private static class DeleteTableAccountAsyncTask extends AsyncTask<Void, Void, Void >{ //удаление таблиц

        private AccountDao accountDao;

        private DeleteTableAccountAsyncTask(AccountDao accountDao){
            this.accountDao = accountDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            accountDao.deleteTable();
            return null;
        }
    }
}
