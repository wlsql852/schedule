CREATE TABLE IF NOT EXISTS SCHEDULE (
    scheduleId bigint primary key auto_increment comment '고유식별번호',
    work varchar(100) comment '할 일',
    password varchar(100) comment '비밀번호',
    creatDate datetime comment '생성날짜',
    updateDate datetime comment '수정날짜'
    );