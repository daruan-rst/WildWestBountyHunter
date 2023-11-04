package wild.west.bounty.hunter.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "person")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType", visible = true)
@JsonTypeName("PERSON")
@JsonSubTypes({@Type(value = Sheriff.class, name = "SHERIFF"),
        @Type(value = BountyHunter.class, name = "BOUNTY_HUNTER")})
public class Person extends RepresentationModel<Person> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "_OBJECT_TYPE", insertable = false, updatable = false)
    @JsonIgnore
    private String objectType;
}
