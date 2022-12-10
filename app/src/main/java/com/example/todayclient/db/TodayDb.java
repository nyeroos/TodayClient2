package com.example.todayclient.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todayclient.vo.Note;
import com.example.todayclient.vo.User;

@Database(entities = {User.class, Note.class}, version = 1, exportSchema = true)
@TypeConverters({})

public abstract class TodayDb extends RoomDatabase {

    abstract public UserDao userDao();

    abstract public NoteDao noteDao();
}
