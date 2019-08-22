package mockito;

import com.sapient.feecalc.contoller.RestEndPoints;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestEndPoints.class)
public class RestEndPointsTest {

    @MockBean
    RestTemplate restTemplate;

    @Autowired
    public RestEndPoints controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTemplate() {
        ResponseEntity<String> myEntity = new ResponseEntity<String>("abcd", HttpStatus.OK);
        mockRequestResponseWithoutLabel(myEntity);
        String res = controller.testRestTemplate();
        Assert.assertEquals(res,"abcd");
    }

    private void mockRequestResponseWithoutLabel(ResponseEntity<?> response) {
        Mockito.when(restTemplate.exchange(anyString(),
                Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
                Mockito.any(Class.class))).thenReturn(response);
    }

}
