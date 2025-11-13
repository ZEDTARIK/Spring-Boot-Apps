package com.ettarak.mapper;

import com.ettarak.dto.NoteDto;
import com.ettarak.entities.Note;
import com.ettarak.requests.NoteRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface NoteMapper {
    // Convert request → entity
    Note toEntity(NoteRequest request);

    // Convert entity → DTO
    NoteDto toDto(Note note);
}
