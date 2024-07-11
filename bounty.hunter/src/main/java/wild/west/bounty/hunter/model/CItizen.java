package wild.west.bounty.hunter.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@JsonTypeName("CITIZEN")
@DiscriminatorValue("CITIZEN")
@Entity
public class CItizen extends Person{
}
