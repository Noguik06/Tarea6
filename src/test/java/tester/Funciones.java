/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

/**
 *
 * @author juannoguera
 */
public class Funciones {
    /**
	 * Metodo que permite calcular una sumatoria
	 * @param numeros Lista de numeros a sumar.
	 * @return double resultado de la sumatoria.
	 */
	public double calcularSumatoria(List<Double> numeros){
		
		double sumatoria = 0;
		
		for (Double numero : numeros) {
			
			sumatoria += numero;
		}
		
		return sumatoria;
	}
        
        /**
	 * Metodo que permite calcular el valor de la distribucion T.
	 * @param dof Grados de libertad
	 * @param x Valor de X
	 * @return double con el resultado del calculo de la distribucion T.
	 */
	public double calcularDistribucionT(double dof, double x) {

		double dividendo;
		double factor1;
		double factor2;
		double divisor;
		double multiplicador;
		double resultado;
		double dividendoGamma = (dof + 1) / 2f;
		double divisorGamma = dof / 2f;
		if (dividendoGamma % 1 == 0) {
			dividendo = funcionGammaEnteros((int) dividendoGamma);
		} else {
			dividendo = funcionGammaNoEnteros(dividendoGamma);
		}
		factor1 = Math.pow((dof * Math.PI), 0.5);
		if (divisorGamma % 1 == 0) {
			factor2 = funcionGammaEnteros((int) divisorGamma);
		} else {
			factor2 = funcionGammaNoEnteros(divisorGamma);
		}
		divisor = factor1 * factor2;
		multiplicador = Math
				.pow(1 + ((Math.pow(x, 2)) / dof), -((dof + 1) / 2));
		resultado = (dividendo / divisor) * multiplicador;
		return resultado;
	}

	/**
	 * Metodo que permite calcular el valor de la funcion gamma cuando el parametro es entero.
	 * @param x Parametro de la funcion gamma.
	 * @return long con el resultado del calculo de la funcion gamma para numero entero.
	 */
	public long funcionGammaEnteros(int x) {
		
		long factorial = 1;
		x -= 1;
		for (int i = 1; i <= x; i++) {

			factorial *= i;
		}
		return factorial;
	}

	/**
	 * Metodo que permite calcular el valor de la funcion gamma cuando el parametro no es entero.
	 * @param x Parametro de la funcion gamma.
	 * @return double con el resultado del calculo de la funcion gamma para numero no entero.
	 */
	public double funcionGammaNoEnteros(double x) {
		double factorial = 1;
		x -= 1;
		while (x >= 0.5) {

			factorial *= x;
			x -= 1;
		}
		factorial *= Math.sqrt(Math.PI);
		return factorial;
	}
        
        /**
	 * valor del error aceptable
	 */
	public static final Double E = 0.00001;

	/**
	 * Metodo que permite realizar el calculo de la integral numerica.
	 * @param dof Grados de libertad
         * @param numSeg Numero de segmentos
         * @param valorX Valor de X
	 * @return double con el resultado del calculo de la integral.
	 */
	public double calcularIntegral(double dof, int numSeg, double valorX) {
		
		ClaseDatos in = new ClaseDatos(dof, numSeg, valorX);
		double integralInicial = in.calcularIntegral();
		
		numSeg *= 2;
		ClaseDatos in2 = new ClaseDatos(dof, numSeg, valorX);
		double integralDefinitiva = in2.calcularIntegral();
		
		while((integralInicial - integralDefinitiva) > E){
			
			integralInicial = integralDefinitiva;
			numSeg *= 2;
			
			in2 = new ClaseDatos(dof, numSeg, valorX);
			integralDefinitiva = in2.calcularIntegral();
		}
		
		return integralDefinitiva;
	}

	/**
	 * Metodo que permite encontrar el valor de x superior, con el cual se calcula la integral.
	 * @param dof Grados de libertas.
	 * @param numSeg Numero de segmentos.
	 * @param limiteSuperior Valor inicial de X.
	 * @param valorEsperado Valor resultado esperado de la integral.
	 * @return double con el valor de X
	 */
	public double calcularIntegralEncontrarX(double dof, int numSeg, double limiteSuperior, double valorEsperado) {
		
		boolean ajustarX = true;
		double d = 0.5;

		DecimalFormat formatoDecimal = new DecimalFormat("#.#####");
		DecimalFormatSymbols formatoDecimalSym = formatoDecimal.getDecimalFormatSymbols();
		formatoDecimalSym.setDecimalSeparator('.');
		formatoDecimal.setDecimalFormatSymbols(formatoDecimalSym);

		double integralInicial = Double.valueOf(formatoDecimal.format(calcularIntegral(dof, numSeg, limiteSuperior)));

		double integralDefinitiva = 0;

		double diferenciaIntegrales = Math.abs(integralInicial - valorEsperado);

		if (diferenciaIntegrales < E) {

			return integralInicial;

		} else {

			while (diferenciaIntegrales > E) {

				diferenciaIntegrales = integralDefinitiva - valorEsperado;

				if (ajustarX && (diferenciaIntegrales < E)) {

					d = ajustarD(d, limiteSuperior);
					
				} else if (!ajustarX && (diferenciaIntegrales > E)) {

					d = ajustarD(d, limiteSuperior);
				}

				if (integralDefinitiva > valorEsperado) {

					ajustarX = true;

				} else {

					ajustarX = false;
				}
				
				limiteSuperior = ajustarValorX(ajustarX, limiteSuperior, d);

				integralInicial = integralDefinitiva;
				integralDefinitiva = Double.valueOf(formatoDecimal
						.format(calcularIntegral(dof, numSeg, limiteSuperior)));

				diferenciaIntegrales = Math.abs(integralDefinitiva - valorEsperado);
			}
		}

		limiteSuperior = Double.valueOf(formatoDecimal.format(limiteSuperior));
		return limiteSuperior;
	}
	
	/**
	 * Metodo que permite ajustar el valor de X si es necesario.
	 * @param ajustarX indica si el valor de X debe ser ajustado.
	 * @param limiteSuperior valor a ajustar.
	 * @param d valor d.
	 * @return double con el valor de X.
	 */
	private double ajustarValorX(boolean ajustarX, double limiteSuperior, double d){
		
		if (ajustarX) {

			limiteSuperior -= d;

		} else {

			limiteSuperior += d;
		}

		return limiteSuperior;
	}
	
	/**
	 * Metodo que permite ajustar el valor de d, si es necesario.
	 * @param d Valor a ajustar.
	 * @param limiteSuperior Valor de X.
	 * @return double con el valor de d.
	 */
	private double ajustarD(double d, double limiteSuperior){
		
		if (limiteSuperior != 1.0) {

			d /= 2;
		}

		return d;
	}
        
        public String calculoEsperado(double dof, int numSeg, double limiteSuperior, double valorEsperado){
        
		Double resultado = 0.0;

		resultado = calcularIntegralEncontrarX(dof, numSeg, limiteSuperior, valorEsperado);

		return resultado.toString();
        }

}
