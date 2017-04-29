package com.example.ernest.kidsmate1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static com.example.ernest.kidsmate1.MainActivity.mDBHelper;

/**
 * Created by User on 2017-04-27.
 */

public class ResultDic extends AppCompatActivity {
    private TextView wordText;
    private TextView meanText;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_dic);

        wordText = (TextView) findViewById(R.id.word);
        meanText = (TextView) findViewById(R.id.mean);
        Intent intent = getIntent();
        String word = intent.getStringExtra("word");        //사전(Dictionary class)에서 전달한 단어를 받아오는 부분
        wordText.setText(word);

        String mean = null;

        try{db = mDBHelper.openDatabase();}catch (Exception e){e.printStackTrace();}        //dbhelper를 통한 실제 DB 오픈
        try {Cursor cursor = db.rawQuery("SELECT mean FROM dic WHERE word = '" + word + "' COLLATE NOCASE", null);
            cursor.moveToFirst();                                           //db를 이용해서 질의하고 커서를 이용해서 활용 하는 부분
            if (!cursor.isAfterLast()) {
                mean = cursor.getString(0);
            }
            else {
                mean = "결과가 없습니다.";
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();                     //db와 helper를 모두 종료해줍니다
        mDBHelper.close();

        meanText.setText(mean);         //결과 출력
    }

    @Override
    public void onBackPressed() {       //뒤로가기 버튼 누르면 현재 activity 제거
        super.onBackPressed();
        finish();
    }
}
