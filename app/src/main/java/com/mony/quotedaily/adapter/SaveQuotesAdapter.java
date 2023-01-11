package com.mony.quotedaily.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mony.quotedaily.R;
import com.mony.quotedaily.dao.SaveQuote;

import java.util.ArrayList;
import java.util.List;

public class SaveQuotesAdapter extends RecyclerView.Adapter<SaveQuotesAdapter.QuotesViewHolder> {

    List<SaveQuote> saveQuote = new ArrayList<>();
    OnItemClickListener listener;

    @NonNull
    @Override
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.save_quote_item, parent, false);

        return new QuotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesViewHolder holder, int position) {

        SaveQuote currentNote = saveQuote.get(position);

        holder.authText.setText(currentNote.getQuoteAuthor());
        holder.msgText.setText(currentNote.getQuoteMsg());

    }

    public void setSaveQuote(List<SaveQuote> saveQuote) {
        this.saveQuote = saveQuote;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return saveQuote.size();
    }

    public SaveQuote getQuotePosition(int position){
        return saveQuote.get(position);
    }

    public interface OnItemClickListener{
        void onItemClick(SaveQuote saveQuote);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public class QuotesViewHolder extends RecyclerView.ViewHolder{

        TextView authText, msgText;

        public QuotesViewHolder(@NonNull View itemView) {
            super(itemView);
            authText = itemView.findViewById(R.id.authorText);
            msgText = itemView.findViewById(R.id.quoteMsg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(saveQuote.get(position));
                    }
                }
            });
        }
    }
}
