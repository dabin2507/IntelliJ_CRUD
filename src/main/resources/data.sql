INSERT INTO article(id, title, content) VALUES(1, '가가가가', '1111');
INSERT INTO article(id, title, content) VALUES(2, '나나나나', '2222');
INSERT INTO article(id, title, content) VALUES(3, '다다다다', '3333');

-- article 더미 데이터
INSERT INTO article(id, title, content) VALUES(4, '당신의 인생 영화는?', '댓글11');
INSERT INTO article(id, title, content) VALUES(5, '당신의 소울 푸드는?', '댓글22');
INSERT INTO article(id, title, content) VALUES(6, '당신의 취미는?', '댓글33');

-- comment 더미 데이터
-- 4번 게시글의 댓글들
INSERT INTO comment(id, article_id, nickname, body) VALUES(1, 4, 'Park', '엘리멘탈');
INSERT INTO comment(id, article_id, nickname, body) VALUES(2, 4, 'Lee', '무빙');
INSERT INTO comment(id, article_id, nickname, body) VALUES(3, 4, 'Choi', '밀수');

-- 5번 게시글
INSERT INTO comment(id, article_id, nickname, body) VALUES(4, 5, 'Park', '초밥');
INSERT INTO comment(id, article_id, nickname, body) VALUES(5, 5, 'Lee', '샤브샤브');
INSERT INTO comment(id, article_id, nickname, body) VALUES(6, 5, 'Choi', '치킨');

-- 6번 게시글
INSERT INTO comment(id, article_id, nickname, body) VALUES(7, 6, 'Park', '조깅');
INSERT INTO comment(id, article_id, nickname, body) VALUES(8, 6, 'Lee', '유튜브');
INSERT INTO comment(id, article_id, nickname, body) VALUES(9, 6, 'Choi', '독서');