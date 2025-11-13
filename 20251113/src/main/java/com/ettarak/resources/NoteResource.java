package com.ettarak.resources;

import com.ettarak.dto.NoteDto;
import com.ettarak.reponses.HttpResponse;
import com.ettarak.requests.NoteRequest;
import com.ettarak.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/notes")
@RequiredArgsConstructor
public class NoteResource {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<HttpResponse<NoteDto>> getNotes(Pageable pageable){
        return ResponseEntity.ok().body(noteService.getNotes(pageable));
    }

    @PostMapping
    public ResponseEntity<HttpResponse<NoteDto>> addNote(NoteRequest noteRequest){
        return ResponseEntity
                .created(URI.create("/api/notes"))
                .body(noteService.addNote(noteRequest));
    }
}
