package com.reaver.testlab.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reaver.testlab.AccountAdapter;
import com.reaver.testlab.AccountViewModel;
import com.reaver.testlab.AddAccountActivity;
import com.reaver.testlab.AddTransaction;
import com.reaver.testlab.R;
import com.reaver.testlab.room.account.Account;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment {

    private AccountViewModel accountViewModel;
    public static final int ADD_ACCOUNT_CODE = 1;
    public static final int ADD_CURRENT_CODE = 2;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        FloatingActionButton buttonAddAccount = view.findViewById(R.id.button_add_account);
        buttonAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddAccountActivity.class);
                startActivityForResult(intent, ADD_ACCOUNT_CODE);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final AccountAdapter adapter = new AccountAdapter();
        recyclerView.setAdapter(adapter);

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        accountViewModel.getAll().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                adapter.setAccounts(accounts);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                accountViewModel.delete(adapter.getAccountAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(),"Счёт удалён",Toast.LENGTH_LONG).show();

            }
        }).attachToRecyclerView(recyclerView);


        adapter.setOnItemClickListener(new AccountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Account account) {
               Intent add = new Intent(getContext(), AddTransaction.class);
               add.putExtra(AddTransaction.Edit_EXTRA_ID,account.getId());
               add.putExtra(AddTransaction.Edit_EXTRA_ACCOUNT,account.getAccountName());
               add.putExtra(AddTransaction.Edit_EXTRA_CURRENCY,account.getCurrency());
                startActivityForResult(add,ADD_CURRENT_CODE);

            }
        });

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == ADD_ACCOUNT_CODE && resultCode == RESULT_OK){
            String Name = data.getStringExtra(AddAccountActivity.EXTRA_ACCOUNT);
            String Currency = data.getStringExtra(AddAccountActivity.EXTRA_CURRENCY);
            Account account = new Account(Name,Currency);
            accountViewModel.insert(account);
            Toast.makeText(getContext(),"Счёт успешно создан",Toast.LENGTH_LONG).show();
        }
        else  if (requestCode == ADD_CURRENT_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddTransaction.Edit_EXTRA_ID, -1);
            if (id == -1) {
                 Toast.makeText(getContext(),"Данные не добавлены", Toast.LENGTH_LONG).show();
                 return;
            }
            String Currency = data.getStringExtra((AddTransaction.Edit_EXTRA_CURRENCY));
            String a =  data.getStringExtra(AddTransaction.Edit_EXTRA_ACCOUNT);
            String b = data.getStringExtra(AddTransaction.Edit_EXTRA_CURRENCY);


            Account account = new Account(a,b);
            account.setId(id);
            accountViewModel.update(account);

        }
        //else
       // Toast.makeText(getContext(),"Отмена",Toast.LENGTH_LONG).show();
        }
    }



