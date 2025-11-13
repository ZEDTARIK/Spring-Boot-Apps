package com.ettarak.mapper;

import com.ettarak.dto.NoteDto;
import com.ettarak.entities.Note;
import com.ettarak.requests.NoteRequest;
import org.springframework.stereotype.Component;

@Component
public class NoteMapperImpl implements NoteMapper {

    @Override
    public Note toEntity(NoteRequest request) {
        if (request == null) return null;
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        return note;
    }

    @Override
    public NoteDto toDto(Note note) {
        if (note == null) return null;
        return new NoteDto(
                note.getCodeId(),
                note.getTitle(),
                note.getContent()
        );
    }
}
