package service.product;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Command;
import model.ProductDao;
import model.ProductDto;

public class productDetail implements Command {

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        
        String pidStr = request.getParameter("productId");
        
        if (pidStr == null || pidStr.isEmpty()) {
            response.sendRedirect("productList.do");
            return;
        }

        int productId = Integer.parseInt(pidStr);
        ProductDao dao = new ProductDao();
        
        dao.updateViewCount(productId);
        
        ProductDto dto = dao.productDetail(productId);
        
        if (dto != null) {
            request.setAttribute("product", dto);
        } else {
            response.sendRedirect("/main");
            return;
        }
        
        int likeCount = dao.getLikeCount(productId);
        request.setAttribute("likeCount", likeCount);
        
        HttpSession session = request.getSession();
        Object memberNoObj = session.getAttribute("member_no");
        boolean isLiked = false;
        boolean isOwner = false; // 작성자 본인 여부

        if (memberNoObj != null) {
            int loginUserNo = (Integer) memberNoObj;
            isLiked = dao.isLiked(productId, loginUserNo);
            // 작성자 본인인지 확인
            isOwner = (dto.getSellerNo() == loginUserNo);
        }
        request.setAttribute("isLiked", isLiked);
        request.setAttribute("isOwner", isOwner);
    }
}