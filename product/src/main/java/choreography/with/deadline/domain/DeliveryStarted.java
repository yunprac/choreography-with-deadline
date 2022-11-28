package choreography.with.deadline.domain;

import choreography.with.deadline.domain.*;
import choreography.with.deadline.infra.AbstractEvent;
import lombok.*;
import java.util.*;
@Data
@ToString
public class DeliveryStarted extends AbstractEvent {

    private String orderId;
    private String productId;
    private String productName;
    private Integer qty;
    private String customerId;
    private String address;
    private String status;
}


