package wild.west.bounty.hunter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import wild.west.bounty.hunter.model.Person;

import java.io.Serial;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class NotOutlawException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NotOutlawException(Person person) {
        super(String.format("%s is not an outlaw", person.getName()));
    }
}
