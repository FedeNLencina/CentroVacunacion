package CentroVacunacion;

public class Persona implements Comparable<Persona>{
	private int dni ;
    private boolean esPersonaRiesgo;
    private boolean esPersonalSalud;
    private int edad;
    private Fecha fechaNacimiento;
    private boolean fueVacunado;
    private int prioridad;
    private Fecha hoy;
    
    
    public Persona(int dni, Fecha nacimiento , boolean esPersonaRiesgo, boolean esPersonalSalud) {
    	this.dni = dni;
    	this.esPersonalSalud = esPersonalSalud;
    	this.esPersonaRiesgo = esPersonaRiesgo;
    	this.fechaNacimiento = nacimiento;
    	this.hoy = Fecha.hoy();
    	this.edad = this.calcularEdad(this.hoy, nacimiento);
        this.fueVacunado = false;
        this.asignarPrioridad(dni, nacimiento, esPersonaRiesgo, esPersonalSalud);
    	  	   	
    }
    
    public int calcularEdad(Fecha hoy, Fecha nacimiento){
    	return Fecha.diferenciaAnios(hoy, nacimiento);
    }
    
    public int getEdad() {
		return edad;
	}

	public void asignarPrioridad(int dni, Fecha nacimiento , boolean esPersonaRiesgo, boolean esPersonalSalud) {
    	//nivel 1: persona de salud
    	//nivel 2: mayor de 60 años
    	//nivel 3: esRiesgo
    	//nivel 4:  publico general
    	if(esPersonalSalud) 
    		this.prioridad = 1;
    	
    	else if(Fecha.diferenciaAnios(this.hoy, nacimiento)> 60) 
    			this.prioridad = 2;
    		
    	else if(esPersonaRiesgo)
    		this.prioridad = 3;
    		
    	else this.prioridad = 4;
    }
    
    
    public int compareTo(Persona otra) {
    	return otra.prioridad - this.prioridad;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + prioridad;
		return result;
	}

	public boolean isFueVacunado() {
		return fueVacunado;
	}

	public void setFueVacunado(boolean fueVacunado) {
		this.fueVacunado = fueVacunado;
	}
	

	public int getDni() {
		return dni;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (prioridad != other.prioridad)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Persona [dni=").append(dni).append(", esPersonaRiesgo=").append(esPersonaRiesgo)
			.append(", esPersonalSalud=").append(esPersonalSalud).append(", edad=" ).append(edad)
			.append(", fechaNacimiento=").append(fechaNacimiento).append(", fueVacunado=" )
			.append(fueVacunado).append(", prioridad=" ).append(prioridad).append(", hoy=")
			.append(hoy).append("]");
		
		return sb.toString();
	}
	
	
}
    
    
    
	


