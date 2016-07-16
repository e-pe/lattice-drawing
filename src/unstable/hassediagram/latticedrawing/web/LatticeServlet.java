package unstable.hassediagram.latticedrawing.web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import unstable.hassediagram.latticedrawing.core.*;
import unstable.hassediagram.latticedrawing.formatters.*;

/**
 * Servlet implementation class LatticeServlet
 */
public class LatticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LatticeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String number = request.getParameter("number"); 
		String dimension = request.getParameter("dimension");
		String path = "dim" + dimension + "/dim" + dimension + "_" + number;
		
		String fileName = this.getServletContext().getRealPath("/Resources/" + path);
		Lattice lattice = Lattice.loadFromFile(fileName);	
		
		//String latex =  lattice.saveAs(LatticeFormatType.Latex);
		
		response.setContentType("application/json");
		response.getOutputStream().print(lattice.saveAs(LatticeFormatType.Json));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
