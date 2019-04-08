package be.vdab.Luigi.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.Luigi.domain.Pizza;
import be.vdab.Luigi.repositories.PizzaRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class DefaultPizzaService implements PizzaService {
	private final PizzaRepository pizzaRepository;
	
	public DefaultPizzaService(PizzaRepository pizzaRepository) {
		this.pizzaRepository = pizzaRepository;
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public long create(Pizza pizza) {
		return pizzaRepository.create(pizza);
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void update(Pizza pizza) {
		pizzaRepository.update(pizza);
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void delete(long id) {
		pizzaRepository.delete(id);
	}

	@Override
	public List<Pizza> findAll() {
		return pizzaRepository.findAll();
	}

	@Override
	public Optional<Pizza> findById(long id) {
		return pizzaRepository.findById(id);
	}

	@Override
	public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
		return pizzaRepository.findByPrijsBetween(van, tot);
	}

	@Override
	public long findAantalPizzas() {
		return pizzaRepository.findAantalPizzas();
	}

	@Override
	public List<BigDecimal> findUniekePrijzen() {
		return pizzaRepository.findUniekePrijzen();
	}

	@Override
	public List<Pizza> findByPrijs(BigDecimal prijs) {
		return pizzaRepository.findByPrijs(prijs);
	}

}
