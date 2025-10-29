package wild.west.bounty.hunter.controller.dto;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.List;

public record PersonRequest(
        String name,
        @Min(value = 0)
        BigDecimal money,
        Long originId,
        List<EquipmentRequest> equipmentRequests
) {
}


