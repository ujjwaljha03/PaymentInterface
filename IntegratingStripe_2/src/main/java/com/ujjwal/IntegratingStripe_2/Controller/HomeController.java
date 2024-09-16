package com.ujjwal.IntegratingStripe_2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
public class HomeController {
	
	@RequestMapping("/")
	public String greet() {
		return "home";
	}
}
