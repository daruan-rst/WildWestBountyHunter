package wild.west.bounty.hunter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "saloon")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Saloon extends RepresentationModel<Saloon> implements Serializable {

    @Serial
    private static final long serialVersionUID =  1L;

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "saloon_name", nullable = false, length = 80)
    private String saloonName;

    @ManyToOne
    @JoinColumn(name = "town_name", nullable = false)
    private Town town;


}
