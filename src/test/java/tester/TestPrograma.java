package tester;



import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


/**
 * TestCase para la clase edu.uniandes.ecos.psp2.modelo.Sumatoria
 * @author Aleja Chica
 *
 */
public class TestPrograma {

	/**
	 * Lista de numero de prueba
	 */
	private List<Double> numeros;
	
	/**
	 * Objeto que se va a probar
	 */
	private Funciones funciones;

	/**
	 * Metodo que permite configurar los datos de prueba
	 */
	@Before
	public void configurarDatos() {
		
		funciones = new Funciones();

	}

	@Test
	public void test1() {
		String resultado = funciones.calculoEsperado(6, 20, 1, 0.20);
		Assert.assertEquals("0.55341", resultado);
	}
        @Test
	public void test2() {
		String resultado = funciones.calculoEsperado(15, 20, 1.0, 0.45);
		Assert.assertEquals("1.75305", resultado);
	}
        @Test
	public void test3() {
		String resultado = funciones.calculoEsperado(4, 20, 1.0, 0.495);
		Assert.assertEquals("4.60352", resultado);
	}
}
