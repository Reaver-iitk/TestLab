package com.reaver.testlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.reaver.testlab.room.history.History;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTransaction extends AppCompatActivity {
    public static final String Edit_EXTRA_ACCOUNT = "com.reaver.testlab.EXTRA_ACCOUNT";
    public static final String Edit_EXTRA_CURRENCY = "com.reaver.testlab.EXTRA_CURRENCY";
    public static final String Edit_EXTRA_ID = "com.reaver.testlab.Edit_EXTRA_ID";

    private EditText editTextCurrency, editTextComm;
    private TextView tvAccount, tvCurrency;
    int choise1 = 0,
            choise2 = 0;
    String Account, Currency, US, EU, YU;
    String hNa,hD,hVi,hCu,hVa,hCo,hNw;
    Double res, value = 0.0;
    int c;
    private AccountViewModel accountViewModel;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_add_transaction);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Пополнение / Оплата");
        editTextCurrency = findViewById(R.id.add_currency);
        editTextComm = findViewById(R.id.add_com);
        tvAccount = findViewById(R.id.tvAccount);
        tvCurrency = findViewById(R.id.tvCurrency);
        Intent intent = getIntent();
        c = intent.getIntExtra(Edit_EXTRA_ID, -1);
        Account = intent.getStringExtra(Edit_EXTRA_ACCOUNT);
        Currency = intent.getStringExtra(Edit_EXTRA_CURRENCY);
        res = Double.parseDouble(Currency);
        Currency = Currency + " RUB";



        tvAccount.setText(Account);
        tvCurrency.setText(Currency);


        RadioButton ViborPlus = findViewById(R.id.radioButton);
        ViborPlus.setOnClickListener(radioButtonClickListener1);
        RadioButton ViborMin = findViewById(R.id.radioButton2);
        ViborMin.setOnClickListener(radioButtonClickListener1);

        RadioButton ViborUs = findViewById(R.id.radioButton3);
        ViborUs.setOnClickListener(radioButtonClickListener2);
        RadioButton ViborEu = findViewById(R.id.radioButton4);
        ViborEu.setOnClickListener(radioButtonClickListener2);
        RadioButton ViborRu = findViewById(R.id.radioButton5);
        ViborRu.setOnClickListener(radioButtonClickListener2);
        RadioButton ViborYu = findViewById(R.id.radioButton6);
        ViborYu.setOnClickListener(radioButtonClickListener2);
        SharedPreferences sPref = getSharedPreferences("VALUE", Context.MODE_PRIVATE);
        US = sPref.getString("US", "62.07");
        EU = sPref.getString("EU", "68.29");
        YU = sPref.getString("YU", "0.56");
    }
    View.OnClickListener radioButtonClickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb1 = (RadioButton)v;
            switch (rb1.getId()) {
                case R.id.radioButton: choise1 = 1;
                    break;
                case R.id.radioButton2: choise1 = 2;
                    break;

                default:
                    break;
            }
        }
    };

    View.OnClickListener radioButtonClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb1 = (RadioButton)v;
            switch (rb1.getId()) {
                case R.id.radioButton3: value = Double.parseDouble(US);
                            hVa = "$";
                    break;
                case R.id.radioButton4: value = Double.parseDouble(EU);
                            hVa = "€";
                    break;
                case R.id.radioButton5: value = 1.0;
                            hVa = "₽";
                    break;
                case R.id.radioButton6: value = Double.parseDouble(YU);
                            hVa = "¥";
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.next_transaction_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.transaction:
                saveAccount();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void saveAccount(){
        String Currency = editTextCurrency.getText().toString();
        hCo = editTextComm.getText().toString();
        Double res2 = Double.parseDouble(Currency);

        if ((choise1 != 0) && (value !=0.0) ){
            if (choise1 == 1) {
                hVi = "Пополнение";
                hCu = "+"+ res2;
                res = res + res2 * value;
                hNw = String.format("%8.2f", res).replace(',', '.');
            }
            else
                if ((choise1 == 2) && (res>res2*value)) {
                    hVi = "Оплата";
                    hCu = "-"+ res2;
                    res = res - res2 * value;
                    hNw = String.format("%8.2f", res).replace(',', '.');
            }
                else {
                    Toast.makeText(AddTransaction.this, "Недостаточно средств на балансе", Toast.LENGTH_LONG).show();
                    return;
                }
            String fin = String.format("%8.2f", res).replace(',', '.');
        if (Currency.trim().isEmpty()){
            Toast.makeText(this,"Вы не заполнили сумму", Toast.LENGTH_LONG).show();
            return;
        }



        hNa = Account;
        DateFormat df = new SimpleDateFormat("d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        hD = date;
        HistoryViewModel historyViewModel;
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        History history = new History(hNa,hD,hVi,hCu,hVa,hCo,hNw);
        historyViewModel.insert(history);

        Intent data = new Intent();
        data.putExtra(Edit_EXTRA_ID, c);
        data.putExtra(Edit_EXTRA_ACCOUNT, Account);
        data.putExtra(Edit_EXTRA_CURRENCY, fin);
         if (c != -1 ) {
           data.putExtra(Edit_EXTRA_ID, c);
        }
        setResult(RESULT_OK, data);
        finish();
    }
    else {
        Toast.makeText(AddTransaction.this, "Выберите валюту и вид транзакции", Toast.LENGTH_LONG).show();
    }
    }
}

