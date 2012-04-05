package org.eclipse.uomo.examples.units.android.web.sandbox;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Tempconv
 */
public class Tempconv extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		out.println("<h1>Degrees F to degrees C conversion</h1>");
		if (in.getParameter("t_in") != null) {
			String supplied_temp = in.getParameter("t_in");
			float base_temp = -999;
			boolean valid = false;
			try {
				base_temp = Float.parseFloat(supplied_temp);
				valid = true;
			} catch (NumberFormatException e) {
				out.println("<h4><font color=red>"
						+ "Invalid Temperature Supplied</font></h4)<br>");
			}
			if (valid) {
				out.println("<h4>Temperature " + base_temp
						+ " deg f converts to "
						+ ((((float) base_temp - 32.0f) / 9.0f) * 5.0f)
						+ " deg celcius </h4>");
			}
		}
		out.println("<form>Enter degrees F: ");
		out.println("<input name=t_in>");
		out.println("<input type=submit></form><br>");

		out.println("<a href=index.jsp>Return to home page of this webapp</a><br><br>");
		out.println("Initial contribution by Well House Consultants Ltd<br>");
		out.println("web <a href=http://www.wellho.net>http://www.wellho.net</a><br>");
		out.println("email <a href=mailto:info@wellho.net>info@wellho.net</a><br>");
		out.println("</body></html>");
		out.close();
	}

}
