package com.ettarak.services.imp;

import com.ettarak.dto.NoteDto;
import com.ettarak.entities.Note;
import com.ettarak.mapper.NoteMapper;
import com.ettarak.reponses.HttpResponse;
import com.ettarak.repository.NoteRepository;
import com.ettarak.requests.NoteRequest;
import com.ettarak.services.NoteService;
import com.ettarak.utils.DateFormatter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn =  Exception.class)
@Log4j2
public class NoteServiceImp implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    public HttpResponse<NoteDto> getNotes(Pageable pageable) {
        log.info("Services : Retrieved paged notes");

        Page<Note> page = noteRepository.findAll(pageable);

        List<NoteDto> notesDto = new ArrayList<>();
        for (Note note : page.getContent()) {
            NoteDto noteDto = new NoteDto();
            BeanUtils.copyProperties(note, noteDto);
            notesDto.add(noteDto);
        }

        return HttpResponse.<NoteDto>builder()
                .data(notesDto)
                .message(!notesDto.isEmpty() ? ( notesDto.size() + " Note(s) retrieved successfully") : "No data to display" )
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timeStamp(now())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                //.sort(page.getSort().toString())
                .build();
    }

    @Override
    public HttpResponse<NoteDto> addNote(NoteRequest noteRequest) {
        log.info("Services : Adding note");

        // Convert request → Entity
        Note note = noteMapper.toEntity(noteRequest);

        // Save entity
        Note savedNote = noteRepository.save(note);

        // Convert entity → DTO
        NoteDto dto = noteMapper.toDto(savedNote);

        return HttpResponse.<NoteDto>builder()
                .data(Collections.singletonList(dto))
                .message("Note added successfully")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .timeStamp(now())
                .build();
    }



    private String now() {
        return LocalDateTime.now().format(DateFormatter.FORMATTER);
    }
}
