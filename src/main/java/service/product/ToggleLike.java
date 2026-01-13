package service.product;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Command;
import model.ProductDao;

public class ToggleLike implements Command {

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        
        // 1. 세션에서 member_no 가져오기
        HttpSession session = request.getSession();
        Object memberNoObj = session.getAttribute("member_no");
        
        // 로그인이 안 되어 있으면 -1 반환
        if (memberNoObj == null) {
            response.setContentType("application/json");
            response.getWriter().print("{\"result\": -1}");
            return;
        }

        // member_no는 Integer로 저장되어 있음
        int memberNo = (Integer) memberNoObj;

        // 2. 파라미터 받기
        String pidStr = request.getParameter("productId");
        if (pidStr == null) return;
        
        int productId = Integer.parseInt(pidStr);

        // 3. DAO 호출
        ProductDao dao = new ProductDao();
        int result = dao.toggleLike(productId, memberNo); 

        // 4. JSON 형식으로 응답 보내기
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print("{\"result\": " + result + "}");
    }
}