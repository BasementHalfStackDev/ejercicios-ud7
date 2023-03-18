package ejercicios_TA07;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;

public class Ej3_BBDDTiendaApp {

	public static void main(String[] args) {

		// Se crea BBDD de stocks
		Hashtable<String, Hashtable<Double, Integer>> controlStocks = new Hashtable<>();

		// Relleno con 10 articulos
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

		// Ejecuto programa principal
		mainMenu(controlStocks);

	}

	// Funcion que agrega valores a la tabla sin comprobar
	public static Hashtable<String, Hashtable<Double, Integer>> rellenaTabla(
			Hashtable<String, Hashtable<Double, Integer>> tabla, String producto, double precio, int stock) {
		Hashtable<Double, Integer> tablaInterior = new Hashtable<>();

		producto = producto.toUpperCase();
		tablaInterior.put(precio, stock);
		tabla.put(producto, tablaInterior);

		return tabla;
	}

	// Funcion que agrega nuevos productos, comprueba si existen, y los modifica si
	// ya existen
	public static Hashtable<String, Hashtable<Double, Integer>> agregaProducto(
			Hashtable<String, Hashtable<Double, Integer>> tabla, String producto, double precio, int stock) {

		// Convierte el nombre del producto a mayusculas para evitar errores
		producto = producto.toUpperCase();

		// Crea tabla donde agregar productos
		Hashtable<Double, Integer> tablaInterior = new Hashtable<>();

		// Try catch por si la tabla interior es null
		try {
			// Si el producto existe, clona la tabla interior para usar de comparador
			Hashtable<Double, Integer> comparador = (Hashtable<Double, Integer>) tabla.get(producto).clone();

			// Consigue el precio actual
			double key = comparador.keySet().iterator().next();
			// Consigue el stock actual y le suma el stock introducido
			int sumStock = comparador.get(key) + stock;
			System.out.println();
			System.out.println("Producto ya existente en la base de datos");

			// Si el precio introducido es diferente, lo actualiza
			if (key != precio) {
				System.out.println("Actualizando precio del producto...");
			}

			// Agrega el producto actualizado
			System.out.println("Agregando nuevo stock...");
			tablaInterior.put(precio, sumStock);
			tabla.put(producto, tablaInterior);
			return tabla;
		} catch (NullPointerException e) {
			// Si el producto no existe, lo agrega a la tabla
			tablaInterior.put(precio, stock);
			tabla.put(producto, tablaInterior);
			return tabla;
		}
	}

	public static void muestraTabla(Hashtable<String, Hashtable<Double, Integer>> tabla) {
		System.out.println("PRODUCTO | PRECIO | STOCK DISPONIBLE");
		tabla.forEach((valor, precio) -> {
			System.out.println(valor + " " + precio.keys().nextElement() + " " + precio.elements().nextElement());
		});
	}

	// Buscador de productos
	public static void buscadorProducto(Hashtable<String, Hashtable<Double, Integer>> tabla, String producto) {

		// Establece que aun no se ha encontrado y pone el nombre en mayusculas
		boolean found = false;
		producto = producto.toUpperCase();

		// Busca si el producto existe
		for (String key : tabla.keySet()) {
			// Si existe, lo marca como encontrado y muestra sus datos
			if (key.equals(producto)) {
				found = true;
				// Consigue la tabla interior
				Hashtable<Double, Integer> tablaInterior = tabla.get(key);

				// Y muestra los datos que contiene
				for (double precio : tablaInterior.keySet()) {
					int stock = tablaInterior.get(precio);
					System.out.println();
					System.out.println("PRODUCTO | PRECIO | STOCK DISPONIBLE");
					System.out.println(key + " | " + precio + " | " + stock);
				}
				return;
			}
		}

		// Si no existe, lo comunica al usuario
		if (found == false) {
			System.out.println();
			System.out.println("El producto no se encuentra en la base de datos.");
		}

		return;

	}

	// Programa principal con su menu
	public static void mainMenu(Hashtable<String, Hashtable<Double, Integer>> controlStocks) {
		// Seleccionador de opcion
		int i = 0;
		Scanner input = new Scanner(System.in).useLocale(Locale.US);

		// Sigue corriendo hasta que se marca la opcion de salida (4)
		while (i != 4) {
			System.out.println();
			System.out.println("Selecciona que quieres hacer.");
			System.out.println();
			System.out.println("1. Agregar nuevo producto");
			System.out.println("2. Ver stock de productos");
			System.out.println("3. Buscar producto");
			System.out.println("4. Salir");
			System.out.println();
			System.out.print("Tu seleccion: ");
			i = input.nextInt();
			System.out.println();

			// Acciones a realizar segun la opcion seleccionada
			if (i == 1) {
				System.out.print("Introduce el nombre del producto a buscar: ");
				String producto = input.next();
				System.out.print("Introduce el precio: ");
				double precio = input.nextDouble();
				System.out.print("Y el stock: ");
				int stock = input.nextInt();
				agregaProducto(controlStocks, producto, precio, stock);
			} else if (i == 2) {
				muestraTabla(controlStocks);
			} else if (i == 3) {
				System.out.print("Introduce el nombre del producto: ");
				String producto = input.next();
				buscadorProducto(controlStocks, producto);
			} else if (i == 4) {
				break;
			} else {
				System.out.println("ERROR, COMANDO NO RECONOCIDO");
				System.out.println();
			}
		}
		input.close();
	}

}
