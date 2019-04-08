package be.vdab.Luigi.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("kleur")
class KleurController {
	 
	@GetMapping
	ModelAndView toonPagina(@CookieValue(name = "kleur", required = false)String kleur){
		return new ModelAndView("kleur", "kleur", kleur);
	}
	
	@GetMapping("{kleur}")
	ModelAndView kiesKleur(@PathVariable String kleur, 
			HttpServletResponse response) {
//		Cookie cookie = new Cookie("kleur", kleur);
		Cookie cookie = new Cookie("kleur", "");
//		cookie.setMaxAge(31_536_000);
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
		return new ModelAndView("kleur");
	}
}
