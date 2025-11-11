package com.ettarak.services;

import com.ettarak.entities.Note;
import com.ettarak.models.HttpResponse;

public interface NoteService {

    HttpResponse<Note> getNotes();
    HttpResponse<Note> getNoteById(long id);
    HttpResponse<Note> createNote(Note note);
    HttpResponse<Note> updateNote(Note note);
    HttpResponse<Note> deleteNote(long id);
}
