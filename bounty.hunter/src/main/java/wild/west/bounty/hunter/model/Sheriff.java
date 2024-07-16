package wild.west.bounty.hunter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@JsonTypeName("SHERIFF")
@DiscriminatorValue("SHERIFF")
@Entity
public class Sheriff extends Person{

    @Serial
    private static final long serialVersionUID =  1L;

}
