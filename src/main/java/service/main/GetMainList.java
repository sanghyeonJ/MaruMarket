package service.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CategoryDto;
import model.Command;
import model.MainDao;
import model.MainDto;

public class GetMainList implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		MainDao dao = new MainDao();
		
		String pageStr = request.getParameter("page");
	    String categoryIdStr = request.getParameter("categoryId");
	    String pageSizeStr = request.getParameter("pageSize");
	    String keyword = request.getParameter("keyword");
	    if (keyword == null) keyword = "";

	    int page = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);
	    int categoryId = (categoryIdStr == null || categoryIdStr.isEmpty()) ? 0 : Integer.parseInt(categoryIdStr);
	    int pageSize = (pageSizeStr == null || pageSizeStr.isEmpty()) ? 4 : Integer.parseInt(pageSizeStr);
		

		int start = (page - 1) * pageSize + 1;
		int end = page * pageSize;
		
		
		List<MainDto> list = dao.getMainList(categoryId, start, end, keyword);
		request.setAttribute("productList", list);
		
		List<CategoryDto> categoryList = dao.getCategoryList();
	    request.setAttribute("categoryList", categoryList);
	}

}
