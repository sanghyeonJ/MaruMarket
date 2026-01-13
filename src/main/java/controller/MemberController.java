package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.member.MemberInsert;
import service.member.MemberLogin;
import service.member.MemberLogout;
import service.member.UserIdCheck;


@WebServlet("/mem/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MemberController() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		String uri = request.getPathInfo();
		
		String page = null;
		
		switch(uri) {
		case "/login.do":
			page = "/member/login.jsp";
			break;
		case "/join.do":
			page = "/member/join.jsp";
			break;
		case "/memberInsert.do":
			new MemberInsert().doCommand(request, response);
			break;
		case "/useridcheck.do":
			new UserIdCheck().doCommand(request, response);
			break;
		case "/loginAction.do":
			new MemberLogin().doCommand(request, response);
			break;
		case "/logout.do":
			new MemberLogout().doCommand(request, response);
			break;
		default:
			System.out.println("잘못된 요청입니다.");
			break;
		}
		
		if(page != null) {
			RequestDispatcher rs = request.getRequestDispatcher(page);
			rs.forward(request, response);
		}
		
	}

}
