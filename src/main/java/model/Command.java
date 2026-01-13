package model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

	void doCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	// dao 메서드를 호출하여 crud 작업처리를 위한 설계도를 만들기위해
	// curd를 처리햐려면 request와 response가 필요하기 때문에
}
