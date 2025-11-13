package com.ettarak.reponses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Collection;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpResponse<T> implements Serializable {
    protected String timeStamp;
    protected HttpStatus status;
    protected int statusCode;
    protected String message;
    protected Collection<? extends T> data;
    protected String errorMessage;
    protected String developerMessage;
    // Pagination metadata (optional, filled when response is paginated)
    protected Integer pageNumber;
    protected Integer pageSize;
    protected Long totalElements;
    protected Integer totalPages;
    protected String sort;
}
