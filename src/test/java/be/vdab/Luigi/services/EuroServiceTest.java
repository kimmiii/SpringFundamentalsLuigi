package be.vdab.Luigi.services;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import be.vdab.Luigi.restclients.KoersClient;

@RunWith(MockitoJUnitRunner.class)
public class EuroServiceTest {
	
	@Mock
	private KoersClient koersClient;
	private EuroService euroService;
	
	@Before
	public void before() {
		when(koersClient.getDollarKoers()).thenReturn(BigDecimal.valueOf(1.5));
		euroService = new DefaultEuroService(new KoersClient[] {koersClient});
	}
	
	@Test
	public void naarDollar() {
		assertEquals(0, BigDecimal.valueOf(3).compareTo(
				euroService.naarDollar(BigDecimal.valueOf(2))));
		verify(koersClient).getDollarKoers();
	}
}
