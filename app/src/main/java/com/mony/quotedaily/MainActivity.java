package com.mony.quotedaily;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mony.quotedaily.controller.API;
import com.mony.quotedaily.dao.QuoteDao;
import com.mony.quotedaily.dao.SaveQuote;
import com.mony.quotedaily.model.QuoteApiModel;
import com.mony.quotedaily.viewmodel.QuoteViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView saveList;
    Button generateButton, shareQuote, designButton;
    TextView quoteText, authorText;
    EditText ownQuote;
    FloatingActionButton fab;

    String quoteTextJson, quoteMoodJson;

    private QuoteViewModel quoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveList = findViewById(R.id.saveListBtn);
        fab = findViewById(R.id.fab);
        generateButton = findViewById(R.id.generateButton);
        quoteText = findViewById(R.id.quoteText);
        authorText = findViewById(R.id.authorText);
        shareQuote = findViewById(R.id.shareQuote);
        designButton = findViewById(R.id.designButton);
        ownQuote = findViewById(R.id.userQuote);

        quoteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(QuoteViewModel.class);
//        quoteViewModel.getBookMarkList().observe(this, new Observer<List<SaveQuote>>() {
//            @Override
//            public void onChanged(List<SaveQuote> quotes) {
//                adapter.setNotes(quotes);
//            }
//        });

        SpannableString content = new SpannableString("Design your quote");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        shareQuote.setText(content);

        designButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ownQuote.setVisibility(View.VISIBLE);
                authorText.setVisibility(View.INVISIBLE);
                quoteText.setVisibility(View.INVISIBLE);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saveQuote;
                String saveQuoteMode;
                String saveQuoteAuthor;
                if (!ownQuote.getText().toString().equals("")){
                    saveQuote = ownQuote.getText().toString();
                    saveQuoteMode = ownQuote.getText().toString();
                    saveQuoteAuthor = "No Name";
                }else{
                    saveQuote = quoteTextJson;
                    saveQuoteMode = quoteMoodJson;
                    saveQuoteAuthor = authorText.getText().toString();
                }
                SaveQuote quote = new SaveQuote(saveQuote, saveQuoteMode, saveQuoteAuthor);
                quoteViewModel.insert(quote);
            }
        });

        saveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookMarkActivity.class));
            }
        });
    }

    public void generateQuote(View view){
        quoteText.setVisibility(View.VISIBLE);
        authorText.setVisibility(View.VISIBLE);
        ownQuote.setVisibility(View.INVISIBLE);
        QuoteService quoteService = API.getClient().create(QuoteService.class);
        Call<QuoteApiModel> call = quoteService.getDailyQuote();
        call.enqueue(new Callback<QuoteApiModel>() {
            @Override
            public void onResponse(Call<QuoteApiModel> call, Response<QuoteApiModel> response) {
                if (response.isSuccessful()){
                    quoteText.setText(response.body().getQuote().getBody());
                    authorText.setText(response.body().getQuote().getAuthor());
                    quoteTextJson = response.body().getQuote().getBody();
                    int listTag = response.body().getQuote().getTags().size();
                    for (int i = 0; i < listTag; i++) {
                        quoteMoodJson = response.body().getQuote().getTags().get(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<QuoteApiModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error API "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void shareQuote(View view){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else {
//            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            if (!ownQuote.getText().toString().equals("")){
                Intent intent = new Intent(MainActivity.this, DesignScreen.class);
                intent.putExtra("quoteText", ownQuote.getText().toString());
                intent.putExtra("quoteMood", ownQuote.getText().toString());
                startActivity(intent);
            }else{
                Intent intent = new Intent(MainActivity.this, DesignScreen.class);
                intent.putExtra("quoteText", quoteTextJson);
                intent.putExtra("quoteMood", quoteMoodJson);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(MainActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}