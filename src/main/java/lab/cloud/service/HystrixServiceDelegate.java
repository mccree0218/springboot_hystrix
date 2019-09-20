package lab.cloud.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service("hystrixServiceDelegate")
public class HystrixServiceDelegate {

	@Autowired
    RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "callUserServiceAndGetData_Fallback")
	public String callUserServiceAndGetData(String userName) {
		System.out.println("Getting User name:" +userName);
		String response = restTemplate
                .exchange("http://localhost:8098/getUserDetailsForUser/{userName}"
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<String>() {
            }, userName).getBody();
 
        System.out.println("Response Received as " + response + " -  " + new Date());
 
        return "NORMAL FLOW !!! - USER Name -  " + userName + " :::  " +
                    " USER Details " + response + " -  " + new Date();
	}
	 @SuppressWarnings("unused")
	    private String callUserServiceAndGetData_Fallback(String userName) {
	 
	        System.out.println("USER Service is down!!! fallback route enabled...");
	 
	        return "CIRCUIT BREAKER ENABLED!!! No Response From USER Service at this moment. " +
	                    " Service will be back shortly - " + new Date();
	    }
	 
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
