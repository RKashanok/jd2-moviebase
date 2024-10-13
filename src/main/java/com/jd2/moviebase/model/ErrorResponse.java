package com.jd2.moviebase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    HttpStatusCode status;
    String message;
    String details;
}
