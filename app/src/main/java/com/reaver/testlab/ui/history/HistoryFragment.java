package com.reaver.testlab.ui.history;

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
import com.reaver.testlab.AccountViewModel;
import com.reaver.testlab.AddAccountActivity;
import com.reaver.testlab.AddTransaction;
import com.reaver.testlab.HistoryAdapter;
import com.reaver.testlab.HistoryViewModel;
import com.reaver.testlab.R;
import com.reaver.testlab.room.history.History;

import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_history, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final HistoryAdapter adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);

        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyViewModel.getAll().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                adapter.setHistories(histories);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                historyViewModel.delete(adapter.getHistoryAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(),"История удалена",Toast.LENGTH_LONG).show();

            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }
}