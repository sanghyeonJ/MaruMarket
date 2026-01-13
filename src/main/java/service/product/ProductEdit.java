package service.product;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Command;
import model.ProductDao;
import model.ProductDto;

public class ProductEdit implements Command {
    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        ProductDao dao = new ProductDao();
        
        String savePath = request.getServletContext().getRealPath("/upload/maru");
        File uploadDir = new File(savePath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        int productId = Integer.parseInt(request.getParameter("productId"));
        
        // [기본 정보 업데이트]
        ProductDto dto = new ProductDto();
        dto.setProductId(productId);
        dto.setTitle(request.getParameter("title"));
        dto.setContent(request.getParameter("content"));
        dto.setPrice(Integer.parseInt(request.getParameter("price")));
        dto.setCategoryId(Integer.parseInt(request.getParameter("category_id")));
        dto.setStatus(request.getParameter("status"));
        dao.updateProduct(dto);

        // [파일 처리 시작]
        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            if (part.getSize() > 0) {
                // 1. 대표 이미지 처리
                if (part.getName().equals("mainImage")) {
                    String originName = part.getSubmittedFileName();
                    String saveName = UUID.randomUUID().toString() + "_" + originName;
                    part.write(savePath + File.separator + saveName);
                    dao.updateProductMainImage(productId, originName, saveName, savePath);
                } 
                // 2. 상세 이미지 처리 (여러 장 가능)
                else if (part.getName().equals("detailImages")) {
                    String originName = part.getSubmittedFileName();
                    String saveName = UUID.randomUUID().toString() + "_" + originName;
                    part.write(savePath + File.separator + saveName);
                    // 위에서 만든 상세 이미지 저장 메서드 호출
                    dao.insertProductDetailImage(productId, originName, saveName, savePath);
                }
            }
        }

        response.sendRedirect("/prod/detail.do?productId=" + productId);
    }
}