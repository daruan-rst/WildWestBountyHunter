package wild.west.bounty.hunter.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Table(name = "person")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "personType", visible = true)
@JsonTypeName("PERSON")
@JsonSubTypes({@Type(value = Sheriff.class, name = "SHERIFF"),
        @Type(value = BountyHunter.class, name = "BOUNTY_HUNTER"),
        @Type(value = Outlaw.class, name = "OUTLAW"),
        @Type(value = Outlaw.class, name = "CITIZEN")})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "_person_type", discriminatorType = DiscriminatorType.STRING, length = 64)
@Entity
public abstract class Person extends RepresentationModel<Person> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "_person_type", insertable = false, updatable = false)
    @JsonIgnore
    private String objectType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @ManyToOne
    @JoinColumn(name = "town_id")
    private Town origin;

    @Column(name="money")
    private BigDecimal money;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Equipment> equipments;

    @Column(name="alive")
    private boolean alive;


}
