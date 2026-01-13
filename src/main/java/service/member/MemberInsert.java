package service.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Command;
import model.MemberDao;
import model.MemberDto;
import util.PasswordBcrypt;
import util.KeyGenerator;


public class MemberInsert implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String user_name = request.getParameter("user_name");
		String email = request.getParameter("email");
		
		MemberDto dto = new MemberDto();
		dto.setUser_id(user_id);
		String hashPW = PasswordBcrypt.hashPassword(user_pw);
		dto.setUser_pw(hashPW);
		dto.setUser_name(user_name);
		dto.setEmail(email);
		dto.setUser_key(KeyGenerator.generateUserKey());
		
		MemberDao dao = new MemberDao();
		dao.memberInsert(dto);
		
		response.sendRedirect("/main");
	}

}
