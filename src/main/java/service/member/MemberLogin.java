package service.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Command;
import model.MemberDao;
import model.MemberDto;
import util.PasswordBcrypt;

public class MemberLogin implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		
		MemberDao dao = new MemberDao();
		MemberDto dto = dao.searchUserFromId(user_id);
		
		if(dto != null && PasswordBcrypt.checkPassword(user_pw, dto.getUser_pw())) {
			HttpSession session = request.getSession();
			session.setAttribute("user_id", dto.getUser_id());
			session.setAttribute("member_no", dto.getMember_no());
			session.setAttribute("loginUser", dto);
			response.getWriter().print("success");
		}else {
			response.getWriter().print("false");
		}
	}

}
