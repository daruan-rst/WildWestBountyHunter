package wild.west.bounty.hunter.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public record BountyContractRequest(String outlawName, String lastTown, String description, BigDecimal reward) {

}
