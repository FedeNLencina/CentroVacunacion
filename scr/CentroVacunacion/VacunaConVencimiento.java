package CentroVacunacion;

public class VacunaConVencimiento extends Vacuna {
	private Fecha caducidad;
	private boolean vencida;
	

	public VacunaConVencimiento(String nombreVacuna,  Fecha fechaIngreso) {
		super(nombreVacuna);
		this.caducidad = (this.marcarVencimiento(fechaIngreso, nombreVacuna));
	    this.vencida = false;
	}
	
	/* 
	 * esta metodo determina el vencimiento de la vacuna desde su fecha de ingreso
	 */
	public Fecha marcarVencimiento(Fecha fechaIngreso ,String nombreVacuna) {
		Fecha caducidad = new Fecha(fechaIngreso.dia(), fechaIngreso.mes(), fechaIngreso.anio());
		if(nombreVacuna.equals("Pfizer")) {
			for (int i = 0; i < 30; i++) 
				caducidad.avanzarUnDia();
		}else{
		    for (int i = 0; i < 60; i++) {
		    	caducidad.avanzarUnDia();
			}
	}
		return caducidad;
	}

	public Fecha getCaducidad() {
		return caducidad;
	}

	public boolean isVencida() {
		return vencida;
	}

	public void setVencida(boolean vencida) {
		this.vencida = vencida;
	}

	public boolean estaVencida(Fecha actual) {
		if(caducidad.compareTo(actual)<0) {
			setVencida(true);
			return true;
		}
		return false;
	}
	
}
