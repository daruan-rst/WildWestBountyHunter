package wild.west.bounty.hunter.wrappedModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import wild.west.bounty.hunter.model.BountyHunter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class WrappedBountyHunter implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private BountyHunterEmbedded embedded;

    @NoArgsConstructor
    @Getter
    @Setter
    @EqualsAndHashCode
    public static class BountyHunterEmbedded{
        private List<BountyHunter> bountyHunterList;
    }

}
