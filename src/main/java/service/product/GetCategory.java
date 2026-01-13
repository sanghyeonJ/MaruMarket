package service.product;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CategoryDto;
import model.Command;
import model.ProductDao;

public class GetCategory implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		ProductDao dao = new ProductDao();
		List<CategoryDto> list = dao.getCategory();
		
		request.setAttribute("categoryList", list);
	}

}
