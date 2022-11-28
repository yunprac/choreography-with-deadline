package choreography.with.deadline.domain;

import choreography.with.deadline.domain.StockDecreaseFailed;
import choreography.with.deadline.domain.StockDecreased;
import choreography.with.deadline.domain.StockIncreased;
import choreography.with.deadline.ProductApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="Inventory_table")
@Data

public class Inventory  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private String productName;
    
    
    
    
    
    private String productImage;
    
    
    
    
    
    private Integer stock;

    @PostPersist
    public void onPostPersist(){


        StockDecreaseFailed stockDecreaseFailed = new StockDecreaseFailed(this);
        stockDecreaseFailed.publishAfterCommit();

    }
    @PostUpdate
    public void onPostUpdate(){


        StockDecreased stockDecreased = new StockDecreased(this);
        stockDecreased.publishAfterCommit();

    }
    @PreUpdate
    public void onPreUpdate(){


        StockIncreased stockIncreased = new StockIncreased(this);
        stockIncreased.publishAfterCommit();

    }

    public static InventoryRepository repository(){
        InventoryRepository inventoryRepository = ProductApplication.applicationContext.getBean(InventoryRepository.class);
        return inventoryRepository;
    }




    public static void stockDecrease(DeliveryStarted deliveryStarted){

        /** Example 1:  new item 
        Inventory inventory = new Inventory();
        repository().save(inventory);

        StockDecreased stockDecreased = new StockDecreased(inventory);
        stockDecreased.publishAfterCommit();
        StockDecreaseFailed stockDecreaseFailed = new StockDecreaseFailed(inventory);
        stockDecreaseFailed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(deliveryStarted.get???()).ifPresent(inventory->{
            
            inventory // do something
            repository().save(inventory);

            StockDecreased stockDecreased = new StockDecreased(inventory);
            stockDecreased.publishAfterCommit();
            StockDecreaseFailed stockDecreaseFailed = new StockDecreaseFailed(inventory);
            stockDecreaseFailed.publishAfterCommit();

         });
        */

        
    }
    public static void compensate(DeliveryCancelled deliveryCancelled){

        /** Example 1:  new item 
        Inventory inventory = new Inventory();
        repository().save(inventory);

        StockIncreased stockIncreased = new StockIncreased(inventory);
        stockIncreased.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(deliveryCancelled.get???()).ifPresent(inventory->{
            
            inventory // do something
            repository().save(inventory);

            StockIncreased stockIncreased = new StockIncreased(inventory);
            stockIncreased.publishAfterCommit();

         });
        */

        
    }


}
