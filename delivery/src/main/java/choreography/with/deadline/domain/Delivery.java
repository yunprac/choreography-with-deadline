package choreography.with.deadline.domain;

import choreography.with.deadline.domain.DeliveryStarted;
import choreography.with.deadline.domain.DeliveryFailed;
import choreography.with.deadline.domain.DeliveryCancelled;
import choreography.with.deadline.DeliveryApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="Delivery_table")
@Data

public class Delivery  {
    
    @Id
    // @GeneratedValue(strategy=GenerationType.AUTO) // FOCUS: disable auto-gen for key. 
    private String orderId;

    private String productId;
    private String productName;
    private Integer qty;
    private String customerId;
    private String address;
    
    private String status;


    public static DeliveryRepository repository(){
        DeliveryRepository deliveryRepository = DeliveryApplication.applicationContext.getBean(DeliveryRepository.class);
        return deliveryRepository;
    }


    public static void startDelivery(OrderCreated orderCreated){

        Delivery delivery = new Delivery();
        delivery.setOrderId(String.valueOf(orderCreated.getId()));      // Prevent duplicate execution of the same message
        delivery.setCustomerId(orderCreated.getCustomerId());
        delivery.setProductId(orderCreated.getProductId());
        delivery.setProductName(orderCreated.getProductName());
        delivery.setQty(orderCreated.getQty());
        repository().save(delivery);

        DeliveryStarted deliveryStarted = new DeliveryStarted(delivery);
        deliveryStarted.publishAfterCommit();        
    }

    public static void compensate(OrderRejected orderRejected){
        
        repository().findByOrderId(String.valueOf(orderRejected.getId())).ifPresent/*OrElse*/(delivery->{
            new DeliveryCancelled(delivery).publishAfterCommit();
            repository().delete(delivery);
         }
         
        //, ()->{
        //     throw new RuntimeException("No Delivery transaction is found for orderId" + orderRejected.getId());
        //  }
         );

        
    }


}