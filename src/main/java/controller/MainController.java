package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.main.GetMainList;

@WebServlet("/main")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MainController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetMainList service = new GetMainList();
	    service.doCommand(request, response);
	    
	    String xRequestedWith = request.getHeader("X-Requested-With");
	    
	    if ("XMLHttpRequest".equals(xRequestedWith)) {
	        RequestDispatcher dispatcher = request.getRequestDispatcher("mainListItem.jsp");
	        dispatcher.forward(request, response);
	    } else {
	        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
	        dispatcher.forward(request, response);
	    }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
