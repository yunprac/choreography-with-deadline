package choreography.with.deadline.domain;

import choreography.with.deadline.domain.*;
import choreography.with.deadline.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class StockDecreaseFailed extends AbstractEvent {

    private Long id;
    private String productName;
    private String productImage;
    private Integer stock;

    public StockDecreaseFailed(Inventory aggregate){
        super(aggregate);
    }
    public StockDecreaseFailed(){
        super();
    }
}
