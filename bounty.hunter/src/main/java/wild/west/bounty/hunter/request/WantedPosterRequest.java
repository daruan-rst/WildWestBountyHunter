package wild.west.bounty.hunter.request;

import java.math.BigDecimal;

public record WantedPosterRequest(String outlawName, String lastTown, String description, BigDecimal reward) {

}
