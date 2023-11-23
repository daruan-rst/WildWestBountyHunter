package wild.west.bounty.hunter.request;

import java.math.BigDecimal;

public record BountyContractRequest(String outlawName, String lastTown, String description, BigDecimal reward) {

}
