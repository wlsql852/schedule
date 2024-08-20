CREATE TABLE IF NOT EXISTS MANAGER (
    managerId bigint primary key auto_increment comment '담당자아이디',
    name varchar(100) comment '담당자 이름',
    email varchar(100) comment '이메일',
    createDate datetime comment '생성날짜',
    updateDate datetime comment '수정날짜'
    );

CREATE TABLE IF NOT EXISTS SCHEDULE (
    scheduleId bigint primary key auto_increment comment '고유식별번호',
    todo varchar(100) comment '할 일',
    managerId bigint comment '담당자아이디',
    password varchar(100) comment '비밀번호',
    createDate datetime comment '생성날짜',
    updateDate datetime comment '수정날짜',
    CONSTRAINT foreign key (managerId) references MANAGER(managerId) ON DELETE CASCADE
    );

ALTER TABLE MANAGER MODIFY managerId  bigint AUTO_INCREMENT;
ALTER TABLE SCHEDULE MODIFY scheduleId  bigint AUTO_INCREMENT;