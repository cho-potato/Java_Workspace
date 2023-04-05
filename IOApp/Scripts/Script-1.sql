CREATE TABLE DEPT(
	DEPTNO NUMBER PRIMARY KEY
	, DNAME VARCHAR2(20)
	, LOC VARCHAR2(30)
);

CREATE SEQUENCE SEQ_DEPT
INCREMENT BY 1
START WITH 1;

CREATE TABLE EMP (
	EMPNO NUMBER PRIMARY KEY
	, ENAME VARCHAR2(20)
	, SAL NUMBER DEFAULT 0
	, JOB VARCHAR2(20)
	, DEPTNO NUMBER
	, CONSTRAINT FK_DEPT_EMP FOREIGN KEY (DEPTNO) REFERENCES DEPT(DEPTNO)
);

CREATE SEQUENCE SEQ_EMP
INCREMENT BY 1
START WITH 1;


-- 회원 테이블
CREATE TABLE MEMBER (
	MEMBER_IDX NUMBER PRIMARY KEY
	, ID VARCHAR2(20)
	, PASS VARCHAR2(64)
	-- SHA 함수에서 SHA-256 함수를 쓰면 64字의 알 수 없는 HASH값으로 고정되는데, 단어가 길든 짧든 64字로 고정된다(TIGER -> 64 / A -> 64)	
	, EMAIL VARCHAR2(50)
	, REGDATE DATE DEFAULT SYSDATE
);

CREATE SEQUENCE SEQ_MEMBER
INCREMENT BY 1
START WITH 1;

CREATE SEQUENCE SEQ_MEMBER
INCREMENT BY 1
START WITH 1;

# 데이터베이스 정보
# 아래와 같이 '='으로 구분되어 있는 파일의 유형을 Properties라 하고,
# 자바의 Collection Frameworks이 제공하는 API객체 중 MAP을 상속받은 Properties라는 객체가
# 아래의 '=' 문자를 기준으로 좌측을 Key로, 우측은 Value로 처리할 수 있다
# 자바의 Properties 객체는 다른 형식에는 관심없고 오직 '='의 쌍으로 구성된 문자열만 해석 가능하다
# 따라서 지금 주석을 달고 있는 #영역은 Properties 객체의 관심영역이 아니다

driver=oracle.jdbc.driver.OracleDriver
url=jdbc:oracle:thin:@localhost:1521:XE
user=javase
pass=1234

--회원테이블
CREATE TABLE diary(
    diary_idx NUMBER PRIMARY KEY
    ,yy NUMBER
    ,mm NUMBER
    ,dd NUMBER
    ,content varchar2(1000)
    ,icon varchar2(20)
);

CREATE SEQUENCE seq_diary
INCREMENT BY 1
START WITH 1;


----------------------------------------------------------------------------------------------
DROP TABLE topcategory; 
DROP TABLE subcategory;
DROP sequence seq_topcategory;
DROP TABLE seq_subcategory;

CREATE TABLE TopCategory (
	topcategory_idx NUMBER PRIMARY KEY
	, topcategory_name varchar2(20)
);

CREATE SEQUENCE seq_topcategory
INCREMENT BY 1
START WITH 1;

--------------------------------- 제약조건 부여하기 
CREATE TABLE subcategory (
	subcategory_idx NUMBER PRIMARY KEY
	, topcategory_idx NUMBER -- 부모의 PRIMARY KEY
	, subcategory_name varchar2(20)
	, CONSTRAINT fk_topcategory_subcategory FOREIGN KEY (topcategory_idx) REFERENCES TopCategory(topcategory_idx)
	);
	
CREATE SEQUENCE seq_subcategory
INCREMENT BY 1
START WITH 1;

-- 상위 카테고리 등록
INSERT INTO topcategory(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '상의');

INSERT INTO topcategory(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '하의');

INSERT INTO topcategory(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '액세서리');

INSERT INTO topcategory(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '신발');

INSERT INTO topcategory(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '모자');

SELECT * FROM topcategory;

------------------------------------하위 카테고리 등록------------------------------------------

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 1, '티셔츠');

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 1, '가디건');

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 1, '니트');

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 1, '점퍼');

---------------------------------------------------------------------------------------------

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 2, '면바지');

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 2, '반바지');

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 2, '청바지');

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 2, '레깅스');

---------------------------------------------------------------------------------------------

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 3, '귀걸이');

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 3, '목걸이');

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 3, '팔찌');

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 3, '반지');

---------------------------------------------------------------------------------------------

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 4, '운동화');

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 4, '구두');

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 4, '샌들');

INSERT INTO subcategory(subcategory_idx, topcategory_idx, SUBCATEGORY_name)
VALUES(seq_subcategory.nextval, 4, '슬리퍼');

SELECT * FROM subcategory;

-- 부모를 죽여보자(부모제한)
DELETE FROM topcategory;
-- 부모에 존재하는 프라이머리 키만 넣을 수 있음(자식제한)
SELECT * FROM topcategory;
SELECT * FROM subcategory;

-- 상품 테이블 만들기 
CREATE TABLE prodct(
	product_idx NUMBER PRIMARY KEY
	, subcategory_idx NUMBER
	, product_name varchar2(30)
	, brand varchar2(30)
	, price NUMBER default 0
	, filename varchar2(20)
	, CONSTRAINT fk_subcategory_product FOREIGN KEY (subcategory_idx) REFERENCES subcategory(subcategory_idx)
);

CREATE SEQUENCE seq_product
INCREMENT BY 1
START WITH 1;

select s.subcategory_idx AS subcategory_idx, subcategory_name, product_idx, product_name, brand, price, filename
from subcategory s , product p 
where s.subcategory_idx = p.subcategory_idx;

-- 3개 테이블 조인

select t.topcategory_idx, topcategory_name
	, s.subcategory_idx, subcategory_name
	, product_idx, product_name, brand, price, filename
from topcategory t, subcategory s, product p
where t.topcategory_idx = s.topcategory_idx 
and s.subcategory_idx = p.subcategory_idx
and product_idx =2;

select s.subcategory_idx as subcategody_idx 
, subcategory_name
, product_idx, product_name, brand, price, filename
from topcategory t, subcategory s, product p
where t.topcategory_idx = s.topcategory_idx
and s.subcategory_idx = p.subcategory_idx
and topcategory_name='상의';
