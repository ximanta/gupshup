package hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.gupshup.discoveryregistery.EurekaServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=EurekaServiceApplication.class)
public class ProductDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
