package CentroVacunacion;

public class Sinopharm extends Vacuna {

	public Sinopharm() {
		 super();
	}

	public Sinopharm(String nombreVacuna) {
		super(nombreVacuna);
		
	}
	
	@Override
	public int asignarConservacion() {
		return 3;
	}

	@Override
	String asignarTipo() {
		
		return "Menor";
	}
}
