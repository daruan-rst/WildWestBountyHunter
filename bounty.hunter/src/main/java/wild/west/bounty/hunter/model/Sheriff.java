package wild.west.bounty.hunter.model;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue("SHERIFF")
public class Sheriff extends Person{
}
