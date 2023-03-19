package ejercicios_TA07;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;

public class Ej4_TiendaFullApp {

	public static void main(String[] args) {

		// Se crea BBDD de stocks
		Hashtable<String, Hashtable<Double, Integer>> controlStocks = new Hashtable<>();

		// Relleno BBDD con 10 articulos
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

		// Creo input
		Scanner input = new Scanner(System.in).useLocale(Locale.US);

		// Ejecuto programa principal
		menuPrincipal(controlStocks, input);

		// Cierro scanner cuando termine el programa
		input.close();

	}

	// Define el IVA como variable global

	public class Globales {
		public static final double IVA = 21;
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

	public static void muestraStock(Hashtable<String, Hashtable<Double, Integer>> tabla) {
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
	public static void menuEmpleado(Hashtable<String, Hashtable<Double, Integer>> controlStocks, Scanner input) {
		// Seleccionador de opcion
		int i = 0;

		// Sigue corriendo hasta que se marca la opcion de salida (4)
		while (i != 4) {
			System.out.println();
			System.out.println("Selecciona que quieres hacer.");
			System.out.println();
			System.out.println("1. Agregar nuevo producto");
			System.out.println("2. Ver stock de productos");
			System.out.println("3. Buscar producto");
			System.out.println("4. Atras");
			System.out.println();
			System.out.print("Tu seleccion: ");
			i = input.nextInt();
			System.out.println();

			// Acciones a realizar segun la opcion seleccionada
			if (i == 1) {
				System.out.print("Introduce el nombre del producto a agregar: ");
				String producto = input.next();
				System.out.print("Introduce el precio: ");
				double precio = input.nextDouble();
				System.out.print("Y el stock: ");
				int stock = input.nextInt();
				agregaProducto(controlStocks, producto, precio, stock);
			} else if (i == 2) {
				muestraStock(controlStocks);
			} else if (i == 3) {
				System.out.print("Introduce el nombre del producto a buscar: ");
				String producto = input.next();
				buscadorProducto(controlStocks, producto);
			} else if (i == 4) {
				menuPrincipal(controlStocks, input);
			} else {
				System.out.println("ERROR, COMANDO NO RECONOCIDO");
				System.out.println();
			}
		}
	}

	public static void menuCliente(Scanner input, Hashtable<String, Hashtable<Double, Integer>> controlStocks) {
		// Crear carrito de la compra
		Hashtable<String, ArrayList<String>> carritoMain = new Hashtable<>();

		// Programa principal con su menu principal
		int i = 0;

		while (i != 5) {
			System.out.println("Selecciona que quieres hacer.");
			System.out.println();
			System.out.println("1. Introducir articulo al carrito");
			System.out.println("2. Ver articulos en el carrito");
			System.out.println("3. Pagar los articulos");
			System.out.println("4. Ver productos disponibles");
			System.out.println("5. Salir");
			System.out.println();
			System.out.print("Tu seleccion: ");
			i = input.nextInt();
			System.out.println();

			if (i == 1) {
				agregarProducto(carritoMain, input);
			} else if (i == 2) {
				verCarrito(carritoMain);
			} else if (i == 3) {
				pagarProductos(carritoMain, input);
			} else if (i == 4) {
				muestraStock(controlStocks);
			} else if (i == 5) {
				menuPrincipal(controlStocks, input);
			} else {
				System.out.println("ERROR, COMANDO NO RECONOCIDO");
				System.out.println();
			}

		}
	}

	public static void menuPrincipal(Hashtable<String, Hashtable<Double, Integer>> controlStocks, Scanner input) {
		// Seleccionador de opcion
		int i = 0;

		// Sigue corriendo hasta que se marca la opcion de salida (4)
		while (i != 3) {
			System.out.println();
			System.out.println("Bienvenido! Eres empleado o cliente?");
			System.out.println("1. Cliente");
			System.out.println("2. Empleado");
			System.out.println("3. Salir");
			System.out.println();
			System.out.print("Tu seleccion: ");
			i = input.nextInt();
			System.out.println();

			if (i == 1) {
				menuCliente(input, controlStocks);
			} else if (i == 2) {
				System.out.print("Introduce tu contraseña: ");
				String passw = input.next();
				System.out.println("Autenticando . . .");
				try {
					Thread.sleep(3000); // 3 seconds
				} catch (InterruptedException e) {
				}
				menuEmpleado(controlStocks, input);
			} else if (i == 3) {
				System.out.println("Gracias por tu visita!");
				return;
			}

		}
	}

	public static void verCarrito(Hashtable<String, ArrayList<String>> carrito) {

		// Muestra mensaje de carrito vacio si no hay nada y termina la funcion si es
		// positivo
		if (carrito.isEmpty()) {
			System.out.println("La cesta de la compra esta vacia");
			System.out.println();
			return;
		}

		// Si hay contenido, pone el titulo de la tabla a mostrar
		System.out.println("Producto | Cantidad | Precio | IVA | Precio con IVA | Total");
		System.out.println();

		// Inicializa variable para calcular el total a pagar
		double totalfinal = 0;

		// Itera por el diccionario, y escribe todo los valores de cada producto
		for (String producto : carrito.keySet()) {
			ArrayList<String> linea = carrito.get(producto);

			// Va agregando al contador total, el total a pagar por cada producto
			totalfinal += Double.parseDouble(linea.get(4));

			System.out.println(producto + " | " + linea.get(0) + " | " + linea.get(1) + " | " + linea.get(2) + " | "
					+ linea.get(3) + " | " + linea.get(4));
		}

		// Muestra el total a pagar por todos los productos con el IVA
		System.out.println();
		System.out.println("El total a pagar es de: " + totalfinal);
		System.out.println();
	}

	// Funcion para agregar producto
	public static Hashtable<String, ArrayList<String>> agregarProducto(Hashtable<String, ArrayList<String>> carrito,
			Scanner input) {

		// Pregunta al usuario el producto, cantidad y precio
		System.out.print("Escribe el nombre del producto a agregar: ");
		String producto = input.next();

		// Pone el nombre del producto en mayus
		producto = producto.toUpperCase();

		// Pide cantidad del producto hasta que sea el adecuado
		String scantidad = "";
		int cantidad = 0;

		do {
			System.out.print("Cantidad del producto: ");
			scantidad = input.next();
			cantidad = Integer.parseInt(scantidad);
			if (scantidad.isEmpty() || cantidad <= 0) {
				System.out.println();
				System.out.println("Error, la cantidad introducida no es correcta");
				System.out.println();
			}
		} while (scantidad.isEmpty() || cantidad <= 0);

		// Pide el precio del producto, comprueba que es valido y calcula el iva y total
		String sprecio = "";
		double precio = 0;
		double precioiva = 0;
		double total = 0;

		do {
			System.out.print("Precio del producto: ");
			sprecio = input.next();
			precio = Double.parseDouble(sprecio);
			if (sprecio.isEmpty() || precio <= 0) {
				System.out.println();
				System.out.println("Error, el precio introducido no es correcto");
				System.out.println();
			}
			precioiva = precio + (precio * Globales.IVA / 100);
			total = precioiva * cantidad;
		} while (sprecio.isEmpty() || precio <= 0);

		// Crea ArrayList de Strings

		ArrayList<String> listaValores = new ArrayList<>();

		// Convierte todo a string, y lo agrega a la lista

		String sprecioiva = String.valueOf(precioiva);
		String stotal = String.valueOf(total);

		listaValores.add(scantidad);
		listaValores.add(sprecio);
		listaValores.add("21%");
		listaValores.add(sprecioiva);
		listaValores.add(stotal);

		// Agrega al carrito el producto junto sus datos
		carrito.put(producto, listaValores);

		System.out.println();

		return carrito;

	}

	// Funcion para pagar los productos
	public static Hashtable<String, ArrayList<String>> pagarProductos(Hashtable<String, ArrayList<String>> carrito,
			Scanner input) {

		// Calcula el total a pagar segun lo que hay en el carrito
		double totalfinal = 0;

		for (String producto : carrito.keySet()) {
			ArrayList<String> linea = carrito.get(producto);
			totalfinal += Double.parseDouble(linea.get(4));
		}

		// Muestra el total a pagar
		System.out.println("El total a pagar es de " + totalfinal);

		// Creado input en formato US para usar '.' para separar decimales de forma
		// consistente en todo el programa

		double pago = 0;

		// Comprueba que el pago sea mayor a la cantidad total a pagar
		while (pago < totalfinal) {
			System.out.print("Inserte la cantidad a pagar: ");
			pago = input.nextDouble();
			if (pago < totalfinal) {
				System.out.println("Error, su saldo es inferior al total a pagar.");
			}
		}

		// Si es mayor, calcula y muestra el cambio
		if (pago > totalfinal) {
			double cambio = pago - totalfinal;
			System.out.println("Su cambio es de " + cambio);
		}

		// Una vez efectuada la compra, despide al usuario y borra el carrito
		System.out.println("Gracias por confiar en nosotros! Hasta la proxima!");
		System.out.println();

		carrito.clear();

		// Devuelve el carrito ahora vacio
		return carrito;
	}

}
