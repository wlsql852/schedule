CREATE TABLE IF NOT EXISTS SCHEDULE (
                                        scheduleId varchar(100) primary key comment '고유식별번호',
    work varchar(100) comment '할 일',
    password varchar(100) comment '비밀번호',
    creatDate date comment '생성날짜',
    updateDate date comment '수정날짜'
    );