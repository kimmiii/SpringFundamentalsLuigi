package be.vdab.Luigi.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.Luigi.domain.Pizza;
import be.vdab.Luigi.services.PizzaService;
import be.vdab.Luigi.sessions.Mandje;

@Controller
@RequestMapping("mandje")
class MandjeController {
	private final Mandje mandje;
	private final PizzaService pizzaService;
	
	public MandjeController(Mandje mandje, PizzaService pizzaService) {
		this.mandje = mandje;
		this.pizzaService = pizzaService;
	}
	
	@GetMapping
	ModelAndView toonMandje() {
		List<Pizza> allePizzas = pizzaService.findAll();
		ModelAndView modelAndView = new ModelAndView("mandje")
				.addObject("allePizzas", allePizzas);
		if (mandje.isGevuld()) {
			modelAndView.addObject("pizzasInMandje", 
					allePizzas.stream().filter(pizza -> mandje.bevat(pizza.getId()))
					.collect(Collectors.toList()));
		}
		return modelAndView;
	}
	
	@PostMapping
	String voegToe(long id) {
		mandje.voegToe(id);
		return "redirect:/mandje";
	}
}
