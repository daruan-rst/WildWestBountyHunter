package wild.west.bounty.hunter.controller.dto.request;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record WantedPosterRequest(String outlawName,
                                  String lastTown,
                                  String description,
                                  @Min(value = 0)
                                  BigDecimal reward) {

}
