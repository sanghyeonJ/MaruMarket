package service.main;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Command;
import model.MainDto;
import model.MemberDto;
import model.MypageDao;

public class MypageList implements Command {

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        MypageDao dao = new MypageDao();
        HttpSession session = request.getSession();
        
        // 1. 로그인 유저 확인
        MemberDto user = (MemberDto) session.getAttribute("loginUser");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/mem/login.do");
            return;
        }

        // 2. 파라미터 수집 (Main 페이지 로직과 동일)
        String pageStr = request.getParameter("page");
        String pageSizeStr = request.getParameter("pageSize");
        String keyword = request.getParameter("keyword");
        if (keyword == null) keyword = "";

        int page = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);
        int pageSize = (pageSizeStr == null || pageSizeStr.isEmpty()) ? 4 : Integer.parseInt(pageSizeStr);

        int start = (page - 1) * pageSize + 1;
        int end = page * pageSize;

        // 3. 현재 메뉴 타입 결정 (URL 주소 기준)
        String uri = request.getPathInfo();
        String type = "LIKE";
        if (uri.contains("buy")) type = "BUY";
        else if (uri.contains("sell")) type = "SELL";

        // 4. 데이터 조회 (아까 만든 통합 메서드 호출)
        List<MainDto> list = dao.getMyProductList(type, user.getMember_no(), start, end);
        request.setAttribute("productList", list);
        request.setAttribute("menuType", type); // JSP 제목 처리용

    }
}