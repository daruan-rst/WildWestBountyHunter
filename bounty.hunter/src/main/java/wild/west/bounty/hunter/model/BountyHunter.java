package wild.west.bounty.hunter.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wild.west.bounty.hunter.model.enums.Reputation;

import java.io.Serial;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonTypeName("BOUNTY_HUNTER")
@DiscriminatorValue("BOUNTY_HUNTER")
@Entity
public class BountyHunter extends Person {

    @Serial
    private static final long serialVersionUID =  1L;


    @Column(name = "reputation", nullable = false, length = 80)
    @Enumerated(EnumType.STRING)
    private Reputation reputation;


}


