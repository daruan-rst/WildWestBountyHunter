package wild.west.bounty.hunter.response;

import wild.west.bounty.hunter.model.Outlaw;
import wild.west.bounty.hunter.model.Person;

public class MurderResponse {

    private Person killer;
    private Person victim;

    public MurderResponse(Person killer, Person victim) {
    }

    public String toString(MurderResponse response) {
        String finalMessage = response.killer.getName().concat (
                killer instanceof Outlaw ?
                " has it's bounty has risen" :
                  " will be currently be considered an outlaw and a bounty for it's head was placed");
        return String.format("%s killed %s using an %s. %s", killer, victim, killer.getEquipments().get(0),  finalMessage);
    }
}