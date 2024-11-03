package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class PedidoTest {

    private Pedido pedido;

    @BeforeEach
    void setUp() throws IOException {
        pedido = new Pedido("Juan Perez", "Calle 123");

        // Agregar un producto base al pedido
        ProductoMenu productoBase = new ProductoMenu("corral", 14000);
        pedido.agregarProducto(productoBase);

        // Crear un producto ajustado y agregarlo al pedido
        ProductoAjustado productoAjustado = new ProductoAjustado(productoBase);
        Ingrediente ingredienteExtra = new Ingrediente("lechuga", 1000);
        productoAjustado.getAgregados().add(ingredienteExtra);
        pedido.agregarProducto(productoAjustado);
    }

    @Test
    void testGetIdPedido() {
        int id = pedido.getIdPedido();
        Pedido nuevoPedido = new Pedido("Maria Lopez", "Calle 456");
        assertEquals(id + 1, nuevoPedido.getIdPedido());
    }

    @Test
    void testGetNombreCliente() {
        assertEquals("Juan Perez", pedido.getNombreCliente());
    }

    @Test
    void testAgregarProducto() {
        ProductoMenu producto = new ProductoMenu("papas medianas", 5500);
        pedido.agregarProducto(producto);
        assertEquals(3, pedido.getProductos().size()); // Verificar que hay tres productos en total
    }

    @Test
    void testGetPrecioNetoPedido() {
        int precioBase = 14000;
        int precioAjustado = 14000 + 1000; // Precio del producto base + lechuga
        int precioNetoEsperado = precioBase + precioAjustado;
        assertEquals(precioNetoEsperado, pedido.getPrecioNetoPedido());
    }

    @Test
    void testGetPrecioIVAPedido() {
        int precioNeto = pedido.getPrecioNetoPedido();
        int ivaEsperado = (int) (precioNeto * 0.19);
        assertEquals(ivaEsperado, pedido.getPrecioIVAPedido());
    }

    @Test
    void testGetPrecioTotalPedido() {
        int precioTotalEsperado = pedido.getPrecioNetoPedido() + pedido.getPrecioIVAPedido();
        assertEquals(precioTotalEsperado, pedido.getPrecioTotalPedido());
    }

    @Test
    void testGenerarTextoFactura() {
        String expectedText = "Cliente: Juan Perez\n" +
                              "Dirección: Calle 123\n" +
                              "----------------\n" +
                              "corral\n            14000\n" +
                              "corral\n    +lechuga                1000\n            15000\n" +
                              "----------------\n" +
                              "Precio Neto:  29000\n" + // 14000 + 15000
                              "IVA:          5510\n" +  // 19% de 29000
                              "Precio Total: 34510\n";  // Precio Neto + IVA
        assertEquals(expectedText, pedido.generarTextoFactura());
    }

    @Test
    void testGuardarFactura() {
        File archivo = new File("factura_test.txt");
        try {
            pedido.guardarFactura(archivo);
            assertTrue(archivo.exists());
        } catch (FileNotFoundException e) {
            fail("No se pudo guardar el archivo de la factura.");
        } finally {
            archivo.delete(); // Limpiar archivo después de la prueba
        }
    }
}