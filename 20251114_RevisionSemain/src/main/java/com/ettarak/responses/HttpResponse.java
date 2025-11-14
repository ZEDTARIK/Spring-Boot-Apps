package com.ettarak.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Collection;

@Getter  @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "response")  // <response> ... </response>

public class HttpResponse<T> {
    protected String timStamp;
    protected HttpStatus status;
    protected int statusCode;
    protected String message;

    @JacksonXmlElementWrapper(localName = "data")
    @JacksonXmlProperty(localName = "employee")   // node name for each element when T = Employee
    protected Collection<? extends T> data;

    protected String errorMessage;
    protected String developerMessage;
}
