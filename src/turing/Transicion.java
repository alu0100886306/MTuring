package turing;

import java.util.ListIterator;
import java.util.Stack;

public class Transicion {

	String val_in;
	String cinta_in;
	Estado est_f;
	String cinta_out;
	
	public Transicion(String val_in, String cinta_in, Estado est_f, String[] cinta_out) {
		this.val_in = val_in;
		this.cinta_in = cinta_in;
		this.est_f = est_f;
		this.cinta_out = cinta_out;	
	}
	public Transicion(Transicion t) {
		this.val_in = t.val_in;
		this.cinta_in = t.cinta_in;
		this.est_f = t.est_f;
		this.cinta_out = t.cinta_out;	
	}
	public void show() {
		System.out.print("  " + val_in + " " + cinta_in + " " + est_f.getId() + " [ ");
		for (String str : cinta_out)
			System.out.print(str + " ");
		System.out.print("]\n");
	}

	public boolean accept(char in, String peek) {
		if ((val_in.equals(""+in) || val_in.equals(".")) && (cinta_in.equals(peek) || cinta_in.equals(".")))
			return true;
		return false;
	}

	public Estado getNewEstado() {
		return est_f;
	}

	public Stack<String> newPila(Stack<String> pila) {
		if (!cinta_in.equals("."))
			pila.pop();
		Stack<String> aux = new Stack<String>();
		for (int i = cinta_out.length-1;i>=0;i--)
			pila.push(cinta_out[i]);
		return pila;
	}
	
	public String newString(String str) {
		if (val_in.equals("."))
			return str;
		return str.substring(1);
	}

}
