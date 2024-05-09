package wild.west.bounty.hunter.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "town")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Town extends RepresentationModel<Town> implements Serializable {

    @Serial
    private static final long serialVersionUID =  1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 80)
    private String townName;

    @OneToMany(mappedBy = "town")
    @JsonManagedReference
    private Set<Saloon> saloons;

}
