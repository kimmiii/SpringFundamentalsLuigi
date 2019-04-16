package be.vdab.Luigi.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.Luigi.domain.Adres;
import be.vdab.Luigi.domain.Persoon;

@Controller
@RequestMapping("/")
class IndexController{
	private final AtomicInteger aantalKeerBekeken = new AtomicInteger();
	private String boodschap() {
		int uur = LocalTime.now().getHour();
		if (uur < 12) {
			return "morgen";
		} else if (uur < 18) {
			return "middag";
		}
		return "avond";
	}

	@GetMapping
	ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index", "boodschap", boodschap());
		modelAndView.addObject("zaakvoerder", new Persoon("Luigi", "Peperone", 7, true, 
				new Adres("Grote markt", "3", 9700, "Oudenaarde"), LocalDate.of(1982, 04, 27)));
		modelAndView.addObject("aantalKeerBekeken",aantalKeerBekeken.incrementAndGet());
		return modelAndView;
	}
	
	
}
