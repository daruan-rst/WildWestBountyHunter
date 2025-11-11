package wild.west.bounty.hunter.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.io.Serial;

@JsonTypeName("CITIZEN")
@DiscriminatorValue("CITIZEN")
@Entity
public final class Citizen extends Person{

    @Serial
    private static final long serialVersionUID =  1L;
}
