package wild.west.bounty.hunter.functions.person;

import wild.west.bounty.hunter.model.*;

import java.util.ArrayList;
import java.util.List;

public class PersonFunctions {

    public static CreateAPersonFromRequest mapPerson = ((request, personType) -> {
        Person person = switch (personType.getSimpleName()) {
            case "Sheriff" -> new Sheriff(); // TODO: FIND A WAY TO USE CLASS PARAMETERS
            case "BountyHunter" -> new BountyHunter();
            case "Outlaw" -> new Outlaw();
            case "Citizen" -> new Citizen();
            default -> throw new IllegalArgumentException("Unknown person type: " + personType.getSimpleName());
        };

        person.setName(request.name());
        person.setMoney(request.money());
//        person.setOrigin(origin);

        if (request.equipmentRequests() != null && !request.equipmentRequests().isEmpty()) {
            List<Equipment> equipmentList = request.equipmentRequests().stream()
                    .map(eq -> {
                        Equipment e = new Equipment();
                        e.setEquipmentName(eq.equipmentName());
                        e.setValue(eq.value());
                        e.setPerson(person);
                        return e;
                    })
                    .toList();
            person.setEquipments(new ArrayList<>(equipmentList));
        }

        person.setAlive(true);
        return person;
    });
}
