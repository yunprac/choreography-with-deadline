package choreography.with.deadline.common;


import choreography.with.deadline.ProductApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = { ProductApplication.class })
public class CucumberSpingConfiguration {
    
}
