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
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private String orderId;
    
    
    
    
    
    private String productId;
    
    
    
    
    
    private String productName;
    
    
    
    
    
    private Integer qty;
    
    
    
    
    
    private String customerId;
    
    
    
    
    
    private String address;
    
    
    
    
    
    private String status;

    @PostPersist
    public void onPostPersist(){


        DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        deliveryStarted.publishAfterCommit();



        DeliveryFailed deliveryFailed = new DeliveryFailed(this);
        deliveryFailed.publishAfterCommit();



        DeliveryCancelled deliveryCancelled = new DeliveryCancelled(this);
        deliveryCancelled.publishAfterCommit();

    }

    public static DeliveryRepository repository(){
        DeliveryRepository deliveryRepository = DeliveryApplication.applicationContext.getBean(DeliveryRepository.class);
        return deliveryRepository;
    }




    public static void startDelivery(OrderCreated orderCreated){

        /** Example 1:  new item 
        Delivery delivery = new Delivery();
        repository().save(delivery);

        DeliveryFailed deliveryFailed = new DeliveryFailed(delivery);
        deliveryFailed.publishAfterCommit();
        DeliveryStarted deliveryStarted = new DeliveryStarted(delivery);
        deliveryStarted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(orderCreated.get???()).ifPresent(delivery->{
            
            delivery // do something
            repository().save(delivery);

            DeliveryFailed deliveryFailed = new DeliveryFailed(delivery);
            deliveryFailed.publishAfterCommit();
            DeliveryStarted deliveryStarted = new DeliveryStarted(delivery);
            deliveryStarted.publishAfterCommit();

         });
        */

        
    }
    public static void compensate(OrderRejected orderRejected){

        /** Example 1:  new item 
        Delivery delivery = new Delivery();
        repository().save(delivery);

        */

        /** Example 2:  finding and process
        
        repository().findById(orderRejected.get???()).ifPresent(delivery->{
            
            delivery // do something
            repository().save(delivery);


         });
        */

        
    }


}
