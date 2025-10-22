package wild.west.bounty.hunter.controller.dto;

import java.math.BigDecimal;
import java.util.List;

public record PersonRequest(
        String name,
        BigDecimal money,
        Long originId,
        List<EquipmentRequest> equipmentRequests
) {
}


