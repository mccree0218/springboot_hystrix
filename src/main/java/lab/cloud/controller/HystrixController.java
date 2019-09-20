package lab.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lab.cloud.service.HystrixServiceDelegate;

@RestController
public class HystrixController {

	@Autowired
	HystrixServiceDelegate hystrixServiceDelegate;
	
	@GetMapping("/getUserDetails/{userName}")
	public String getUserInfo(@PathVariable("userName") String userName) {
		return hystrixServiceDelegate.callUserServiceAndGetData(userName);
	}
}
