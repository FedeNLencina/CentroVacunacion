package CentroVacunacion;

public abstract class VacunaConVencimiento extends Vacuna {
	private Fecha caducidad;
	private boolean vencida;
	
	public VacunaConVencimiento() {
		super();
	}
	
	public VacunaConVencimiento(String nombre){
		super(nombre);
	}

	public VacunaConVencimiento(String nombreVacuna,  Fecha fechaIngreso) {
		super(nombreVacuna);
		this.caducidad = (this.marcarVencimiento(fechaIngreso));
	    this.vencida = false;
	}
	
	/* 
	 * esta metodo determina el vencimiento de la vacuna desde su fecha de ingreso
	 */
	public abstract  Fecha marcarVencimiento(Fecha fechaIngreso);
	

	public void setFecha(Fecha fecha) {
		this.caducidad= fecha;
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
	
	
	
	public void setCaducidad(Fecha caducidad) {
		this.caducidad = caducidad;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString())
		  .append(", Caducidad = ")
		  .append(caducidad)
		  .append(", vencimiento = ")
		  .append(vencida);
		return sb.toString();
	}
}
