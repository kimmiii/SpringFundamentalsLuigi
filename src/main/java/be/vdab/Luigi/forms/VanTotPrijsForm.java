package be.vdab.Luigi.forms;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class VanTotPrijsForm {
	@NotNull
	@PositiveOrZero
	private final BigDecimal van;
	@NotNull
	@PositiveOrZero
	private final BigDecimal tot;
	
	public VanTotPrijsForm(BigDecimal van, BigDecimal tot) {
		this.van = van;
		this.tot = tot;
	}

	public BigDecimal getVan() {
		return van;
	}

	public BigDecimal getTot() {
		return tot;
	}

}
