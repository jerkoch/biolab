package org.bradylab.plates.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

	@RequestMapping("/home")
	public String home() {
		return "home";
	}
}
