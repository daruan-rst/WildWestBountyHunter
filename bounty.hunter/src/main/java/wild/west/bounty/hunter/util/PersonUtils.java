package wild.west.bounty.hunter.util;

import wild.west.bounty.hunter.exceptions.PersonAlreadyDeadException;
import wild.west.bounty.hunter.model.Person;

public class PersonUtils {

    public static void validateIfAlive(Person person) {
        if (!person.isAlive()) {
            throw new PersonAlreadyDeadException(String.format("%s is already dead", person.getName()));
        }
    }

    public static void validateKiller(Person killer){
        if (!killer.isAlive()) {
            throw new PersonAlreadyDeadException(String.format("%s is dead and won't be able to kill anybody", killer.getName()));
        }
    }
}
