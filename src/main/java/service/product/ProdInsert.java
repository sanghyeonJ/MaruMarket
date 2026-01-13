package service.product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.Command;
import model.FileDto;
import model.ProductDao;
import model.ProductDto;

public class ProdInsert implements Command {

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        /* 1️⃣ 로그인 체크 */
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("member_no") == null) {
            response.sendRedirect("/mem/login.do");
            return;
        }

        int sellerNo = (int) session.getAttribute("member_no");

        /* 2️⃣ 상품 정보 */
        ProductDto product = new ProductDto();
        product.setSellerNo(sellerNo);
        product.setTitle(request.getParameter("title"));
        product.setContent(request.getParameter("content"));
        product.setCategoryId(Integer.parseInt(request.getParameter("category_id")));
        product.setPrice(Integer.parseInt(request.getParameter("price")));

        /* 3️⃣ 대표 이미지 */
        Part mainPart = request.getPart("mainImage");
        FileDto mainFile = uploadFile(mainPart);

        /* 4️⃣ 상세 이미지 */
        List<FileDto> detailFiles = new ArrayList<>();
        for (Part part : request.getParts()) {
            if ("detailImages".equals(part.getName()) && part.getSize() > 0) {
                detailFiles.add(uploadFile(part));
            }
        }

        /* 5️⃣ DAO 호출 */
        ProductDao dao = new ProductDao();
        dao.insertProduct(product, mainFile, detailFiles);

        /* 6️⃣ 이동 */
        response.sendRedirect("/main");
    }

    /* 📌 파일 업로드 공통 메서드 */
    private FileDto uploadFile(Part part) throws IOException {
        String originName = part.getSubmittedFileName();
        String saveName = UUID.randomUUID().toString() + "_" + originName;
        String uploadPath = System.getProperty("user.home") + "/upload/maru";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        part.write(uploadPath + File.separator + saveName);

        FileDto dto = new FileDto();
        dto.setOriginName(originName);
        dto.setSaveName(saveName);
        dto.setFilePath(uploadPath);
        dto.setFileSize(part.getSize());

        return dto;
    }
}
