package com.example.roomtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.roomtest.adapter.WordAdapter;
import com.example.roomtest.db.Word;
import com.example.roomtest.db.WordDao;
import com.example.roomtest.db.WordDatabase;
import com.example.roomtest.db.WordViewModel;

import java.util.List;
import java.util.Objects;

import javax.sql.StatementEvent;

public class MainActivity extends AppCompatActivity {
    WordViewModel wordViewModel;
    RecyclerView recyclerView;
    WordAdapter wordAdapter1, wordAdapter2;
    Button buttonInsert, buttonClear;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(WordViewModel.class);
        recyclerView = findViewById(R.id.rv_words);
        wordAdapter1 = new WordAdapter(false);
        wordAdapter2 = new WordAdapter(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wordAdapter1);

        aSwitch = findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    recyclerView.setAdapter(wordAdapter2);
                } else {
                    recyclerView.setAdapter(wordAdapter1);
                }
            }
        });

        wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                wordAdapter1.setAllWords(words);
                wordAdapter2.setAllWords(words);

                wordAdapter1.notifyDataSetChanged();
                wordAdapter2.notifyDataSetChanged();
            }
        });

        buttonInsert = findViewById(R.id.btn_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] wordArr = {
                        "nation",
                        "hand",
                        "old",
                        "life",
                        "tell",
                        "write"
                };
                String[] chineseArr = {
                        "国家",
                        "手",
                        "老，旧",
                        "生活",
                        "告诉",
                        "写作"
                };
                Word[] words = new Word[wordArr.length];
                for (int i = 0; i < wordArr.length; i++) {
                    Word word = new Word();
                    word.setWord(wordArr[i]);
                    word.setChineseMeaning(chineseArr[i]);
                    words[i] = word;
                }
                wordViewModel.insertWords(words);
            }
        });

        buttonClear = findViewById(R.id.btn_deleteAll);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.deleteAllWords();
            }
        });

    }

}
