package ejercicios_TA07;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;

public class Ej2_cajaSupermercadoApp {

	public static void main(String[] args) {

		// Crear carrito de la compra
		Hashtable<String, ArrayList<String>> carritoMain = new Hashtable<>();

		// Creado scanner formato US para usar '.' para separar doubles sin que de error
		int i = 0;
		Scanner input = new Scanner(System.in).useLocale(Locale.US);

		// Programa principal con su menu principal
		while (i != 4) {
			System.out.println("Selecciona que quieres hacer.");
			System.out.println();
			System.out.println("1. Introducir articulo al carrito");
			System.out.println("2. Ver articulos en el carrito");
			System.out.println("3. Pagar los articulos");
			System.out.println("4. Salir");
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
				break;
			} else {
				System.out.println("ERROR, COMANDO NO RECONOCIDO");
				System.out.println();
			}

		}

	}

	// Define el IVA como variable global

	public class Globales {
		public static final double IVA = 21;
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

	// Ver carrito

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
