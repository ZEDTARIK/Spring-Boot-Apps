package com.ettarak.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Collection;

@SuperBuilder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpResponse<T> implements Serializable {
    protected String timeStamp;
    protected HttpStatus status;
    protected int statusCode;
    protected String message;
    protected Collection<? extends T> data;
    protected String reason;
    protected String developerMessage;
}
