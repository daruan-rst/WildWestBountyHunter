package wild.west.bounty.hunter.functions.equipment;

import wild.west.bounty.hunter.controller.dto.EquipmentRequest;
import wild.west.bounty.hunter.model.Equipment;

@FunctionalInterface
public interface CreateAnEquipmentFromRequest {

    Equipment CreateAnEquipmentFromRequest(EquipmentRequest request);

}
