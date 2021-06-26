package CentroVacunacion;

public class Sputnik extends Vacuna{
	
	
	public Sputnik() {
		 super();
	}
	public Sputnik(String nombreVacuna) {
		super(nombreVacuna);
		
	}
	
	@Override
	public int asignarConservacion() {
		return 3;
	}
	@Override
	String asignarTipo() {
		
		return "Mayor";
	}
}
