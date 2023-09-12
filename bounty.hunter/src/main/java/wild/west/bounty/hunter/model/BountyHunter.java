package wild.west.bounty.hunter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import wild.west.bounty.hunter.model.enums.Reputation;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "bounty_hunter")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BountyHunter extends RepresentationModel<BountyHunter> implements Serializable {

    @Serial
    private static final long serialVersionUID =  1L;

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cowboy_name", nullable = false, length = 80)
    private String cowboyName;

    @Column(name = "reputation", nullable = false, length = 80)
    private String reputation;

    @Column(name = "town_name", nullable = false, length = 80)
    private String origin;


}
