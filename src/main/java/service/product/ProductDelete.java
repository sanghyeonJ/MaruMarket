package service.product;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Command;
import model.ProductDao;

public class ProductDelete implements Command {

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        
        String pidStr = request.getParameter("productId");
        if (pidStr == null || pidStr.isEmpty()) {
            response.getWriter().print("{\"result\": \"fail\"}");
            return;
        }

        int productId = Integer.parseInt(pidStr);
        HttpSession session = request.getSession();
        Object memberNoObj = session.getAttribute("member_no");
        
        if (memberNoObj == null) {
            response.getWriter().print("{\"result\": \"fail\"}");
            return;
        }

        int loginUserNo = (Integer) memberNoObj;
        ProductDao dao = new ProductDao();
        
        // 작성자 본인인지 확인 후 삭제
        boolean deleted = dao.deleteProduct(productId, loginUserNo);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        if (deleted) {
            response.getWriter().print("{\"result\": \"success\"}");
        } else {
            response.getWriter().print("{\"result\": \"fail\"}");
        }
    }
}