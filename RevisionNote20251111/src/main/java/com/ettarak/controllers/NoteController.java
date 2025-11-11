package com.ettarak.controllers;

import com.ettarak.entities.Note;
import com.ettarak.exceptions.NoteNotFoundException;
import com.ettarak.models.HttpResponse;
import com.ettarak.services.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<HttpResponse<Note>> getNotes() {
        return ResponseEntity.ok().body(noteService.getNotes());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<HttpResponse<Note>> getNoteById(@PathVariable long id) throws NoteNotFoundException {
        return ResponseEntity.ok().body(noteService.getNoteById(id));
    }

    @PostMapping
    public ResponseEntity<HttpResponse<Note>> createNote(@RequestBody @Valid Note note) {
        HttpResponse<Note> response = noteService.createNote(note);
        Long createdId = response.getData() != null ? response.getData().stream().findFirst().map(Note::getId).orElse(null) : null;
        URI location = createdId != null ? URI.create("/notes/" + createdId) : URI.create("/notes");
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping
    public ResponseEntity<HttpResponse<Note>> updateNote(@RequestBody @Valid Note note) {
        return ResponseEntity.accepted()
                .body(noteService.updateNote(note));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpResponse<Note>> deleteNote(@PathVariable long id) {
        return ResponseEntity.ok().body(noteService.deleteNote(id));
    }
}
