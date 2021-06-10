package CentroVacunacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CentroVacunacion implements metodosCentroVacunacion{
	private String nombreCentro;
	private int capacidadVacunacionDiaria;
	private Contenedor<Vacuna> contenedor3;
	private ContenedorConVencimiento contenedor18;
	private ArrayList<Turno> turnos;
	private ArrayList<Persona> listaPersonasEspera;
	
	
	
	public CentroVacunacion(String nombreCentro, int capacidadVacunacionDiaria) {
		this.nombreCentro = nombreCentro;
		turnos = new ArrayList<Turno>();
		contenedor3 = new Contenedor<Vacuna>();
		contenedor18 = new ContenedorConVencimiento();
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
			if (nombreVacuna.equals("Sinopharm") || nombreVacuna.equals("Sputnik") || nombreVacuna.equals("AstraZeneca")) {
				contenedor3.agregarVacunas(new Vacuna(nombreVacuna));
			} else {			
				contenedor18.agregarVacunas(new VacunaConVencimiento(nombreVacuna, fechaIngreso));
			}
		}
	}

	public void generarTurnos(Fecha fechaInicial) {
		Fecha actual = Fecha.hoy();
		if(fechaInicial.compareTo(actual)<0) {
			throw new RuntimeException("La fecha ingresada es una fecha no valida");
		}
		else {
			Iterator<Turno> it = turnos.iterator();
			while(it.hasNext()) {
				Turno turnoActual = it.next();
				if(turnoActual.getFecha().compareTo(fechaInicial)<0) {
					Vacuna vacunaAsignada = turnoActual.getVacuna();
					vacunaAsignada.setReservada(false);
					it.remove();
				}	
			}
			Iterator<VacunaConVencimiento> it2 = contenedor18.getStockVacunas().iterator();
			while(it2.hasNext()) {
				VacunaConVencimiento vacunaActual= it2.next();
				if(vacunaActual.estaVencida(fechaInicial)) {
					contenedor18.agregarVacunaVencina(vacunaActual);
					it2.remove();
				}
			}
		
			//Una vez verificado los dos caso anteriores, ordeno la lista con el metodo sort el cual utiliza el compareTo para
			//ordenarlos en funcion de la prioridad y luego se les asigna el turno en funcion de la capacidad de vacunacion.
			Collections.sort(listaDeEspera());
			Iterator <Persona> it3 = listaPersonasEspera.iterator();
			while(it3.hasNext()) {
				while(this.listaPersonasEspera.size()>0) {
					if(this.turnos.size() >0 && (this.turnos.size() % capacidadVacunacionDiaria==0)) {
						Fecha siguiente = new Fecha(fechaInicial.dia(), fechaInicial.mes(), fechaInicial.anio());
						siguiente.avanzarUnDia();
						fechaInicial=siguiente;
			
					}
				
					Persona personaActual = it3.next();
					if(personaActual.getEdad()>=60) {
						if(vacunasDisponibles("Pfizer")>0) {
							Vacuna asignada = contenedor18.dameVacunaNoReservada("Pfizer");
							if(asignada!=null) {
								turnos.add(new Turno(fechaInicial, asignada, personaActual));
								asignada.setReservada(true);
								it3.remove();
							}
							else
								throw new RuntimeException("No se encuentran vacunas disponibles");
						}
						else if(vacunasDisponibles("Sputnik")>0) {
							Vacuna asignada = contenedor3.dameVacunaNoReservada("Sputnik");
							if(asignada!=null) {
								turnos.add(new Turno(fechaInicial, asignada, personaActual));
								asignada.setReservada(true);
								it3.remove();
							}
							else
								throw new RuntimeException("No se encuentran vacunas disponibles");
						}	
					}
					else {
						if(vacunasDisponibles("Sinopharm")>0) {
							Vacuna asignada = contenedor3.dameVacunaNoReservada("Sinopharm");
							if(asignada!=null) {
								turnos.add(new Turno(fechaInicial, asignada, personaActual));
								asignada.setReservada(true);
								it3.remove();
							}
							else
								throw new RuntimeException("No se encuentran vacunas disponibles");
						}
						if(vacunasDisponibles("Moderna")>0) {
							Vacuna asignada = contenedor18.dameVacunaNoReservada("Moderna");
							if(asignada!=null) {
								turnos.add(new Turno(fechaInicial, asignada, personaActual));
								asignada.setReservada(true);
								it3.remove();
							}
							else
								throw new RuntimeException("No se encuentran vacunas disponibles");
						}
						if(vacunasDisponibles("AstraZeneca")>0) {
							Vacuna asignada = contenedor3.dameVacunaNoReservada("AstraZeneca");
							if(asignada!=null) {
								turnos.add(new Turno(fechaInicial, asignada, personaActual));
								asignada.setReservada(true);
								it3.remove();
							}
							else
								throw new RuntimeException("No se encuentran vacunas disponibles");
						}
			
					}
				}
			}
		}
	}

	public void inscribirPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		listaPersonasEspera.add( new Persona(dni, nacimiento, tienePadecimientos, esEmpleadoSalud));

	}

	
	public int vacunasDisponibles(String nombreVacuna) {
		if (contenedor3.existeMarcaVacuna(nombreVacuna)) {
			return contenedor3.consultarCantidad(nombreVacuna);
		} else {
			return contenedor18.consultarCantidad(nombreVacuna);
		}		
	}

	
	public int vacunasDisponibles() {
		int cantidad= 0;
		for( Vacuna vacuna : contenedor3.getStockVacunas()) {
			if (!vacuna.isReservada()) {
				cantidad = cantidad + 1;
			}
		}
		for( Vacuna vacuna : contenedor18.getStockVacunas()) {
			if (!vacuna.isReservada()) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
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
		
		return this.contenedor18.getListaVencidas();
	}

	private void quitarDelContenedor(Vacuna vacuna) {
		if(vacuna.getMarca().equals("Pfizer") || vacuna.getMarca().equals("Moderna")) {
			contenedor18.quitarVacuna(vacuna.getMarca());
		}
		else
			contenedor3.quitarVacuna(vacuna.getMarca());	
	}
	
	@Override
	public String toString() {
		return "CentroVacunacion [nombreCentro=" + nombreCentro + ", capacidadVacunacionDiaria="
				+ capacidadVacunacionDiaria + "]";
	}

}
