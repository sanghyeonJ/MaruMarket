package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.product.GetCategory;
import service.product.ProdInsert;
import service.product.ProductDelete;
import service.product.ProductEdit;
import service.product.ToggleLike;
import service.product.productDetail;

@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 2, // 2MB 메모리 또는 임시폴더에 잠깐저장 
		maxFileSize = 1024 * 1024* 10, // 10MB 파일 1개당 최대크기
		maxRequestSize =  1024 * 1024 * 50 // 50MB 폼 전체 합산크기 파일 여러개 + 텍스트까지 합쳐서 50MB 까지 허용
		)
@WebServlet("/prod/*")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		String uri = request.getPathInfo();
		
		String page = null;
		
		switch(uri) {
		case "/insert.do":
			new GetCategory().doCommand(request, response);
			page = "/product/insert.jsp";
			break;
		case "/insertAction.do":
			new ProdInsert().doCommand(request, response);
			break;
		case "/detail.do":
		    new productDetail().doCommand(request, response);
		    page = "/product/detail.jsp";
		    break;
		case "/toggleLike.do":
		    new ToggleLike().doCommand(request, response);
		    break;
		case "/delete.do":
            new ProductDelete().doCommand(request, response);
            break;
		case "/edit.do":
		    new GetCategory().doCommand(request, response);
		    new productDetail().doCommand(request, response);
		    page = "/product/edit.jsp";
		    break;
		case "/editAction.do":
		    new ProductEdit().doCommand(request, response);
		    break;
		default:
			System.out.println("間違ったリクエストです。");
			break;
		}
		
		if(page != null) {
			RequestDispatcher rs = request.getRequestDispatcher(page);
			rs.forward(request, response);
		}
		
	}

}
