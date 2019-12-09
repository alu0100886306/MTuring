package turing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class MTuring {
	
	String blanco;
	ArrayList<Estado> estados;
	ArrayList<String> alf_cadena_in;
	ArrayList<String> alf_cadena_out;
	
	MTuring(String f) throws Exception{
		File file = new File(f);
		Scanner input = new Scanner(file);
		
		estados = new ArrayList<Estado>();
		ArrayList<String> ids = new ArrayList<String>();
		
		String line;
		do {
		line = input.nextLine();
		}while(line.charAt(0) == '#');//Elimino comentarios
		
		//Estados
		String []tokens = line.split(" ");
		for (String str : tokens) {
			estados.add(new Estado(str));
			ids.add(str);
		}
		
		//Alfabeto cadena de entrada
		alf_cadena_in = new ArrayList<String>();
		line = input.nextLine();
		tokens = line.split(" ");
		for (String str : tokens)
			alf_cadena_in.add(str);
		
		//Alfabeto cadena de salida
		alf_cadena_out = new ArrayList<String>();
		line = input.nextLine();
		tokens = line.split(" ");
		for (String str : tokens)
			alf_cadena_out.add(str);
		
		//Estado inicial
		String ini = input.nextLine();
		if (!ids.contains(ini)) throw new Exception("Estado inicial '" + ini + "' no existe");
		estados.get(ids.indexOf(ini)).setIni();
		
		//Simbolo blanco
		blanco = input.nextLine();
		if (!alf_cadena_out.contains(blanco)) throw new Exception("Caracter de cinta '" + blanco + "' no declarado");		
		Transicion.blanco = blanco;
		//Estados de aceptacion
		line = input.nextLine();
		tokens = line.split(" ");
		for (String str : tokens) {
			if (str.isEmpty()) break;
			if (!ids.contains(str)) throw new Exception("Estado de aceptacion '" + str + "' no existe");
			int i = ids.indexOf(str);
			if (i != -1)
				estados.get(i).setFinal();
		}
		
		//Transiciones
		while(input.hasNext()) {
		    line = input.nextLine();
		    tokens = line.split(" ");
		    String est_i = tokens[0];//Estado inicial
		    if (!ids.contains(est_i)) 
		    	throw new Exception("En transicion: Estado '" + est_i + "' no declarado");
		    String val_in = tokens[1];//Valor de entrada
		    if (!alf_cadena_in.contains(val_in) && !alf_cadena_out.contains(val_in)) 
		    	throw new Exception("En transicion: Caracter de alfabeto '" + val_in + "' no declarado");
		    String est_f = tokens[2];//Estado final
		    if (!ids.contains(est_f)) 
		    	throw new Exception("En transicion: Estado '" + est_f + "' no declarado");
		    String val_out = tokens[3];//Valor de cadena a escribir
		    if (!alf_cadena_out.contains(val_out)) 
		    	throw new Exception("En transicion: Caracter de pila '" + val_out + "' no declarado");
		    String move = tokens[4];
		    if (!move.equals("R") && !move.equals("L") && !move.equals("S"))
		    	throw new Exception("Movimiento '" + move + "' invalido");
		    Transicion t = new Transicion(val_in, val_out, estados.get(ids.indexOf(est_f)), move);
		    estados.get(ids.indexOf(est_i)).addTrans(t);
		}
	}
	
	public void start(String string, boolean see) throws Exception {
		System.out.println(string);
		Estado actual = new Estado("");
		for (Estado e : estados)
			if (e.inicial()) actual = e;
		if (actual.getId() == "") throw new Exception("No existe estado inicial");
		String trz = actual.getId() + " "; 
		
		int index = 1;
		
		string = blanco + string + blanco;
		
		actual.transita(string, index, trz, see);
	}
	
	public void show() {
		for (Estado e : estados)
			e.show();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		String fileTuring;
		int ncads = 0;
		String cads[] = {};
				
		boolean deb = false;
		
		ArrayList<String> cades = new ArrayList<String>();
		if (args.length == 2) {
			fileTuring = args[0];
			File file = new File(args[1]);
			Scanner input = new Scanner(file);
			deb = Boolean.parseBoolean(input.nextLine());
			while(input.hasNext()) {
				cades.add(input.nextLine());
			}
		}
		else {
			Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		    System.out.println("Introduce fichero con el Automata: ");
		    fileTuring = myObj.nextLine();
		    System.out.println("Introduce numero de cadenas a analizar: ");
		    ncads = Integer.parseInt(myObj.nextLine());
		    cads = new String[ncads];
		    for (int i = 0;i<ncads;i++) {
		    	System.out.print((i+1)+":");
		    	cads[i] = myObj.nextLine();
		    }
		    System.out.println("Debugg?(Y/N): ");
		    String opc = myObj.nextLine();
		    
			if (opc.equals("Y")) deb = true;
		}
		
		try {		
			MTuring mt = new MTuring(fileTuring);
			mt.show();
			if (args.length == 2) {
				for (String str : cades) {
					mt.start(str,deb);
				}
			}else {
				for (int i =0;i<ncads;i++) 
					mt.start(cads[i],deb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}