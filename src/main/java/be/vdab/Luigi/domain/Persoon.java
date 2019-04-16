package be.vdab.Luigi.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Persoon {
	private final String voornaam;
	private final String familienaam;
	private final int aantalKinderen;
	private final boolean gehuwd;
	private Adres adres;
	@DateTimeFormat(style = "S-")
	private final LocalDate geboorte;
	
	public Persoon(String voornaam, 
				   String familienaam, 
				   int aantalKinderen, 
				   boolean gehuwd,
				   Adres adres,
				   LocalDate geboorte) {
		this.voornaam = voornaam;
		this.familienaam = familienaam;
		this.aantalKinderen = aantalKinderen;
		this.gehuwd = gehuwd;
		this.adres = adres;
		this.geboorte = geboorte;
	}


	public String getVoornaam() {
		return voornaam;
	}
	public String getFamilienaam() {
		return familienaam;
	}
	public int getAantalKinderen() {
		return aantalKinderen;
	}
	public boolean isGehuwd() {
		return gehuwd;
	}
	public String getNaam() {
		return voornaam + ' ' + familienaam;
	}
	public Adres getAdres() {
		return adres;
	}
	public LocalDate getGeboorte() {
		return geboorte;
	}
	
}
