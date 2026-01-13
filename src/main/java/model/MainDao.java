package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class MainDao {


    public List<MainDto> getMainList(int categoryId, int start, int end, String keyword) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        List<MainDto> list = new ArrayList<MainDto>();

        String whereClause = " WHERE pf.file_type = 'MAIN' ";
        if (categoryId != 0) {
            whereClause += " AND p.category_id = ? ";
        }
        if (!keyword.isEmpty()) {
            whereClause += " AND p.title LIKE ? ";
        }
        
        String sql = 
                "SELECT * FROM ( " +
                "  SELECT p.product_id, p.title, p.price, p.status, f.save_name, " +
                "         ROW_NUMBER() OVER (ORDER BY p.regdate DESC) rn " +
                "  FROM product p " +
                "  JOIN product_file pf ON p.product_id = pf.product_id " +
                "  JOIN file_info f ON pf.file_id = f.file_id " +
                whereClause + 
                ") WHERE rn BETWEEN ? AND ?";

        try {
            conn = DBManager.getInstance();
            pstmt = conn.prepareStatement(sql);
            
            int paramIdx = 1;
            if (categoryId != 0) pstmt.setInt(paramIdx++, categoryId);
            if (!keyword.isEmpty()) pstmt.setString(paramIdx++, "%" + keyword + "%");
            
            pstmt.setInt(paramIdx++, start);
            pstmt.setInt(paramIdx++, end);

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
            try {
            	if(rs != null) rs.close();
            	if(pstmt != null) pstmt.close();
            	if(conn != null) conn.close();
            }catch(Exception e) {
            	e.printStackTrace();
            }
        }

        return list;
    }
    
    public List<CategoryDto> getCategoryList() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        List<CategoryDto> list = new ArrayList<>();
        String sql = "SELECT category_id, category_name FROM category WHERE is_use = 'Y' ORDER BY category_id ASC";
        
        try {
            conn = DBManager.getInstance();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                CategoryDto dto = new CategoryDto();
                dto.setCategoryId(rs.getInt("category_id"));
                dto.setCategoryName(rs.getString("category_name"));
                list.add(dto);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
