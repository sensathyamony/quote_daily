package com.mony.quotedaily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.mony.quotedaily.adapter.SaveQuotesAdapter;
import com.mony.quotedaily.dao.SaveQuote;
import com.mony.quotedaily.viewmodel.QuoteViewModel;

import java.util.List;

public class BookMarkActivity extends AppCompatActivity {

    SaveQuotesAdapter adapter;
    RecyclerView rv;

    private QuoteViewModel quoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        rv = findViewById(R.id.rvList);

        rv.setLayoutManager(new LinearLayoutManager(this));
        quoteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(QuoteViewModel.class);
        quoteViewModel.getBookMarkList().observe(this, new Observer<List<SaveQuote>>() {
            @Override
            public void onChanged(List<SaveQuote> notes) {
                adapter.setSaveQuote(notes);
            }
        });

        adapter = new SaveQuotesAdapter();
        rv.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                quoteViewModel.delete(adapter.getQuotePosition(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(rv);

        adapter.setOnItemClickListener(new SaveQuotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SaveQuote saveQuote) {
                Intent intent = new Intent(BookMarkActivity.this, DesignScreen.class);
                intent.putExtra("quoteText", saveQuote.getQuoteMsg());
                intent.putExtra("quoteMood", saveQuote.getQuoteMood());
                startActivity(intent);
                finish();
            }
        });
    }
}