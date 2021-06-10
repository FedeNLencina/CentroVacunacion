package CentroVacunacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public  class Contenedor <T extends Vacuna> {
     //utilizar listade vacunas 
	private ArrayList<T>stockVacunas;
	
	
	
	public Contenedor() {
		this.stockVacunas = new ArrayList<T>();
		
	}

	public void agregarVacunas(T vacunas) {
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
	
	public T dameVacunaNoReservada(String nombre) {
		Iterator<T> it = stockVacunas.iterator();
		while(it.hasNext()) {
			T vacuna = it.next();
			if(vacuna.getMarca().equals(nombre) && !vacuna.isReservada())
				return vacuna;
		}
		return null;
	}
	
	public boolean existeMarcaVacuna(String nombre) {
		Iterator<T> it = stockVacunas.iterator();
		while(it.hasNext()) {
			T vacuna = it.next();
			if(vacuna.getMarca().equals(nombre))
				return true;
			}
		return false;
	}
	
	public int stockTotalVacunas() {
		return stockVacunas.size();
	}
	
	
	public void quitarVacuna(String nombre) {
		boolean fueRemovida =false;
		Iterator<T> it = stockVacunas.iterator();
		while(it.hasNext()) {
			T vacuna = it.next();
			if(vacuna.getMarca().equals(nombre) && !fueRemovida) {
				it.remove();
				fueRemovida=true;
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Contenedor [stockVacunas=")
		.append(stockVacunas)
		.append("]");
		
		return sb.toString();
	}


	public ArrayList<T> getStockVacunas() {
		return stockVacunas;
	}

	public void setStockVacunas(ArrayList<T> stockVacunas) {
		this.stockVacunas = stockVacunas;
	}



	
	
}
