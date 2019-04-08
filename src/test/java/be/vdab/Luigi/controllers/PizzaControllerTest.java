package be.vdab.Luigi.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import be.vdab.Luigi.domain.Pizza;
import be.vdab.Luigi.services.EuroService;
import be.vdab.Luigi.services.PizzaService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PizzaControllerTest {

	private PizzaController controller;
	@Mock
	private EuroService euroService;
	@Mock
	private PizzaService pizzaService;
		
	@Before
	public void before() {
		when(pizzaService.findById(1))
			.thenReturn(Optional.of(new Pizza(1, "Test", BigDecimal.ONE,true)));
		controller = new PizzaController(euroService,pizzaService);
	}
	
	@Test
	public void pizzeGebruiktJuisteThymeleafPagina() {
		assertEquals("pizzas", controller.pizzas().getViewName());
	}
	
	@Test
	public void pizzasGeeftPizzasDoorAanDeThymeleafPagina() {
		assertTrue(controller.pizzas().getModel().get("pizzas") instanceof List);
	}
	
	@Test
	public void pizzaGebruiktJuisteThymeleafPagina() {
		assertEquals("pizza", controller.pizza(12).getViewName()); 
	}
	
	@Test
	public void pizzaGeeftGevondenPizzaDoorAanDeThymeleafPagina() {
		Pizza pizza = (Pizza) controller.pizza(1).getModel().get("pizza");
		assertEquals(1, pizza.getId());
	}
	
	@Test
	public void pizzaGeeftOnbestaandePizzaNietDoorAanDeThymeleafPagina() {
		assertFalse(controller.pizza(-1).getModel().containsKey("pizza"));
	}
}
