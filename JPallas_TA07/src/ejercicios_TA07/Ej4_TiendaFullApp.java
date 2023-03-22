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

	// Menu principal con menus de empleado y cliente
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
					Thread.sleep(3000); // pausa 3 segundos para simular por el ejercicio
				} catch (InterruptedException e) {
				}
				menuEmpleado(controlStocks, input);
			} else if (i == 3) {
				System.out.println("Gracias por tu visita!");
				return;
			}

		}
	}

	// Menu para empleados donde pueden manipular, ver y buscar el stock de
	// productos
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
				return;
			} else {
				System.out.println("ERROR, COMANDO NO RECONOCIDO");
				System.out.println();
			}
		}
	}

	// Menu del cliente donde puede realizar compras a la tienda
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
				agregarProducto(controlStocks, carritoMain, input);
			} else if (i == 2) {
				verCarrito(carritoMain);
			} else if (i == 3) {
				pagarProductos(carritoMain, input);
			} else if (i == 4) {
				muestraStock(controlStocks);
			} else if (i == 5) {
				return;
			} else {
				System.out.println("ERROR, COMANDO NO RECONOCIDO");
				System.out.println();
			}

		}
	}

	// FUNCIONES
	// FUNCIONES PARA EL CONTROL DE STOCK
	// Funcion que agrega valores a la tabla de stocks sin comprobar
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

	// Muestra todos los productos y su stock en la base de datos
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

	// FUNCIONES PARA CLIENTE
	// Funcion que muestra el contenido del carrito
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

	// Funcion para agregar producto al carrito segun el stock
	public static Hashtable<String, ArrayList<String>> agregarProducto(
			Hashtable<String, Hashtable<Double, Integer>> controlStocks, Hashtable<String, ArrayList<String>> carrito,
			Scanner input) {

		// Pregunta al usuario el producto y comprueba que esté en la base de datos
		boolean existeProducto = false;
		String producto = "";
		do {
			// Pide el producto
			System.out.print("Escribe el nombre del producto a agregar: ");
			producto = input.next();
			// Pone el nombre del producto en mayus
			producto = producto.toUpperCase();

			// Comprueba que existe en la base de datos
			for (String key : controlStocks.keySet()) {
				if (key.equals(producto)) {
					System.out.println("Producto encontrado.");
					existeProducto = true;
				}
			}
			// Si no existe, muestra el stock de productos
			if (existeProducto == false) {
				System.out.println("No se encuentra este producto en la base de datos.");
				System.out.println("Productos disponibles:");
				System.out.println();
				muestraStock(controlStocks);
				System.out.println();
			}
		} while (existeProducto == false);

		// Coge el precio del producto y comprueba que la cantidad introducida no sea
		// superior al inventario actual
		double precioProducto = controlStocks.get(producto).keySet().iterator().next();
		boolean cantidadValida = false;
		int cantidad = 0;
		// Comprueba que la cantidad sea correcta y haya stock suficiente en el almacen
		do {
			System.out.print("Introduce la cantidad: ");
			cantidad = input.nextInt();
			int stockDisponible = controlStocks.get(producto).get(precioProducto);
			if (cantidad > stockDisponible) {
				System.out.println();
				System.out.println("No hay stock suficiente de este produco. La cantidad en el almacen es de: "
						+ stockDisponible + " unidades.");
				System.out.println();
			} else if (cantidad <= stockDisponible) { // Si es correcto, actualiza el stock de productos
				cantidadValida = true; 				  // restando las unidades agregadas al carrito
				System.out.println("Hecho!");
				int stockRestante = stockDisponible - cantidad;
				Hashtable<Double, Integer> tablaRestante = new Hashtable<>();
				tablaRestante.put(precioProducto, stockRestante);
				controlStocks.put(producto, tablaRestante);
			}
		} while (cantidadValida == false || cantidad <= 0);

		// Paso variables a String para el arrayList y calculo el precio con iva + total
		String sPrecio = String.valueOf(precioProducto);
		String sCantidad = String.valueOf(cantidad);
		double precioiva = precioProducto + (precioProducto * Globales.IVA / 100);
		double total = precioiva * cantidad;
		String sprecioiva = String.valueOf(precioiva);
		String stotal = String.valueOf(total);

		// Crea ArrayList de Strings

		ArrayList<String> listaValores = new ArrayList<>();

		// Pone todos los valores del producto en formato string
		listaValores.add(sCantidad);
		listaValores.add(sPrecio);
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
