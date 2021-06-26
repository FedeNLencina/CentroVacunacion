package CentroVacunacion;

public class Moderna extends VacunaConVencimiento {

	public Moderna(String nombre) {
		super(nombre);
	}
	
	public Moderna(String nombreVacuna, Fecha fechaIngreso) {
		super(nombreVacuna, fechaIngreso);
		
	}
	@Override
	public int asignarConservacion() {
		return -18;
	}
	
	@Override
	public Fecha marcarVencimiento(Fecha fechaIngreso) {
		Fecha caducidad = new Fecha(fechaIngreso.dia(), fechaIngreso.mes(), fechaIngreso.anio());
	    for (int i = 0; i < 60; i++) {
		    	caducidad.avanzarUnDia();
			}
	
		return caducidad;
	}

	@Override
	String asignarTipo() {
		
		return "Menor";
	}
}
