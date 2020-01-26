package com.reaver.testlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddAccountActivity extends AppCompatActivity {
    public static final String EXTRA_ACCOUNT = "com.reaver.testlab.EXTRA_ACCOUNT";
    public static final String EXTRA_CURRENCY = "com.reaver.testlab.EXTRA_CURRENCY";

    private EditText editTextAccount;
    private EditText editTextCurrency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        editTextAccount = findViewById(R.id.edit_text_account);
        editTextCurrency = findViewById(R.id.edit_text_currency);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Добавить счёт");
    }

    private void saveAccount(){
        String Name = editTextAccount.getText().toString();
        String Currency = editTextCurrency.getText().toString();
        Double Currency_f = Double.parseDouble(Currency);
        Currency = String.format("%8.2f", Currency_f).replace(',', '.');
        if (Name.trim().isEmpty() || Currency.trim().isEmpty()){
            Toast.makeText(this,"Вы не заполнили данные", Toast.LENGTH_LONG).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_ACCOUNT, Name);
        data.putExtra(EXTRA_CURRENCY, Currency);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_account_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.save_account:
                saveAccount();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

