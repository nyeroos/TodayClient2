package com.example.todayclient.ui.note;


import com.example.todayclient.repository.NoteRepository;
import com.example.todayclient.repository.Resource;
import com.example.todayclient.vo.Note;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class NoteViewModel extends ViewModel {

    private final NoteRepository mNoteRepository;

    @Inject
    public NoteViewModel(NoteRepository repository) {
        mNoteRepository = repository;
    }
    public void add(String title, String description, int priority){
        Note note = new Note(title, description, priority);
        mNoteRepository.insert(note);
    }

    public LiveData<Resource<List<Note>>> getNote(int id){
        return mNoteRepository.getNote(id);
    }
}
