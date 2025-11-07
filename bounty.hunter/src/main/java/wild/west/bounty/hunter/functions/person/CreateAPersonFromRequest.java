package wild.west.bounty.hunter.functions.person;

import wild.west.bounty.hunter.controller.dto.request.PersonRequest;
import wild.west.bounty.hunter.model.Person;

@FunctionalInterface
public interface CreateAPersonFromRequest<T extends Person> {

    Person createPerson(PersonRequest request, Class<T> personType);

}
