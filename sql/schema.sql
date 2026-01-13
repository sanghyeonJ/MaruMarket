DROP TABLE product_like CASCADE CONSTRAINTS;
DROP TABLE product_file CASCADE CONSTRAINTS;
DROP TABLE product CASCADE CONSTRAINTS;
DROP TABLE file_info CASCADE CONSTRAINTS;
DROP TABLE category CASCADE CONSTRAINTS;
DROP TABLE member CASCADE CONSTRAINTS;

DROP SEQUENCE member_seq;
DROP SEQUENCE category_seq;
DROP SEQUENCE file_info_seq;
DROP SEQUENCE product_seq;
DROP SEQUENCE product_file_seq;

CREATE TABLE member (
  member_no NUMBER PRIMARY KEY,
  user_id VARCHAR2(50) NOT NULL UNIQUE,
  user_pw VARCHAR2(255) NOT NULL,
  user_name VARCHAR2(50) NOT NULL,
  email VARCHAR2(100),
  is_deleted CHAR(1) DEFAULT 'N' CHECK (is_deleted IN ('Y','N')),
  is_admin CHAR(1) DEFAULT 'N' CHECK (is_admin IN ('Y','N')),
  user_key VARCHAR2(64) NOT NULL UNIQUE,
  regdate DATE DEFAULT SYSDATE
);
CREATE SEQUENCE member_seq
START WITH 1
INCREMENT BY 1
NOCACHE;


CREATE TABLE category (
  category_id NUMBER PRIMARY KEY,
  category_name VARCHAR2(50) NOT NULL UNIQUE,
  is_use CHAR(1) DEFAULT 'Y' CHECK (is_use IN ('Y','N'))
);
CREATE SEQUENCE category_seq
START WITH 1
INCREMENT BY 1
NOCACHE;
INSERT INTO category (category_id, category_name, is_use)
VALUES (category_seq.NEXTVAL, '電子機器', 'Y');

INSERT INTO category (category_id, category_name, is_use)
VALUES (category_seq.NEXTVAL, '衣類', 'Y');

INSERT INTO category (category_id, category_name, is_use)
VALUES (category_seq.NEXTVAL, '本・雑誌', 'Y');

INSERT INTO category (category_id, category_name, is_use)
VALUES (category_seq.NEXTVAL, '生活用品', 'Y');

INSERT INTO category (category_id, category_name, is_use)
VALUES (category_seq.NEXTVAL, '家具・インテリア', 'Y');

INSERT INTO category (category_id, category_name, is_use)
VALUES (category_seq.NEXTVAL, 'スポーツ・レジャー', 'Y');

INSERT INTO category (category_id, category_name, is_use)
VALUES (category_seq.NEXTVAL, 'おもちゃ・ホビー', 'Y');

INSERT INTO category (category_id, category_name, is_use)
VALUES (category_seq.NEXTVAL, 'その他', 'Y');

commit;


CREATE TABLE file_info (
  file_id NUMBER PRIMARY KEY,
  origin_name VARCHAR2(255) NOT NULL,
  save_name VARCHAR2(255) NOT NULL,
  file_path VARCHAR2(500) NOT NULL,
  file_size NUMBER,
  regdate DATE DEFAULT SYSDATE
);
CREATE SEQUENCE file_info_seq
START WITH 1
INCREMENT BY 1
NOCACHE;




CREATE TABLE product (
  product_id NUMBER PRIMARY KEY,
  seller_no NUMBER NOT NULL,
  title VARCHAR2(200) NOT NULL,
  content CLOB NOT NULL,
  category_id NUMBER NOT NULL,
  status VARCHAR2(20) DEFAULT 'SELL'
    CHECK (status IN ('SELL','RESERVE','SOLD')),
  price NUMBER NOT NULL,
  buyer_no NUMBER,
  regdate DATE DEFAULT SYSDATE,
  view_count NUMBER DEFAULT 0 NOT NULL,

  CONSTRAINT fk_product_seller
    FOREIGN KEY (seller_no) REFERENCES member(member_no),

  CONSTRAINT fk_product_buyer
    FOREIGN KEY (buyer_no) REFERENCES member(member_no),

  CONSTRAINT fk_product_category
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);
CREATE SEQUENCE product_seq
START WITH 1
INCREMENT BY 1
NOCACHE;


CREATE TABLE product_file (
  product_file_id NUMBER PRIMARY KEY,
  product_id NUMBER NOT NULL,
  file_id NUMBER NOT NULL,

  file_type VARCHAR2(20) NOT NULL
    CHECK (file_type IN ('MAIN','DETAIL')),

  sort_order NUMBER DEFAULT 1,

  CONSTRAINT fk_pf_product
    FOREIGN KEY (product_id)
    REFERENCES product(product_id)
    ON DELETE CASCADE,

  CONSTRAINT fk_pf_file
    FOREIGN KEY (file_id)
    REFERENCES file_info(file_id)
    ON DELETE CASCADE
);
CREATE SEQUENCE product_file_seq
START WITH 1
INCREMENT BY 1
NOCACHE;


CREATE TABLE product_like (
    member_no NUMBER NOT NULL,
    product_id NUMBER NOT NULL,
    regdate DATE DEFAULT SYSDATE,
    
    -- PK 설정: 한 유저가 한 상품에 대해 중복 찜 방지
    CONSTRAINT pk_product_like PRIMARY KEY (member_no, product_id),
    
    -- 외래키 설정: 유저나 상품이 삭제되면 찜 기록도 삭제 (CASCADE)
    CONSTRAINT fk_like_member FOREIGN KEY (member_no) 
        REFERENCES member(member_no) ON DELETE CASCADE,
    CONSTRAINT fk_like_product FOREIGN KEY (product_id) 
        REFERENCES product(product_id) ON DELETE CASCADE
);

