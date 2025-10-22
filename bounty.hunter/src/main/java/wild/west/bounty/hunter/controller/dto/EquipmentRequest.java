package wild.west.bounty.hunter.controller.dto;

import java.math.BigDecimal;

public record EquipmentRequest(
        String equipmentName,
        BigDecimal value
) {
}
