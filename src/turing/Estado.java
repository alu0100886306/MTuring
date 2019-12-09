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

	public void transita(String string, int index, String traza, boolean see) {
		
		if (see) System.out.println(string + " i:" + index + " q:" + id);
		
		boolean no_trans = true;
		for (Transicion t : trans) {
			if (t.accept(string.charAt(index))) {
				no_trans = false;
				t.getNewEstado().transita(
						t.newString(new String(string),index), 
						t.newIndex(index), 
						traza + string.charAt(index) + "," + t.val_out + "," + t.move + "->" + t.getNewEstado().getId() + " ",
						see);
			}
		}		
		if (no_trans) {
			if (see) System.out.println(traza);
			if (accept) {
				System.out.println("Cadena aceptada");
			}
			else {
				System.out.println("Cadena Rechazada");
			}
		}
	}
}
