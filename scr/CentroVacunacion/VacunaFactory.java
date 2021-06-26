package CentroVacunacion;

public class VacunaFactory {
	public static Vacuna crearVacuna(String nombre) {
		if (nombre.equals("Sinopharm")){
			return new Sinopharm(nombre);
		}
		if (nombre.equals("AstraZeneca")){
			return new Astrazeneca(nombre);
		}
		if (nombre.equals("Sputnik")){
			return new Sputnik(nombre);
		}
		if (nombre.equals("Pfizer")){
			return new Pfizer(nombre);
		}
		
		else
			return new Moderna(nombre);
		}
	
	
}
