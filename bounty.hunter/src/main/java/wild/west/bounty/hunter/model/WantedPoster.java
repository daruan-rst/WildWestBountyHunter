package wild.west.bounty.hunter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="wanted_poster")
public class WantedPoster extends RepresentationModel<WantedPoster> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Serial
    private static final long serialVersionUID =  1L;

    @Column(name="poster_name")
    private String posterName;

    @Column(name="reward")
    private BigDecimal reward;

    @OneToOne
    @JoinColumn(name="outlaw")
    private Outlaw outlaw;

    @Column(name="outlaw_description")
    private String outlawDescription;

    @OneToOne
    @JoinColumn(name="last_place")
    private Town lastPlace;

}
