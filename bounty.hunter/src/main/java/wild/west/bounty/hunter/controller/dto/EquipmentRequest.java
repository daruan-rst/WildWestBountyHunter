package wild.west.bounty.hunter.controller.dto;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record EquipmentRequest(
        Long id,
        String equipmentName,
        @Min(value = 0)

        BigDecimal value
) {
}
