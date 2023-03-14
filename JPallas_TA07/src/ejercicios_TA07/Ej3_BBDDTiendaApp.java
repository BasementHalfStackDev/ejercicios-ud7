package ejercicios_TA07;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;

public class Ej3_BBDDTiendaApp {

	public static void main(String[] args) {

		// Crear BBDD y rellenar con 10 articulos
		Hashtable<String, Hashtable<Double, Integer>> controlStocks = new Hashtable<>();

		controlStocks = rellenaTabla(controlStocks, "Tomate", 0.99, 500);
		controlStocks = rellenaTabla(controlStocks, "Zanahoria", 1.00, 450);
		controlStocks = rellenaTabla(controlStocks, "Naranja", 0.60, 2000);
		controlStocks = rellenaTabla(controlStocks, "Limon", 0.80, 1500);
		controlStocks = rellenaTabla(controlStocks, "Manzana", 1.60, 3050);
		controlStocks = rellenaTabla(controlStocks, "Pera", 1.10, 1080);
		controlStocks = rellenaTabla(controlStocks, "Platano", 1.25, 1530);
		controlStocks = rellenaTabla(controlStocks, "Lechuga", 2.15, 2300);
		controlStocks = rellenaTabla(controlStocks, "Leche", 1.55, 125);
		controlStocks = rellenaTabla(controlStocks, "Mandarina", 0.45, 4020);

		muestraTabla(controlStocks);

		// Programa principal con su menu principal
		int i = 0;
		Scanner input = new Scanner(System.in).useLocale(Locale.US);

		while (i != 6) {
			System.out.println();
			System.out.println("Selecciona que quieres hacer.");
			System.out.println();
			System.out.println("1. Agregar nuevo producto");
			System.out.println("2. Modificar stock");
			System.out.println("3. Modificar precio");
			System.out.println("4. Ver todos los productos");
			System.out.println("5. Buscar producto");
			System.out.println("6. Salir");
			System.out.println();
			System.out.print("Tu seleccion: ");
			i = input.nextInt();
			System.out.println();

			if (i == 1) {
				System.out.print("Introduce el nombre del producto: ");
				String producto = input.next();
				System.out.print("Introduce el precio: ");
				double precio = input.nextDouble();
				System.out.print("Y el stock: ");
				int stock = input.nextInt();
				rellenaTabla(controlStocks, producto, precio, stock);
			} else if (i == 2) {

			} else if (i == 3) {

			} else if (i == 4) {
				muestraTabla(controlStocks);
			} else {
				System.out.println("ERROR, COMANDO NO RECONOCIDO");
				System.out.println();
			}

		}

		// Añadir productos y cantidades de productos existentes manualmente

		// Consultar stock

	}

	// Funcion que agrega valores a la tabla
	public static Hashtable<String, Hashtable<Double, Integer>> rellenaTabla(
			Hashtable<String, Hashtable<Double, Integer>> tabla, String producto, double precio, int stock) {
		Hashtable<Double, Integer> tablaInterior = new Hashtable<>();

		producto = producto.toUpperCase();
		tablaInterior.put(precio, stock);
		tabla.put(producto, tablaInterior);

		return tabla;
	}

	public static void muestraTabla(Hashtable<String, Hashtable<Double, Integer>> tabla) {
		System.out.println("PRODUCTO | PRECIO | STOCK DISPONIBLE");
		tabla.forEach((valor, precio) -> {
			System.out.println(valor + " " + precio.keys().nextElement() + " " + precio.elements().nextElement());
		});
	}

}
