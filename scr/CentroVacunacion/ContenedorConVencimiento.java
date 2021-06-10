package CentroVacunacion;

import java.util.ArrayList;
import java.util.HashMap;

public class ContenedorConVencimiento extends Contenedor<VacunaConVencimiento>{
	private ArrayList<VacunaConVencimiento> stockVacunasVencidas;
	private HashMap<String, Integer> listaVencidas;
	
	public ContenedorConVencimiento() {
		super();
		stockVacunasVencidas= new ArrayList <VacunaConVencimiento>();
		listaVencidas= new HashMap<>();
	}
	
	public void agregarVacunaVencina(VacunaConVencimiento vacuna) {
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

	public void setStockVacunasVencidas(ArrayList<VacunaConVencimiento> stockVacunasVencidas) {
		this.stockVacunasVencidas = stockVacunasVencidas;
	}

	

}
