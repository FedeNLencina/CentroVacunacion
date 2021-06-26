package CentroVacunacion;

public class Pfizer extends VacunaConVencimiento{

	public Pfizer(String nombre) {
		super(nombre);
	}
	
	public Pfizer(String nombreVacuna,  Fecha fechaIngreso) {
		super(nombreVacuna,  fechaIngreso);
		
		
	}
	
	@Override
	public int asignarConservacion() {
		return -18;
	}
	@Override
	public Fecha marcarVencimiento(Fecha fechaIngreso) {
		Fecha caducidad = new Fecha(fechaIngreso.dia(), fechaIngreso.mes(), fechaIngreso.anio());
		for (int i = 0; i < 30; i++) { 
				caducidad.avanzarUnDia();
		}
		return caducidad;
	}

	@Override
	String asignarTipo() {
		
		return "Mayor";
	}


}
