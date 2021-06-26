package CentroVacunacion;

public class Astrazeneca extends Vacuna{
	
	public Astrazeneca() {
		 super();
	}
	
	public Astrazeneca(String nombreVacuna) {
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
