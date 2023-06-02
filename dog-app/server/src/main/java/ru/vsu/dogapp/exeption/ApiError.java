package ru.vsu.dogapp.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {

    private HttpStatus status;
    private String message;
    private Map<String, String> errors;
}
