package one.digitalinnovation.contact.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public class ApiErrors implements Serializable {

    private List<String> errors;

    public ApiErrors(String error) {
        errors = Arrays.asList(error);
    }
}
