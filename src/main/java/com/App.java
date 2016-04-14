package com;

import controlador.Funciones;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


/**
 * Clase Principal
 *
 * @author Juan Noguera
 */
public class App extends HttpServlet{

   /*
     * 
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        String  resultado = "<html>"
                + "<p>Tarea 6</p>"
                + "<br/>"
                + "<br/>"
                + "<table width='20%'>"
                + "<tr><td>p</td><td>dof</td><td>x</td><td>Resultado</td></tr>"
                + "<tr><td>0.20</td><td>6</td><td>0.55338</td><td>" + calculoEsperado(6, 20, 1, 0.20)    + "</td></tr>"
                + "<tr><td>0.45</td><td>15</td><td>1.75305</td><td>"+ calculoEsperado(15, 20, 1.0, 0.45) + "</td></tr>"
                + "<tr><td>0.495</td><td>4</td><td>4.60409</td><td>"+ calculoEsperado(4, 20, 1.0, 0.495) + "</td></tr>"
                + "</table>"
                + "</html>";
        pw.write(resultado);
    }

    /**
     * Metodo que permite ejecutar una aplicacion Java.
     *
     * @param args Argumentos de java
     * @throws Exception Excepcion al ejecutar la peticion.
     */
    public static void main(String[] args) throws Exception {

//		 Server server = new Server(8081);
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/tarea6");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new App()), "/*");
        server.start();
        server.join();
    }
    
    /**
     * Metodo que permite encontrar el valor de x superior, con el cual se calcula la integral.
     * @param dof Grados de libertad
     * @param numSeg Numero de segmentos
     * @param limiteSuperior valor inicial de X como limite superior de la integral
     * @param valorEsperado Valor esperado de la integral
     * @return String con el valor encontrado para X.
     */
    public String calculoEsperado(double dof, int numSeg, double limiteSuperior, double valorEsperado){
        
		Double resultado = 0.0;

		Funciones calc = new Funciones();

		resultado = calc.calcularIntegralEncontrarX(dof, numSeg, limiteSuperior, valorEsperado);

		return resultado.toString();
    }
}
