package com.ettarak.services;

import com.ettarak.dto.NoteDto;
import com.ettarak.reponses.HttpResponse;
import com.ettarak.requests.NoteRequest;
import org.springframework.data.domain.Pageable;

public interface NoteService {
    HttpResponse<NoteDto> getNotes(Pageable pageable);
    HttpResponse<NoteDto> addNote(NoteRequest noteRequest);
}
