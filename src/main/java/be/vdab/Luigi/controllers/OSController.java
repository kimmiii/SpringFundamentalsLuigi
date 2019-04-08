package be.vdab.Luigi.controllers;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("os")
class OSController {
	
	private static final String[] OSS = {"Windows", "macintosh", "linux"};
	
	@GetMapping
	ModelAndView os(@RequestHeader("User-Agent") String userAgent) {
		ModelAndView modelAndView = new ModelAndView("os");
		Arrays.stream(OSS).filter(os -> userAgent.toLowerCase().contains(os))
		.findFirst().ifPresent(os -> modelAndView.addObject("os",os));
		return modelAndView;
	}
}
