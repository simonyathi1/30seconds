package com.example.temp.a30seconds.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by temp on 20/09/2017.
 */

public class QuestionsDBHelper extends SQLiteOpenHelper {


    public QuestionsDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table questions"+
                "(id integer primary key, question text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS questions");
        onCreate(sqLiteDatabase);
    }

    public boolean insertQuestion(String question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("question",question);
        db.insert("questions",null,contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from questions where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "questions");
        return numRows;
    }

    public boolean updateQuestions(Integer id, String question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("question",question);
        db.update("questions",contentValues,"id=?",new String[]{Integer.toString(id)});
        return true;
    }
    public Integer deleteQuestion (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("questions",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllQuestions() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from questions", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("question")));
            res.moveToNext();
        }
        return array_list;
    }
}
