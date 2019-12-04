package turing;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Stack;

public class Estado {
	private String id;
	private boolean ini;
	public boolean accept;
	public ArrayList<Transicion> trans;
	
	public Estado(String str) {
		id = str;
		ini = false;
		accept = false;
		trans = new ArrayList<Transicion>();
	}

	public void setIni() {
		ini = true;
	}

	public void setFinal() {
		accept = true;
	}

	public void addTrans(Transicion t) {
		trans.add(t);
	}

	public void show() {
		System.out.println(id + " i:"	+ ini + " f:" +  accept + " ");
		for (Transicion t : trans)
			t.show();		
	}

	public boolean inicial() {
		return ini;
	}

	public String getId() {
		return id;
	}

	public boolean transita(String string, Stack<String> pila, String traza, boolean see) {
		
		if (pila.empty() && string.isEmpty()) {
			if (see) System.out.println("Cadena aceptada");
			return true;
		}
		
		boolean result = false;
		boolean no_trans = true;
		for (Transicion t : trans) {
			if (t.accept('.', pila.peek())) {
				no_trans = false;
				result = result || t.getNewEstado().transita(
						t.newString(new String(string)), 
						t.newPila((Stack)pila.clone()), 
						traza + ".," + pila.peek() + "->" + t.getNewEstado().getId()+ t.newPila((Stack)pila.clone()).toString() + " ",
						see);
			}
			else if (!string.isEmpty() && t.accept(string.charAt(0), pila.peek())) {
				no_trans = false;
				result = result || t.getNewEstado().transita(
						t.newString(new String(string)), 
						t.newPila((Stack)pila.clone()), 
						traza + string.charAt(0) + "->" + t.getNewEstado().getId()+ t.newPila((Stack)pila.clone()).toString() + " ",
						see);
			}
		}
		
		if (string.length()==0 || pila.empty() || no_trans) {
			if (see) System.out.println(traza);
			if (accept) {
				if (see) System.out.println("Cadena aceptada");
				return true;
			}
			else {
				if (see) System.out.println("Cadena Rechazada");
				return result;
			}
		}
		return result;
	}
}
