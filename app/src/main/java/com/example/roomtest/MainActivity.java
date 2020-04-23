package com.example.roomtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.roomtest.db.Word;
import com.example.roomtest.db.WordDao;
import com.example.roomtest.db.WordDatabase;
import com.example.roomtest.db.WordViewModel;

import java.util.List;
import java.util.Objects;

import javax.sql.StatementEvent;

public class MainActivity extends AppCompatActivity {
    WordViewModel wordViewModel;
    TextView textView;
    Button buttonInsert, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(WordViewModel.class);

        textView = findViewById(R.id.tv_content);
        wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                StringBuilder str = new StringBuilder();
                for (Word word :
                        words) {
                    str.append(word.getId()).append(" : ").append(word.getWord()).append(" -- ").append(word.getChineseMeaning()).append("\n");
                }
                textView.setText(str.toString());
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
