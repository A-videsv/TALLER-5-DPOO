package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class ProductoMenuTest {

    private ProductoMenu producto;

    @BeforeEach
    void setUp() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("data/menu.txt"));
        String line = reader.readLine(); 
        if (line != null) {
            String[] data = line.split(";");
            String nombreProducto = data[0];
            int precioBase = Integer.parseInt(data[1]);
            producto = new ProductoMenu(nombreProducto, precioBase);
        }
        reader.close();
    }

    @Test
    void testGetNombre() {
        assertNotNull(producto, "El producto no se inicializó correctamente");
        assertEquals("corral", producto.getNombre(), "El nombre del producto no coincide con lo esperado");
    }

    @Test
    void testGetPrecio() {
        assertNotNull(producto, "El producto no se inicializó correctamente");
        assertEquals(14000, producto.getPrecio(), "El precio del producto no coincide con lo esperado");
    }

    @Test
    void testGenerarTextoFactura() {
        assertNotNull(producto, "El producto no se inicializó correctamente");
        String expectedText = "corral\n            14000\n";
        assertEquals(expectedText, producto.generarTextoFactura(), "El texto de factura no coincide con lo esperado");
    }
}