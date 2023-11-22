package wild.west.bounty.hunter.response;

import wild.west.bounty.hunter.model.Outlaw;
import wild.west.bounty.hunter.model.Person;

public class MurderResponse {

    Person killer;
    Person victim;

    public MurderResponse(Person killer, Person victim) {
    }

    public String toString(MurderResponse response) {
        String finalMessage = killer instanceof Outlaw ?
                "It's bounty has risen to %s" :
                "He will be currently be considered an outlaw and a bounty for it's head was placed";
        return String.format("%s killed %s using an %s. %s", killer, victim, finalMessage);
    }
}