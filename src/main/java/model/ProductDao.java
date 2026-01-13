package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class ProductDao {

    public List<CategoryDto> getCategory() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM category WHERE is_use = 'Y'";
        List<CategoryDto> list = new ArrayList<CategoryDto>();

        try {
            conn = DBManager.getInstance();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CategoryDto dto = new CategoryDto();
                dto.setCategoryId(rs.getInt("category_id"));
                dto.setCategoryName(rs.getString("category_name"));
                dto.setIsUse(rs.getString("is_use"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void insertProduct(ProductDto product, FileDto mainFile, List<FileDto> detailFiles) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBManager.getInstance();
            conn.setAutoCommit(false);
            /* 1️⃣ 상품 등록 */
            String productSql = "INSERT INTO product ( "
                    + "product_id, seller_no, title, content, category_id, status, price " + ") VALUES ( "
                    + "product_seq.NEXTVAL, ?, ?, ?, ?, 'SELL', ? )";

            pstmt = conn.prepareStatement(productSql);
            pstmt.setInt(1, product.getSellerNo());
            pstmt.setString(2, product.getTitle());
            pstmt.setString(3, product.getContent());
            pstmt.setInt(4, product.getCategoryId());
            pstmt.setInt(5, product.getPrice());
            pstmt.executeUpdate();
            pstmt.close();

            /* 2️⃣ 대표 이미지 파일 등록 */
            String fileSql = "INSERT INTO file_info ( " + "file_id, origin_name, save_name, file_path, file_size "
                    + ") VALUES ( " + "file_info_seq.NEXTVAL, ?, ?, ?, ? )";

            pstmt = conn.prepareStatement(fileSql);
            pstmt.setString(1, mainFile.getOriginName());
            pstmt.setString(2, mainFile.getSaveName());
            pstmt.setString(3, mainFile.getFilePath());
            pstmt.setLong(4, mainFile.getFileSize());
            pstmt.executeUpdate();
            pstmt.close();

            /* 3️⃣ 상품-파일 연결 (대표 이미지) */
            String pfSql = "INSERT INTO product_file ( "
                    + "product_file_id, product_id, file_id, file_type, sort_order " + ") VALUES ( "
                    + "product_file_seq.NEXTVAL, product_seq.CURRVAL, file_seq.CURRVAL, 'MAIN', 1 )";

            pstmt = conn.prepareStatement(pfSql);
            pstmt.executeUpdate();
            pstmt.close();

            /* 4️⃣ 상세 이미지 처리 */
            if (detailFiles != null) {
                int order = 1;
                for (FileDto file : detailFiles) {

                    // file_info insert
                    pstmt = conn.prepareStatement(fileSql);
                    pstmt.setString(1, file.getOriginName());
                    pstmt.setString(2, file.getSaveName());
                    pstmt.setString(3, file.getFilePath());
                    pstmt.setLong(4, file.getFileSize());
                    pstmt.executeUpdate();
                    pstmt.close();

                    // product_file insert
                    pstmt = conn.prepareStatement("INSERT INTO product_file ( "
                            + "product_file_id, product_id, file_id, file_type, sort_order " + ") VALUES ( "
                            + "product_file_seq.NEXTVAL, product_seq.CURRVAL, file_seq.CURRVAL, 'DETAIL', ? )");
                    pstmt.setInt(1, order++);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }

            conn.commit(); // ✅ 커밋

        } catch (Exception e) {
            try {
                if (conn != null)
                    conn.rollback(); // ❌ 실패 시 롤백
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();

        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateViewCount(int productId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "UPDATE product SET view_count = view_count + 1 WHERE product_id = ?";

        try {
            conn = DBManager.getInstance();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, productId);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ProductDto productDetail(int productId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ProductDto dto = null;

        try {
            conn = DBManager.getInstance();

            String sql = "SELECT p.*, m.user_id, c.category_name " + "FROM product p "
                    + "JOIN member m ON p.seller_no = m.member_no "
                    + "JOIN category c ON p.category_id = c.category_id " + "WHERE p.product_id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = new ProductDto();
                dto.setProductId(rs.getInt("product_id"));
                dto.setSellerNo(rs.getInt("seller_no"));
                dto.setSellerId(rs.getString("user_id")); // 판매자 아이디 추가
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setCategoryId(rs.getInt("category_id"));
                dto.setCategoryName(rs.getString("category_name")); // 카테고리명 추가
                dto.setStatus(rs.getString("status"));
                dto.setPrice(rs.getInt("price"));
                dto.setBuyerNo(rs.getInt("buyer_no"));
                dto.setRegdate(rs.getTimestamp("regdate"));
                dto.setViewCount(rs.getInt("view_count"));
            }

            // [2] 이미지 파일 목록 조회 (product_file + file_info JOIN)
            if (dto != null) {
                String imgSql = "SELECT f.save_name " + "FROM product_file pf "
                        + "JOIN file_info f ON pf.file_id = f.file_id " + "WHERE pf.product_id = ? "
                        + "ORDER BY pf.file_type DESC, pf.product_file_id ASC";

                pstmt.close();
                pstmt = conn.prepareStatement(imgSql);
                pstmt.setInt(1, productId);
                rs.close();
                rs = pstmt.executeQuery();

                List<String> imgList = new ArrayList<>();
                while (rs.next()) {
                    imgList.add(rs.getString("save_name"));
                }
                dto.setImages(imgList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dto;
    }

    // 찜 토글 (추가/삭제)
    public int toggleLike(int productId, int memberNo) {
        String checkSql = "SELECT COUNT(*) FROM product_like WHERE product_id = ? AND member_no = ?";
        String insertSql = "INSERT INTO product_like (product_id, member_no) VALUES (?, ?)";
        String deleteSql = "DELETE FROM product_like WHERE product_id = ? AND member_no = ?";

        try (Connection conn = DBManager.getInstance();
                PreparedStatement pstmtCheck = conn.prepareStatement(checkSql)) {

            pstmtCheck.setInt(1, productId);
            pstmtCheck.setInt(2, memberNo);
            ResultSet rs = pstmtCheck.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // 이미 찜이 되어있으면 삭제
                try (PreparedStatement pstmtDel = conn.prepareStatement(deleteSql)) {
                    pstmtDel.setInt(1, productId);
                    pstmtDel.setInt(2, memberNo);
                    pstmtDel.executeUpdate();
                    return 0; // 찜 취소됨
                }
            } else {
                // 찜이 안되어있으면 추가
                try (PreparedStatement pstmtIns = conn.prepareStatement(insertSql)) {
                    pstmtIns.setInt(1, productId);
                    pstmtIns.setInt(2, memberNo);
                    pstmtIns.executeUpdate();
                    return 1; // 찜 성공
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 상세페이지 로딩 시 현재 유저가 찜했는지 확인
    public boolean isLiked(int productId, int memberNo) {
        String sql = "SELECT COUNT(*) FROM product_like WHERE product_id = ? AND member_no = ?";
        try (Connection conn = DBManager.getInstance(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, memberNo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 상품의 찜 수 조회
    public int getLikeCount(int productId) {
        String sql = "SELECT COUNT(*) FROM product_like WHERE product_id = ?";
        try (Connection conn = DBManager.getInstance(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean deleteProduct(int productId, int memberNo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBManager.getInstance();

            // 작성자 본인인지 확인
            String checkSql = "SELECT seller_no FROM product WHERE product_id = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next() || rs.getInt("seller_no") != memberNo) {
                // 작성자가 아니거나 상품이 없으면 삭제 불가
                return false;
            }
            rs.close();
            pstmt.close();

            // 상품 삭제 (CASCADE로 관련 데이터도 삭제됨)
            String deleteSql = "DELETE FROM product WHERE product_id = ?";
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, productId);
            int result = pstmt.executeUpdate();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 1. 기본 정보 수정
    public boolean updateProduct(ProductDto dto) {
        String sql = "UPDATE product SET title=?, content=?, price=?, category_id=?, status=? WHERE product_id=?";
        try (Connection conn = DBManager.getInstance(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContent());
            pstmt.setInt(3, dto.getPrice());
            pstmt.setInt(4, dto.getCategoryId());
            pstmt.setString(5, dto.getStatus());
            pstmt.setInt(6, dto.getProductId());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. 대표 이미지 교체 (기존 메인 파일 연결 끊고 새 파일 등록)
    public void updateProductMainImage(int productId, String originName, String saveName, String path) {
        Connection conn = null;
        try {
            conn = DBManager.getInstance();
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 기존 MAIN 이미지 관계 삭제 (파일 정보는 남겨두더라도 관계는 끊음)
            String delSql = "DELETE FROM product_file WHERE product_id = ? AND file_type = 'MAIN'";
            PreparedStatement pstmt1 = conn.prepareStatement(delSql);
            pstmt1.setInt(1, productId);
            pstmt1.executeUpdate();

            // file_info에 새 파일 저장
            String insFileSql = "INSERT INTO file_info (file_id, origin_name, save_name, file_path) VALUES (file_info_seq.NEXTVAL, ?, ?, ?)";
            PreparedStatement pstmt2 = conn.prepareStatement(insFileSql, new String[] { "FILE_ID" });
            pstmt2.setString(1, originName);
            pstmt2.setString(2, saveName);
            pstmt2.setString(3, path);
            pstmt2.executeUpdate();

            ResultSet rs = pstmt2.getGeneratedKeys();
            if (rs.next()) {
                int fileId = rs.getInt(1);
                // product_file에 연결
                String insPfSql = "INSERT INTO product_file (product_file_id, product_id, file_id, file_type) VALUES (product_file_seq.NEXTVAL, ?, ?, 'MAIN')";
                PreparedStatement pstmt3 = conn.prepareStatement(insPfSql);
                pstmt3.setInt(1, productId);
                pstmt3.setInt(2, fileId);
                pstmt3.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception e1) {
            }
            e.printStackTrace();
        }
    }

    // 3. 상세 이미지 추가
    // 상세 이미지 개별 추가 (ProductDao에 추가)
    public void insertProductDetailImage(int productId, String originName, String saveName, String path) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getInstance();

            // 1. file_info 테이블에 파일 정보 저장
            String insFileSql = "INSERT INTO file_info (file_id, origin_name, save_name, file_path) VALUES (file_info_seq.NEXTVAL, ?, ?, ?)";
            pstmt = conn.prepareStatement(insFileSql, new String[] { "FILE_ID" });
            pstmt.setString(1, originName);
            pstmt.setString(2, saveName);
            pstmt.setString(3, path);
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int fileId = rs.getInt(1);

                // 2. product_file 테이블에 연결 (DETAIL 타입으로)
                String insPfSql = "INSERT INTO product_file (product_file_id, product_id, file_id, file_type) VALUES (product_file_seq.NEXTVAL, ?, ?, 'DETAIL')";
                pstmt = conn.prepareStatement(insPfSql);
                pstmt.setInt(1, productId);
                pstmt.setInt(2, fileId);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
