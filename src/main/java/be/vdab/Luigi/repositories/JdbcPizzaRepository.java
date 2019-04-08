package be.vdab.Luigi.repositories;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import be.vdab.Luigi.domain.Pizza;
import be.vdab.Luigi.exceptions.PizzaNietGevondenException;

@Repository
public class JdbcPizzaRepository implements PizzaRepository{
	private final JdbcTemplate template;
	private static final String SELECT_AANTAL_PIZZAS = 
			"select count(*) from pizzas";
	private static final String DELETE_PIZZA = 
			"delete from pizzas where id=?";
	private static final String UPDATE_PIZZA = 
			"update pizzas set naam=?, prijs=?, pikant=? where id=?";
	private final SimpleJdbcInsert insert;
	private final RowMapper<Pizza> pizzaMapper = 
			(result, rowNum)-> new Pizza(result.getLong("id"), result.getString("naam"),
					result.getBigDecimal("prijs"), result.getBoolean("pikant"));
	private static final String SELECT_ALL = 
			"select id, naam, prijs, pikant from pizzas order by id";
	private static final String SELECT_BY_PRIJS_BETWEEN = 
			"select id, naam, prijs, pikant from pizzas"
			+ " where prijs between ? and ? order by prijs";
	private static final String SELECT_BY_ID = 
			"select id, naam, prijs, pikant from pizzas where id =?";
	private final RowMapper<BigDecimal> prijsMapper = (result, rowNum) -> result.getBigDecimal("prijs");
	private static final String SELECT_UNIEKE_PRIJZEN = 
			"select distinct prijs from pizzas order by prijs";
	private static final String SELECT_BY_PRIJS = 
			"select id, naam, prijs, pikant from pizzas where prijs=? order by naam";
	private static final String SELECT_BY_IDS =
			"select id, naam, prijs, pikant from pizzas where id in (";
	
	JdbcPizzaRepository(JdbcTemplate template){
		this.template = template;
		this.insert = new SimpleJdbcInsert(template);
		insert.withTableName("pizzas");
		insert.usingGeneratedKeyColumns("id");
	}

	@Override
	public long create(Pizza pizza) {
		Map<String, Object> kolomWaarden = new HashMap<>();
		kolomWaarden.put("naam", pizza.getNaam());
		kolomWaarden.put("prijs", pizza.getPrijs());
		kolomWaarden.putIfAbsent("pikant", pizza.isPikant());
		Number id = insert.executeAndReturnKey(kolomWaarden) ;
		return id.longValue();
	}

	@Override
	public void update(Pizza pizza) {
		if (template.update(UPDATE_PIZZA, pizza.getNaam(), 
				pizza.getPrijs(), pizza.isPikant(), pizza.getId()) == 0) {
			throw new PizzaNietGevondenException();
		}		
	}

	@Override
	public void delete(long id) {
		template.update(DELETE_PIZZA, id);		
	}

	@Override
	public List<Pizza> findAll() {
		return template.query(SELECT_ALL, pizzaMapper);
	}

	@Override
	public Optional<Pizza> findById(long id) {
		try {
			return Optional.of(template.queryForObject(SELECT_BY_ID, pizzaMapper, id));
		} catch (IncorrectResultSizeDataAccessException ex) {
			return Optional.empty();
		}
	}

	@Override
	public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
		return template.query(SELECT_BY_PRIJS_BETWEEN, pizzaMapper, van, tot);
	}

	@Override
	public long findAantalPizzas() {
		return template.queryForObject(SELECT_AANTAL_PIZZAS, long.class);
	}

	@Override
	public List<BigDecimal> findUniekePrijzen() {
		return template.query(SELECT_UNIEKE_PRIJZEN, prijsMapper);
	}

	@Override
	public List<Pizza> findByPrijs(BigDecimal prijs) {
		return template.query(SELECT_BY_PRIJS, pizzaMapper, prijs);
	}

	@Override
	public List<Pizza> findByIds(Set<Long> ids) {
		if (ids.isEmpty()) { 
			return Collections.emptyList();
		}
		StringBuilder builder = new StringBuilder(SELECT_BY_IDS);
		ids.forEach(id -> builder.append("?,")); 
		builder.setCharAt(builder.length() - 1, ')'); 
		builder.append(" order by id");
		return template.query(builder.toString(), ids.toArray(), pizzaMapper);
	}

}
