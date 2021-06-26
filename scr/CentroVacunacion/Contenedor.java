package CentroVacunacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public  class Contenedor {
     //utilizar listade vacunas 
	private ArrayList<Vacuna> stockVacunas;
	private ArrayList<VacunaConVencimiento> stockVacunasVencidas;
	private HashMap<String, Integer> listaVencidas;
	
	
	public Contenedor() {
		this.stockVacunas = new ArrayList<Vacuna>();
		this.stockVacunasVencidas = new ArrayList<VacunaConVencimiento>();
		this.listaVencidas= new HashMap<String, Integer>();
		
	}

	public void agregarVacunas(Vacuna vacunas) {
		this.stockVacunas.add(vacunas);
	}

	public int consultarCantidad(String nombreVacuna) {
		int cantidad=0;
		for(Vacuna vacuna : this.stockVacunas) {
			if(vacuna.getMarca().equals(nombreVacuna) && !vacuna.isReservada())
				cantidad= cantidad +1;
		}
		return cantidad;
		
	}
	
	
	public int consultarCantidad() {
		int cantidad= 0;
		for( Vacuna vacuna : stockVacunas) {
			if (!vacuna.isReservada()) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}
	
	public Vacuna dameVacunaNoReservada(Persona persona) {
		Iterator<Vacuna> it = stockVacunas.iterator();
		while(it.hasNext()) {
			Vacuna vacuna = it.next();
			if(vacuna.getTipo().equals(persona.getTipo()) && !vacuna.isReservada())
				return vacuna;
		}
		return null;
	}
	
	public boolean existeMarcaVacuna(String nombre) {
		Iterator<Vacuna> it = stockVacunas.iterator();
		while(it.hasNext()) {
			Vacuna vacuna = it.next();
			if(vacuna.getMarca().equals(nombre))
				return true;
			}
		return false;
	}
	
	public int stockTotalVacunas() {
		return stockVacunas.size();
	}
	
	
	public void quitarVacuna(String tipo) {
		boolean fueRemovida =false;
		Iterator<Vacuna> it = stockVacunas.iterator();
		while(it.hasNext()) {
			Vacuna vacuna = it.next();
			if(vacuna.getTipo().equals(tipo) && !fueRemovida) {
				it.remove();
				fueRemovida=true;
			}
		}
	}
	
	public void agregarVacunaVencida(VacunaConVencimiento vacuna) {
		stockVacunasVencidas.add(vacuna);
		if(!listaVencidas.containsKey(vacuna.getMarca()))
			listaVencidas.put(vacuna.getMarca(), 1);
		else
			listaVencidas.put(vacuna.getMarca(), listaVencidas.get(vacuna.getMarca())+1);
		}
	
	public int cantVacunasVencidas() {
		return stockVacunasVencidas.size();
	}

	public HashMap<String, Integer> getListaVencidas() {
		return listaVencidas;
	}

	public ArrayList<VacunaConVencimiento> getStockVacunasVencidas() {
		return stockVacunasVencidas;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Contenedor [stockVacunas=")
		.append(stockVacunas)
		.append("]");
		
		return sb.toString();
	}


	public ArrayList<Vacuna> getStockVacunas() {
		return stockVacunas;
	}





	
	
}
