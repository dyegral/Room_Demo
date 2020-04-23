package com.example.roomtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.roomtest.db.Word;
import com.example.roomtest.db.WordDao;
import com.example.roomtest.db.WordDatabase;

import java.util.List;

import javax.sql.StatementEvent;

public class MainActivity extends AppCompatActivity {
    WordDatabase wordDatabase;
    WordDao wordDao;
    TextView textView;
    Button buttonInsert, buttonUpdate, buttonDelete, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordDatabase = Room.databaseBuilder(this, WordDatabase.class, "word_database")
                .allowMainThreadQueries()
                .build();
        wordDao = wordDatabase.getWordDao();

        textView = findViewById(R.id.tv_content);
        updataView();

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
                for (int i = 0; i < wordArr.length; i++) {
                    Word word = new Word();
                    word.setWord(wordArr[i]);
                    word.setChineseMeaning(chineseArr[i]);
                    wordDao.insertWords(word);
                }
                updataView();
            }
        });

        buttonClear = findViewById(R.id.btn_clear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordDao.deleteAll();
                updataView();
            }
        });

    }

    void updataView() {
        List<Word> allWords = wordDao.getAllWords();

        String str = "";
        for (Word word :
                allWords) {
            str += word.getId() + " : " + word.getWord() + " -- " + word.getChineseMeaning() + "\n";
        }

        textView.setText(str);
    }

}
