package ejercicios_TA07;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Hashtable;

public class Ej1_notaMediaApp {

	public static void main(String[] args) {

		int l = 0;

		// Pide cantidad de alumnos en el curso y comprueba que sea correcta
		do {
			String lstring = JOptionPane.showInputDialog("Introduce la cantidad de alumnos del curso");
			l = Integer.parseInt(lstring);

			if (l < 1) {
				JOptionPane.showMessageDialog(null,
						"Por favor, introduce un numero positivo mayor de 0 y sin decimales.");
			}
		} while (l < 1);

		// Inicializa hashtable con nombre y lista de notas, junto la lista de notas
		Hashtable<String, Double> notaMedia = new Hashtable<>();

		// Asigna nombre y notas a cada alumno
		notaMedia = rellenaHash(l, notaMedia);

		// Muestra resultados por consola
		muestraValoresHash(notaMedia);

	}

	// Funcion que rellena la Hash table con el nombre de cada alumno y su nota
	// media. Calculandola mediante una lista con todas sus notas hasta el momento
	// (5 actividades como ejemplo)
	public static Hashtable<String, Double> rellenaHash(int n, Hashtable<String, Double> hashTable) {

		int actividades = 5;
		ArrayList<Double> notas = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			String nombre = JOptionPane.showInputDialog("Introduce el nombre del alumno");
			for (int j = 0; j < actividades; j++) {
				String sNota = JOptionPane.showInputDialog("Introduce las notas de sus 5 actividades realizadas");
				Double nota = Double.parseDouble(sNota);
				notas.add(nota);
			}

			double notamedia = 0;

			for (int k = 0; k < notas.size(); k++) {
				notamedia += notas.get(k);
			}

			notamedia = notamedia / actividades;
			hashTable.put(nombre, notamedia);
			notas.clear();

		}
		return hashTable;
	}

	// Muestra contenido de la hash table, mostrando el nombre del alumno junto su
	// nota media usando loop for Each
	public static void muestraValoresHash(Hashtable<String, Double> tabla) {
		tabla.forEach((clave, valor) -> System.out.println("La nota media de " + clave + " es de: " + valor));
	}

}
