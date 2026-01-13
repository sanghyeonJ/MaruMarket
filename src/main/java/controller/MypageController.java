package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.main.MypageList;

@WebServlet("/my/*")
public class MypageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MypageController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String uri = request.getPathInfo();

	    if (uri != null && (uri.contains("like") || uri.contains("buy") || uri.contains("sell"))) {
	        new MypageList().doCommand(request, response);
	    }

	    if (response.isCommitted()) return;

	    String xHeader = request.getHeader("X-Requested-With");
	    String target = ("XMLHttpRequest".equals(xHeader)) ? "/mainListItem.jsp" : "/mypage/list.jsp";
	    
	    request.getRequestDispatcher(target).forward(request, response);
	}

}
