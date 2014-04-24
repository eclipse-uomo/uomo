package org.eclipse.uomo.examples.units.android.web;

import static org.eclipse.uomo.units.SI.CELSIUS;
import static org.eclipse.uomo.units.impl.system.USCustomary.FAHRENHEIT;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.uomo.units.*;
import org.eclipse.uomo.units.impl.quantity.TemperatureAmount;
import org.unitsofmeasurement.quantity.Quantity;
import org.unitsofmeasurement.quantity.Temperature;
import org.unitsofmeasurement.unit.UnitConverter;

/**
 * Servlet implementation class UnitConverterServlet
 * @param <Q>
 */
public class UnitConverterServlet<Q extends Quantity<Q>> extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Quantity<Temperature> amount;
	private UnitConverter converter;
	
    /**
     * Default constructor. 
     */
    public UnitConverterServlet() {
        amount = new TemperatureAmount(0, CELSIUS);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest in, HttpServletResponse output)
			throws ServletException, IOException {

		output.setContentType("text/html");
		PrintWriter out = output.getWriter();

		out.println("<html><head><title>Temperature Conversion</title></head>");
		out.println("<body>");
		out.println("<h1> " + FAHRENHEIT + " to " + CELSIUS + " conversion</h1>");
		if (in.getParameter("t_in") != null) {
			String supplied_temp = in.getParameter("t_in");
			double base_temp = -999;
			TemperatureAmount base_temp_amt = null;
			boolean valid = false;
			try {
				base_temp = Double.parseDouble(supplied_temp);
				base_temp_amt = new TemperatureAmount(base_temp, FAHRENHEIT);
				valid = true;
			} catch (NumberFormatException e) {
				out.println("<h4><font color=red>"
						+ "Invalid Temperature Supplied</font></h4)<br>");
			}
			if (valid) {
				out.println("<h4>Temperature " + base_temp_amt
						//+ " "+ FAHRENHEIT 
						+ " converts to "						
						//+ ((((double) base_temp - 32.0d) / 9.0d) * 5.0d)
						//+ " " + CELSIUS
						+ base_temp_amt.to(CELSIUS) 
						+ " </h4>");
			}
		}
		out.println("<form>Enter " + FAHRENHEIT + ": ");
		out.println("<input name=t_in>");
		out.println("<input type=submit></form><br>");

		out.println("<a href=index.jsp>Return to home page of this webapp</a><br><br>");
		out.println("</body></html>");
		out.close();
	}
}
