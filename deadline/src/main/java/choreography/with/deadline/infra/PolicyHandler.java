package choreography.with.deadline.infra;

import javax.naming.NameParser;
import javax.transaction.Transactional;
import choreography.with.deadline.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import choreography.with.deadline.domain.*;


@Service
@Transactional
@Configuration
@EnableScheduling
public class PolicyHandler{
    @Autowired DeadlineRepository deadlineRepository;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderCreated'")
    public void wheneverOrderCreated_Schedule(@Payload OrderCreated orderCreated){

        OrderCreated event = orderCreated;
        System.out.println("\n\n##### listener Schedule : " + orderCreated + "\n\n");

        // Sample Logic //
        Deadline.schedule(event);
    }

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderPlaced'")
    public void wheneverOrderPlaced_delete(@Payload OrderPlaced orderPlaced){

        OrderPlaced event = orderPlaced;
        System.out.println("\n\n##### listener RemoveDeadline : " + orderPlaced + "\n\n");

        // Sample Logic //
        Deadline.removeDeadline(event);       
    }

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderRejected'")
    public void wheneverOrderPlaced_delete(@Payload OrderRejected orderRejected){

        OrderRejected event = orderRejected;
        System.out.println("\n\n##### listener RemoveDeadline : " + orderRejected + "\n\n");

        // Sample Logic //
        Deadline.delete(event);       
    }

    @Scheduled(fixedRate = 5000) //FOCUS: every 5 seconds. 5초에 한번씩
    public void checkDeadline(){
        Deadline.sendDeadlineEvents();
    }

}


