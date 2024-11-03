package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class ProductoAjustadoTest {

    private ProductoAjustado productoAjustado;

    @BeforeEach
    void setUp() throws IOException {
    	// Cargar el producto base desde el archivo menu.txt
        BufferedReader menuReader = new BufferedReader(new FileReader("data/menu.txt"));
        String menuLine = menuReader.readLine();
        assertNotNull(menuLine, "El archivo menu.txt está vacío o no se encontró la primera línea.");
        String[] menuData = menuLine.split(";");
        ProductoMenu productoBase = new ProductoMenu(menuData[0], Integer.parseInt(menuData[1]));
        productoAjustado = new ProductoAjustado(productoBase);
        menuReader.close();

        // Solo agrega el ingrediente "lechuga" desde ingredientes.txt
        BufferedReader ingReader = new BufferedReader(new FileReader("data/ingredientes.txt"));
        String ingLine;
        while ((ingLine = ingReader.readLine()) != null) {
            String[] ingData = ingLine.split(";");
            if (ingData[0].equals("lechuga")) {
                Ingrediente ingrediente = new Ingrediente(ingData[0], Integer.parseInt(ingData[1]));
                productoAjustado.getAgregados().add(ingrediente);
                break; // Detenemos la lectura después de encontrar "lechuga"
            }
        }
        ingReader.close();
    }

    @Test
    void testGetNombre() {
        assertEquals("corral", productoAjustado.getNombre());
    }

    @Test
    void testGetPrecio() {
        // Precio del producto base más el primer ingrediente en ingredientes.txt
        int precioBase = 14000; // Precio del producto "corral"
        int precioTotalEsperado = precioBase;

        // Sumar costos de los ingredientes agregados
        precioTotalEsperado += 1000; // lechuga
        assertEquals(precioTotalEsperado, productoAjustado.getPrecio());
    }

    @Test
    void testGenerarTextoFactura() {
    	String expectedText = "corral\n    +lechuga                1000\n            15000\n";
        assertEquals(expectedText, productoAjustado.generarTextoFactura());
    }
}