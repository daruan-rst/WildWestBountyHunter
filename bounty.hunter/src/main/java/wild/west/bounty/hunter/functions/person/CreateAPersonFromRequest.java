package wild.west.bounty.hunter.functions.person;

import wild.west.bounty.hunter.controller.dto.PersonRequest;
import wild.west.bounty.hunter.model.BountyHunter;
import wild.west.bounty.hunter.model.Person;

@FunctionalInterface
public interface CreateAPersonFromRequest {

    Person createPerson(PersonRequest request, Class<? extends Person> personType);

}
