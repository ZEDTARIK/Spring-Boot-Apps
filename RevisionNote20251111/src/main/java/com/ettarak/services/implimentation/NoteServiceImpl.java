package com.ettarak.services.implimentation;

import com.ettarak.entities.Note;
import com.ettarak.exceptions.NoteNotFoundException;
import com.ettarak.models.HttpResponse;
import com.ettarak.repositories.NoteRepository;
import com.ettarak.services.NoteService;
import com.ettarak.utils.DateFormatter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public HttpResponse<Note> getNotes() {
        log.info("Service : retrieved notes");

        List<Note> notes = noteRepository.findAll();

        return HttpResponse.<Note>builder()
                .data(notes)
                .message(!notes.isEmpty() ? (notes.size() + " notes found.") : "No data found.")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(DateFormatter.FORMATTER))
                .build();
    }

    @Override
    public HttpResponse<Note> getNoteById(long id) {
        log.info("Service : getting note by id {}", id);

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found, try again with different code !!"));

        return HttpResponse.<Note>builder()
                .data(Collections.singleton(note))
                .message("Note found.")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(DateFormatter.FORMATTER))
                .build();
    }

    @Override
    public HttpResponse<Note> createNote(Note note) {
        log.info("Service : creating note");
        Note createdNote = noteRepository.save(note);
        return HttpResponse.<Note>builder()
                .data(Collections.singleton(createdNote))
                .message("Note created successfully.")
                .status(CREATED)
                .statusCode(CREATED.value())
                .timeStamp(LocalDateTime.now().format(DateFormatter.FORMATTER))
                .build();
    }

    @Override
    public HttpResponse<Note> updateNote(Note note) throws NoteNotFoundException {
        log.info("Service : updating note");
        Note updatedNote =noteRepository.findById(note.getId()).orElseThrow(() -> new NoteNotFoundException("Can not found this note for updated, please try with others code !!"));

        updatedNote.setTitle(note.getTitle());
        updatedNote.setContent(note.getContent());
        updatedNote.setLevel(note.getLevel());

        noteRepository.save(updatedNote);

        return HttpResponse.<Note>builder()
                .data(Collections.singleton(updatedNote))
                .message("Note updated successfully.")
                .status(ACCEPTED)
                .statusCode(ACCEPTED.value())
                .timeStamp(LocalDateTime.now().format(DateFormatter.FORMATTER))
                .build();

    }

    @Override
    public HttpResponse<Note> deleteNote(long id) {
        log.info("Service : deleting note by id {}", id);
        Note deleteNote = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException("Note not found, try again with different code !!"));
        noteRepository.delete(deleteNote);
        return HttpResponse.<Note>builder()
                .message("Note deleted successfully.")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(DateFormatter.FORMATTER))
                .build();
    }
}
