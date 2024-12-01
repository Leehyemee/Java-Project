-- 기차 테이블
CREATE TABLE train (
    train_num NUMBER(10) PRIMARY KEY, -- 기차번호
    train_type VARCHAR2(100) NOT NULL, -- ktx/srt, 새마을호 구분
    str_station VARCHAR2(10), -- 출발역
    arr_station VARCHAR2(10) -- 도착역
);

-- 기차 정보 테이블
CREATE TABLE schedule (
    schedule_id NUMBER(10) PRIMARY KEY, -- 일정고유번호 : 시퀀스 사용
    train_num NUMBER(10), -- 기차번호 (FOREIGN KEY로 train 테이블과 연결)
    start_day TIMESTAMP NOT NULL, -- 출발일
    arrive_day TIMESTAMP NOT NULL, -- 도착일
    money NUMBER(10) NOT NULL, -- 요금
    -- special_seat NUMBER(3), -- 특실 좌석 개수 >> 없어도 됨
    economy_seat NUMBER(3), -- 일반석 좌석 개수 >> 1인, 내측, 외측
    baby_seat NUMBER(3), -- 유아석 좌석 개수
    stand_seat CHAR(1) CHECK (stand_seat IN ('Y', 'N')), -- 입석 좌석 유무
    FOREIGN KEY (train_num) REFERENCES train(train_num)
);

-- 기차 일정 고유 번호 시퀀스
CREATE SEQUENCE schedule_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;


-- 좌석 수 테이블
create table seatinfo(--일반석의 경우
    train_num number(10),-- 기차 번호
    schedule_id NUMBER(10),
    one_seat_count number(10),-- 1인실 좌석 수
    inside_seat_count number(10), -- 내측 좌석 수
    outside_seat_count number(10), -- 외측 좌석 수
    -- seat_category varchar2(10), -- 좌석 카테고리 (예: 특실, 일반석 등) >> 삭제 전부 일반석
    FOREIGN KEY (train_num) REFERENCES train(train_num), 
    FOREIGN KEY (schedule_id) REFERENCES schedule(schedule_id)
);


-- 좌석 예약 테이블 >> 예약시간, 제약조건 추가
create table reservation(
reser_num number(10) primary key, --예약번호
memberid number , -- 회원번호 -- foreign key 제약조건을 걸어야 할 듯
train_num number(10) not null, --기차번호
seat_type varchar2(10) not null, --1인실, 내측좌석, 외측좌석
seat_num number(10) not null, --좌석번호
reser_user varchar2(100), -- 예약자 이름
reserved TIMESTAMP not null, -- 예약시간 
FOREIGN KEY (train_num) REFERENCES train(train_num)
);




-- train 테이블에 데이터 삽입
INSERT INTO train VALUES (1, 'KTX', '서울', '부산');
INSERT INTO train VALUES (2, 'KTX', '서울', '대구');
INSERT INTO train VALUES (3, 'KTX', '서울', '광주');
INSERT INTO train VALUES (4, 'SRT', '서울', '부산');
INSERT INTO train VALUES (5, 'SRT', '서울', '대구');
INSERT INTO train VALUES (6, '새마을호', '서울', '부산');
INSERT INTO train VALUES (7, '새마을호', '서울', '대구');
INSERT INTO train VALUES (8, 'KTX', '서울', '대전');
INSERT INTO train VALUES (9, 'KTX', '서울', '마산');
INSERT INTO train VALUES (10, '새마을호', '서울', '광주');


-- schedule 테이블에 데이터 삽입
INSERT INTO schedule VALUES (schedule_seq.NEXTVAL, 1, TO_TIMESTAMP('2024-11-29 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-11-29 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 50000, 100, 10, 'Y');
INSERT INTO schedule VALUES (schedule_seq.NEXTVAL, 2, TO_TIMESTAMP('2024-11-29 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-11-29 12:30:00', 'YYYY-MM-DD HH24:MI:SS'), 45000, 90, 8, 'N');
INSERT INTO schedule VALUES (schedule_seq.NEXTVAL, 3, TO_TIMESTAMP('2024-11-29 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-11-29 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 47000, 95, 9, 'Y');
INSERT INTO schedule VALUES (schedule_seq.NEXTVAL, 4, TO_TIMESTAMP('2024-11-29 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-11-29 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 48000, 98, 7, 'N');
INSERT INTO schedule VALUES (schedule_seq.NEXTVAL, 5, TO_TIMESTAMP('2024-11-29 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-11-29 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), 46000, 85, 5, 'Y');
INSERT INTO schedule VALUES (schedule_seq.NEXTVAL, 6, TO_TIMESTAMP('2024-11-29 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-11-29 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), 44000, 92, 11, 'N');
INSERT INTO schedule VALUES (schedule_seq.NEXTVAL, 7, TO_TIMESTAMP('2024-11-29 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-11-29 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), 49000, 88, 6, 'Y');
INSERT INTO schedule VALUES (schedule_seq.NEXTVAL, 8, TO_TIMESTAMP('2024-11-29 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-11-29 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), 45000, 96, 4, 'N');
INSERT INTO schedule VALUES (schedule_seq.NEXTVAL, 9, TO_TIMESTAMP('2024-11-29 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-11-29 19:30:00', 'YYYY-MM-DD HH24:MI:SS'), 46000, 90, 8, 'Y');
INSERT INTO schedule VALUES (schedule_seq.NEXTVAL, 10, TO_TIMESTAMP('2024-11-29 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-11-29 20:30:00', 'YYYY-MM-DD HH24:MI:SS'), 43000, 89, 5, 'N');