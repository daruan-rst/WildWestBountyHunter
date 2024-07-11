package wild.west.bounty.hunter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import wild.west.bounty.hunter.model.enums.Reputation;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType", visible = true)
@JsonTypeName("BOUNTY_HUNTER")
@DiscriminatorValue("BOUNTY_HUNTER")
@Entity
public class BountyHunter extends Person {

    @Serial
    private static final long serialVersionUID =  1L;


    @Column(name = "reputation", nullable = false, length = 80)
    private Reputation reputation;


}


