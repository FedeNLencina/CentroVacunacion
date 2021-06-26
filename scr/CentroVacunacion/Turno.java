package CentroVacunacion;

public class Turno {
	private Fecha fecha;
	private Vacuna vacuna;
	private Persona persona;

	public Turno(Fecha fecha, Vacuna vacuna, Persona persona) {

		this.fecha = fecha;
		this.vacuna = vacuna;
		this.persona = persona;
	}

	public Fecha getFecha() {
		return fecha;
	}

	public Vacuna getVacuna() {
		return vacuna;
	}
	
	public Persona getPersona() {
		return persona;
	}

	
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Turno [fecha=").append(fecha).append(", vacuna=").append(vacuna)
		.append(", persona=").append(persona).append("]");
		
		return sb.toString();
	}

	

}
