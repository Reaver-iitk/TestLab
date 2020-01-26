package com.reaver.testlab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.reaver.testlab.room.account.Account;
import com.reaver.testlab.room.history.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    private List<History> histories = new ArrayList<>();
    private HistoryAdapter.OnItemClickListener listener;


    @NonNull
    @Override
    public HistoryAdapter.HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new HistoryAdapter.HistoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryHolder holder, int position) {
        History currentHistory = histories.get(position);
        holder.textViewHname.setText(currentHistory.gethistoryName());
        holder.textViewHdate.setText(currentHistory.gethistoryDate());
        holder.textViewHvid.setText(currentHistory.gethistoryVid());
        holder.textViewHsum.setText(currentHistory.gethistoryCurrency());
        holder.textViewHval.setText(currentHistory.gethistoryVal());
        holder.textViewHcom.setText(currentHistory.gethistoryComm());
        holder.textViewHnow.setText(currentHistory.gethistoryNow());

    }
    @Override
    public int getItemCount() {
        return histories.size();
    }

    public void setHistories(List<History> histories){
        this.histories= histories;
        notifyDataSetChanged();
    }
    class HistoryHolder extends RecyclerView.ViewHolder {
        private TextView textViewHname, textViewHdate, textViewHvid, textViewHsum,
        textViewHval, textViewHcom, textViewHnow;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            textViewHname = itemView.findViewById(R.id.history_account);
            textViewHdate = itemView.findViewById(R.id.history_data);
            textViewHvid = itemView.findViewById(R.id.history_vid);
            textViewHsum = itemView.findViewById(R.id.history_sum);
            textViewHval = itemView.findViewById(R.id.history_val);
            textViewHcom = itemView.findViewById(R.id.history_com);
            textViewHnow = itemView.findViewById(R.id.history_now);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(histories.get(position));
                    }
                }
            });
        }

    }
    public History getHistoryAt(int position){
        return histories.get(position);
    }



    public interface OnItemClickListener {
        void onItemClick(History history);
    }
    public void setOnItemClickListener(HistoryAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

}



