package com.example.todayclient.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.todayclient.AppExecutors;
import com.example.todayclient.api.ApiResponse;
import com.example.todayclient.api.TodayService;
import com.example.todayclient.db.NoteDao;
import com.example.todayclient.vo.Note;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class NoteRepository {
    private final NoteDao noteDao;
    private final TodayService todayService;
    private final AppExecutors appExecutors;

    @Inject
    public NoteRepository(AppExecutors appExecutors, NoteDao noteDao, TodayService todayService) {
        this.noteDao = noteDao;
        this.todayService = todayService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<Note>>> getNote(int id) {
        return new NetworkBoundResource<List<Note>,Note>() {
            @Override
            protected void saveCallResult(@NonNull Note item) {
                noteDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Note> data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<List<Note>> loadFromDb() {
                return noteDao.findById(id);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Note>> createCall() {
                return todayService.getNote(id);
            }
        }.asLiveData();
    }


    public void insert(Note note) {

    }
}
