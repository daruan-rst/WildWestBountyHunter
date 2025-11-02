package wild.west.bounty.hunter.functions.equipment;

import wild.west.bounty.hunter.model.Equipment;

public class EquipmentFunctions {

    public static CreateAnEquipmentFromRequest mapEquipment = ((request) -> {
        Equipment equipment = new Equipment();
        equipment.setEquipmentName(request.equipmentName());
        equipment.setValue(request.value());
        return equipment;
    });
}
