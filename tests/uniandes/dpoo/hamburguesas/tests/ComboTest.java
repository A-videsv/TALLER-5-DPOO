package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class ComboTest {

    private Combo combo;

    @BeforeEach
    void setUp() throws IOException {
    	BufferedReader reader = new BufferedReader(new FileReader("data/combos.txt"));
        String line = reader.readLine(); 
        assertNotNull(line, "El archivo combos.txt está vacío o no se encontró la primera línea.");
        String[] data = line.split(";");
        String nombreCombo = data[0];
        double descuento = Double.parseDouble(data[1].replace("%", "")) / 100;
        ArrayList<ProductoMenu> itemsCombo = new ArrayList<>();
        for (int i = 2; i < data.length; i++) {
            itemsCombo.add(new ProductoMenu(data[i], 5000));
        }
        combo = new Combo(nombreCombo, descuento, itemsCombo);
        reader.close();
    }


    @Test
    void testGetNombre() {
        assertEquals("combo corral", combo.getNombre());
    }

    @Test
    void testGetPrecio() {
        int precioOriginal = 5000 * 3;
        int precioConDescuento = (int) (precioOriginal * (1 - 0.1)); 
        assertEquals(precioConDescuento, combo.getPrecio());
    }

    @Test
    void testGenerarTextoFactura() {
        String expectedText = "Combo combo corral\n Descuento: 0.1\n            13500\n";
        assertEquals(expectedText, combo.generarTextoFactura());
    }
}