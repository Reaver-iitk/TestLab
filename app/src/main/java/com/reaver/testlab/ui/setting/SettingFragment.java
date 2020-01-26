package com.reaver.testlab.ui.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.reaver.testlab.R;

public class SettingFragment extends Fragment {
    String US, EU, YU;
    EditText etUS, etEU, etYU;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button Save =  view.findViewById(R.id.save_settings);
        Button Reset = view.findViewById(R.id.reset_settings);
        SharedPreferences sPref = getActivity().getSharedPreferences("VALUE", Context.MODE_PRIVATE);
        US = sPref.getString("US", "62.07");
        EU = sPref.getString("EU", "68.29");
        YU = sPref.getString("YU", "0.56");
        etUS = view.findViewById(R.id.etUS);
        etEU = view.findViewById(R.id.etEU);
        etYU = view.findViewById(R.id.etYU);
        etUS.setText(US);
        etEU.setText(EU);
        etYU.setText(YU);
       // Toast.makeText(getContext(),Enter,Toast.LENGTH_LONG).show();
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("VALUE", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sharedPreferences.edit();
                ed.putString("US", "62.07").apply();
                ed.putString("EU", "68.29").apply();
                ed.putString("YU", "0.56").apply();
                ed.commit();
                etUS.setText("62.07");
                etEU.setText("68.29");
                etYU.setText("0.56");
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((etUS.getText().toString().equals("")) || (etEU.getText().toString().equals("")) || (etYU.getText().toString().equals(""))) {
                    Toast.makeText(getActivity(), "Поля не могут оставаться пустыми", Toast.LENGTH_LONG).show();
                } else {
                US = etUS.getText().toString();
                EU = etEU.getText().toString();
                YU = etYU.getText().toString();

                Double Us_d = Double.parseDouble(US);  //исключение значений по типу (.05) и (04)
                Double Eu_d = Double.parseDouble(EU);
                Double Yu_d = Double.parseDouble(YU);

                if((Us_d <= 0)||(Eu_d <= 0 )||(Yu_d <= 0)) {
                    Toast.makeText(getActivity(), "Значения не могут быть отрицательными или нулевыми", Toast.LENGTH_LONG).show();
                }else {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("VALUE", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sharedPreferences.edit();

                US = Us_d.toString();
                EU = Eu_d.toString();
                YU = Yu_d.toString();
                ed.putString("US", US).apply();
                ed.putString("EU", EU).apply();
                ed.putString("YU", YU).apply();
                ed.commit();
                }
            }
            }
        });

        return view;
    }
}