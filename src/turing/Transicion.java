package turing;

import java.util.ListIterator;
import java.util.Stack;

public class Transicion {

	String val_in;
	String val_out;
	Estado est_f;
	String move;
	static String blanco = ".";
	
	public Transicion(String val_in, String val_out, Estado est_f, String move) {
		this.val_in = val_in;
		this.val_out = val_out;
		this.est_f = est_f;
		this.move = move;	
	}
	public Transicion(Transicion t) {
		this.val_in = t.val_in;
		this.val_out = t.val_out;
		this.est_f = t.est_f;
		this.move = t.move;	
	}
	public void show() {
		System.out.println("  " + val_in + " " + est_f.getId() + " " + val_out + " " + move);
	}

	public boolean accept(char in) {
		return val_in.equals(""+in);
	}

	public Estado getNewEstado() {
		return est_f;
	}
	
	public String newString(String str, int index) {

		if (newIndex(index) > str.length()-1)
			str += blanco;
		if (index == 0) {
			str = blanco+str;
			index++;
		}
		String f = str.substring(0, index);
		String s = str.substring(index+1, str.length());
		return f + val_out + s;
	}
	
	public int newIndex(int index) {

		switch(move) {
		case "R":
			return index+1;
		case "L":
			if (index-1 < 0)
				return 0;
			return index-1;
		}
		
		return index;
	}
}
