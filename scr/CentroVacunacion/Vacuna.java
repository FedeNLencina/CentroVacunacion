package CentroVacunacion;

public abstract class Vacuna {
	private String marca;
	private int gradoConservacion;
	private boolean reservada;
	private String tipo;

	public Vacuna() {
		super();
	}

	public Vacuna(String nombreVacuna) {
		marca = nombreVacuna;
		this.gradoConservacion = this.asignarConservacion();
		this.reservada = false;
		this.tipo=asignarTipo();
	}

	abstract String asignarTipo();
	

	public int asignarConservacion() {
		return 0;
	}
	

	public String getMarca() {
		return marca;
	}

	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo= tipo;
	}
	public int getGradoConservacion() {
		return gradoConservacion;
	}
	

	public boolean isReservada() {
		return reservada;
	}

	public void setReservada(boolean reservada) {
		this.reservada = reservada;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Vacuna [marca=")
		.append(marca)
		.append(", gradoConservacion=")
		.append(gradoConservacion)
		.append("]");
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vacuna other = (Vacuna) obj;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		return true;
	}

}
