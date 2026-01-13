package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class MypageDao {
    public List<MainDto> getMyProductList(String type, int memberNo, int start, int end) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MainDto> list = new ArrayList<MainDto>();

        // 기본 SQL 구조 (메인 이미지 Join 포함)
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ( ");
        sql.append("  SELECT p.product_id, p.title, p.price, p.status, f.save_name, ");
        sql.append("         ROW_NUMBER() OVER (ORDER BY ");
        
        // 찜 목록일 경우 찜한 날짜순, 나머지는 상품 등록일순 정렬
        if ("LIKE".equals(type)) sql.append("pl.regdate DESC) rn ");
        else sql.append("p.regdate DESC) rn ");
        
        sql.append("  FROM product p ");
        sql.append("  JOIN product_file pf ON p.product_id = pf.product_id ");
        sql.append("  JOIN file_info f ON pf.file_id = f.file_id ");

        // 타입에 따른 Join 및 조건문 분기
        if ("LIKE".equals(type)) {
            sql.append("  JOIN product_like pl ON p.product_id = pl.product_id ");
            sql.append("  WHERE pl.member_no = ? AND pf.file_type = 'MAIN' ");
        } else if ("SELL".equals(type)) {
            sql.append("  WHERE p.seller_no = ? AND pf.file_type = 'MAIN' ");
        } else if ("BUY".equals(type)) {
            sql.append("  WHERE p.buyer_no = ? AND pf.file_type = 'MAIN' ");
        }

        sql.append(") WHERE rn BETWEEN ? AND ?");

        try {
            conn = DBManager.getInstance();
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setInt(1, memberNo);
            pstmt.setInt(2, start);
            pstmt.setInt(3, end);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                MainDto dto = new MainDto();
                dto.setProductId(rs.getInt("product_id"));
                dto.setTitle(rs.getString("title"));
                dto.setPrice(rs.getInt("price"));
                dto.setStatus(rs.getString("status"));
                dto.setMainImage(rs.getString("save_name"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 기존에 사용하시던 자원 해제 로직
            try { if(rs != null) rs.close(); if(pstmt != null) pstmt.close(); if(conn != null) conn.close(); } catch(Exception e) {}
        }
        return list;
    }
}
