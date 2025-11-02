package wild.west.bounty.hunter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import wild.west.bounty.hunter.model.Outlaw;
import wild.west.bounty.hunter.model.Person;

import java.io.Serial;

@ResponseStatus(HttpStatus.CONFLICT)
public class PersonAlreadyDeadException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public PersonAlreadyDeadException(String ex) {
        super(ex);
    }
}
