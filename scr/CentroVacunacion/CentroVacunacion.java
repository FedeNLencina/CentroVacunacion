package CentroVacunacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CentroVacunacion {
	private String nombreCentro;
	private int capacidadVacunacionDiaria;
	private Contenedor contenedorVacunas;
	private ArrayList<Turno> turnos;
	private ArrayList<Persona> listaPersonasEspera;
	
	
	
	public CentroVacunacion(String nombreCentro, int capacidadVacunacionDiaria) {
		this.nombreCentro = nombreCentro;
		turnos = new ArrayList<Turno>();
		contenedorVacunas = new Contenedor();
		listaPersonasEspera=new ArrayList<Persona>();
		this.capacidadVacunacionDiaria = capacidadVacunacionDiaria;
	}

	public void ingresarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso) {
		if (cantidad < 1) {
			throw new VacunaException("no ingresar cantidad negativa");
		}
		if (VacunasNombre.valueOf(nombreVacuna) == null) {
			throw new VacunaException("no existe la marca de vacuna ingresada");
		}
		for(int i=0; i<cantidad; i++) {
			Vacuna vacuna= VacunaFactory.crearVacuna(nombreVacuna);
			if (vacuna instanceof VacunaConVencimiento) {
				VacunaConVencimiento vacunaAVencer = (VacunaConVencimiento)vacuna;
				vacunaAVencer.setCaducidad(vacunaAVencer.marcarVencimiento(fechaIngreso));
				contenedorVacunas.agregarVacunas(vacunaAVencer);
			}
			else {
				contenedorVacunas.agregarVacunas(vacuna);
			}
			
		}
	}

	public void generarTurnos(Fecha fechaInicial) {
		Fecha actual = Fecha.hoy();
		if(fechaInicial.compareTo(actual)<0) {
			throw new RuntimeException("La fecha ingresada es una fecha no valida");
		}
		else {
			//caso turno vencido.
			Iterator<Turno> it = turnos.iterator();
			while(it.hasNext()) {
				Turno turnoActual = it.next();
				if(turnoActual.getFecha().compareTo(fechaInicial)<0) {
					Vacuna vacunaAsignada = turnoActual.getVacuna();
					vacunaAsignada.setReservada(false);
					it.remove();
				}	
			}
			//caso de vacunas vencidas
			Iterator<Vacuna> it2 = contenedorVacunas.getStockVacunas().iterator();
			while(it2.hasNext()) {
				Vacuna vacunaActual= it2.next();
				if(vacunaActual instanceof VacunaConVencimiento) {
					if(((VacunaConVencimiento)vacunaActual).estaVencida(fechaInicial)) {
						contenedorVacunas.agregarVacunaVencida((VacunaConVencimiento)vacunaActual);
						it2.remove();
					}
				}
			}
		
			//Una vez verificado los dos caso anteriores, ordeno la lista con el metodo sort el cual utiliza el compareTo para
			//ordenarlos en funcion de la prioridad y luego se les asigna el turno en funcion de la capacidad de vacunacion.
			Collections.sort(listaPersonasEspera);
			Iterator <Persona> it3 = listaPersonasEspera.iterator();
			while(it3.hasNext()) {
				if(this.listaPersonasEspera.size()>0) {
					if(this.turnos.size()>0 && (this.turnos.size() % capacidadVacunacionDiaria==0)) {
						Fecha siguiente = new Fecha(fechaInicial.dia(), fechaInicial.mes(), fechaInicial.anio());
						siguiente.avanzarUnDia();
						fechaInicial=siguiente;
			
					}
					Persona personaActual = it3.next();
					Vacuna vacunaAsignada=contenedorVacunas.dameVacunaNoReservada(personaActual);
					if(vacunaAsignada!=null) {
						turnos.add(new Turno(fechaInicial, vacunaAsignada, personaActual));
						vacunaAsignada.setReservada(true);
						it3.remove();
					}

				}
			}
		}
	}

	public void inscribirPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		Persona persona = new Persona(dni, nacimiento, tienePadecimientos, esEmpleadoSalud);
		if (persona.getEdad()<18) {
			throw new RuntimeException("La persona no puede ser inscripta por ser menor de edad.");
		}
		else if (persona.isFueVacunado()) {
			throw new RuntimeException("La persona fue vacunada.");
		}
		else if (listaPersonasEspera.contains(persona)) {
			throw new RuntimeException("La persona se encuentra inscripta");
		}
		else 
			listaPersonasEspera.add(persona);

	}

	
	public int vacunasDisponibles(String nombreVacuna) {
			return contenedorVacunas.consultarCantidad(nombreVacuna);
	}

	
	public int vacunasDisponibles() {
		return contenedorVacunas.consultarCantidad();
	}

	
	public List<Integer> listaDeEspera() {
		ArrayList<Integer> listaDNI= new ArrayList<Integer>();
		for(Persona persona: this.listaPersonasEspera)
			if(!persona.isFueVacunado())
				listaDNI.add(persona.getDni());
		return listaDNI;
	}

	
	public Map<Integer, String> reporteVacunacion() {
		HashMap<Integer, String> reporte = new HashMap<>();
		for (Turno turno : turnos) {
			Persona persona = turno.getPersona();
			if(persona.isFueVacunado() && !reporte.containsKey(persona.getDni()))
				reporte.put(persona.getDni(), turno.getVacuna().getMarca());
		}
		return reporte;
	}

	
	public List<Integer> turnosConFecha(Fecha fecha) {
		ArrayList<Integer> listaDNI= new ArrayList<Integer>();
		for(Turno turno: this.turnos)
			if(turno.getFecha().equals(fecha))
				listaDNI.add(turno.getPersona().getDni());
		return listaDNI;
	}

	public void vacunarInscripto(int dni, Fecha fechaVacunacion) {
		boolean vacunar = false;
		
		for(Turno turno : turnos) {
			Persona persona = turno.getPersona();
			if ((persona.getDni()==dni) && (turno.getFecha().compareTo(fechaVacunacion))==0) {
				persona.setFueVacunado(true);
				vacunar=true;
				quitarDelContenedor(turno.getVacuna());
				
			}	
			if((persona.getDni()==dni) && (turno.getFecha().compareTo(fechaVacunacion))!=0)
				throw new RuntimeException("La persona no tiene turno para esta fecha");
		}
		if(!vacunar)
			throw new RuntimeException("La persona no tiene un turno asignado");

	}
	
	public Map<String, Integer> reporteVacunasVencidas() {
		
		return this.contenedorVacunas.getListaVencidas();
	}

	private void quitarDelContenedor(Vacuna vacuna) {
		contenedorVacunas.quitarVacuna(vacuna.getTipo());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CentroVacunacion=" )
		  .append(nombreCentro)
		  .append(", capacidadVacunacionDiaria= ")
		  .append(capacidadVacunacionDiaria)
		  .append(", cantidad de vacunas disponibles= ")
		  .append(vacunasDisponibles())
		  .append(", cantidad de personas en espera= ")
		  .append(listaDeEspera().size())
		  .append(", cantidad de turnos generados= ")
		  .append(turnos.size())
		  .append(", cantidad de vacunas aplicadas= ")
		  .append(reporteVacunacion().size())
		  .append("]");
		
		return sb.toString();
	}

}
