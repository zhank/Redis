CREATE TABLE TB_USER(
	USER_ID INTEGER NOT NULL PRIMARY KEY,
	USER_NAME VARCHAR2(48),
	USER_PWD  VARCHAR2(96),
	USER_PHONE VARCHAR2(48),
	USER_AGE INTEGER
);

CREATE SEQUENCE GEN_TB_USER INCREMENT BY 1 START WITH 1
    NOMAXVALUE NOCYCLE CACHE 10;

CREATE OR REPLACE TRIGGER TR_COMMON_ADMIN_INFO
    BEFORE INSERT ON TB_USER FOR EACH ROW
BEGIN
    SELECT GEN_TB_USER.NEXTVAL INTO :NEW.USER_ID FROM DUAL;
END;

--测试数据--

insert into TB_USER (User_Name, USER_PWD,USER_PHONE,USER_AGE) values ('张三','123','10086', 20);
insert into TB_USER (User_Name, USER_PWD,USER_PHONE,USER_AGE) values ('李四','456','10087', 21);
insert into TB_USER (User_Name, USER_PWD,USER_PHONE,USER_AGE) values ('王五','789','10088', 22);
insert into TB_USER (User_Name, USER_PWD,USER_PHONE,USER_AGE) values ('赵六','100','10089', 23);
