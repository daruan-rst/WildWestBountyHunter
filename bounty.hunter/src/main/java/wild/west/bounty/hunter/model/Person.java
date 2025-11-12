package wild.west.bounty.hunter.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
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
        @Type(value = Citizen.class, name = "CITIZEN")})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "_person_type", discriminatorType = DiscriminatorType.STRING, length = 64)
@Entity
@Proxy(lazy = false)

/**
 * TODO: Escolher por tornar a classe Person como sealed tem as suas consequencias:
 * O Hibernate sempre cria proxies dinâmicos (HibernateProxy) das suas entidades para fazer lazy loading.
 * Mas Person é sealed — e classes sealed somente permitem subclasses listadas explicitamente no permits.
 * Isso faz com que o Hibernate tente gerar uma classe extra:  Person$HibernateProxy$qA1qJB9s
 *
 * Resta escolher o nosso veneno:
 * 1. Remover o sealed da classe Person - o que seria mais aconselhado
 * 2. Tornar a classe Person não-proxiável - e eu tomei essa decisão usando a anotação @Proxy(lazy = false)
 *  -> Acabei optando pela segunda por se tratar de um exercício prático de programação. Meu objetivo no momento é utilizar sealed classes
 * -> Vale lembrar que isso acaba com o lazy loading
 **/

/**
 * Eu entendi que sealed classes e proxies não combinam. Varios erros surgiram no endpoint PUT kill/{killerId}/{victimId}
 * TODO: Procurar alternativas para usar sealed classes
 */

public abstract class Person extends RepresentationModel<Person> implements Serializable  {

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
