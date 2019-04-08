package be.vdab.Luigi.controllers;


import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.Luigi.forms.VanTotPrijsForm;
import be.vdab.Luigi.services.EuroService;
import be.vdab.Luigi.services.PizzaService;

@Controller
@RequestMapping("pizzas")
class PizzaController {
	
	private final EuroService euroService;
	private final PizzaService pizzaService;	

	public PizzaController(EuroService euroService, PizzaService pizzaService) {
		this.euroService = euroService;
		this.pizzaService = pizzaService;
	}

	@GetMapping
	ModelAndView pizzas() {
		return new ModelAndView("pizzas", "pizzas", pizzaService.findAll());
	}

	@GetMapping("{id}")
	ModelAndView pizza(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView("pizza");		
		pizzaService.findById(id).ifPresent(pizza -> {
			modelAndView.addObject(pizza);
			modelAndView.addObject("inDollar", euroService.naarDollar(pizza.getPrijs()));
		});
		return modelAndView;
	}
			
	@GetMapping("prijzen")
	ModelAndView prijzen()
	{
		return new ModelAndView("prijzen", "prijzen", pizzaService.findUniekePrijzen());
	}
	
	@GetMapping("prijzen/{prijs}")
	ModelAndView pizzasMetEenPrijs(@PathVariable BigDecimal prijs) {
		return new ModelAndView("prijzen", "pizzas", pizzaService.findByPrijs(prijs))
				.addObject("prijzen", pizzaService.findUniekePrijzen());
	}
	
	@GetMapping("vantotprijs/form")
	ModelAndView vanTotPrijsForm() {
		return new ModelAndView("vantotprijs")
				.addObject(new VanTotPrijsForm(null, null));
	}
	
	@GetMapping("vantotprijs")
	ModelAndView vanTotPrijs(VanTotPrijsForm form, Errors errors) {
		ModelAndView modelAndView = new ModelAndView("vantotprijs");
		if (errors.hasErrors()) {
			return modelAndView;
		}
		return modelAndView.addObject("pizzas", 
				pizzaService.findByPrijsBetween(form.getVan(), form.getTot()));
	}

}
