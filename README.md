27/11: Thêm tính năng nhắn tin, thêm phần Tài liệu (nhấn vào tài liệu máy tính tự mở), lưu chỗ Công việc vào Database

Trong CSDL tạo bảng tinnhan1
Đây là SQL phần nhắn tin:

use java_project;
CREATE TABLE `java_project`.`tinnhan1` (
  `id_tn` VARCHAR(20) NOT NULL,
  `stt` INT NOT NULL,
  `message` VARCHAR(5000) NULL,
  `from` VARCHAR(20) NULL,
  PRIMARY KEY (`id_tn`, `stt`));


INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNDPT', '1', 'Hello', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNDPT', '2', 'Can you tell me what is this?', 'CNDPT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNDPT', '3', 'I don\'t know', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNDPT', '4', 'Really ???', 'CNDPT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNDPT', '6', 'I know a thing that can make you surprise', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNDPT', '7', 'I said the TRUE', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNKTD', '1', 'A', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNKTD', '2', 'B', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNKTD', '3', 'C', 'CNKTD');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNKTD', '4', 'D', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNKTD', '5', 'E', 'CNKTD');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNKTD', '6', 'F', 'CNKTD');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNKTD', '8', 'hoàn thiện nốt phần client...', 'CNKTD');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNKTD', '9', 'oke', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNKTD', '10', 'ffffff', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNTT', '8', '123', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_CNTT', '9', '234', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_KTDTVT', '1', 'Xin chào', 'KTDTVT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_KTDTVT', '2', 'Chào bạn, bạn có việc gì vậy', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_KTDTVT', '3', 'Không có gì, mình nhắn để làm phiền tí thôi', 'KTDTVT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_KTDTVT', '5', 'Hello...', 'KTDTVT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_KTDTVT', '6', 'Hi', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_KTDTVT', '7', 'Hell', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_KTDTVT', '8', 'ABCDEF...', 'KTDTVT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_KTDTVT', '9', 'Helllllllllllll', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_KTDTVT', '10', 'Hiiiii', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_KTDTVT', '11', 'COCOCHANEL...', 'KTDTVT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_KTDTVT', '12', 'đang xử lý...', 'KTDTVT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_KTDTVT', '13', 'Thế đã xong chưa mà ngồi chơi', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_KTDTVT', '14', 'HHHHHHHHHHHHHHHHHHHHH', 'CNTT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_TTDPT', '1', 'Hiiiiiiiiiiiiiiiiiiiiiiiii', 'TTDPT');
INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_TTDPT', '2', 'Hello', 'CNTT');

SQL phần Công việc:

CREATE TABLE `btl_java`.`congviec` (
  `mota` VARCHAR(350) NOT NULL,
  `trangthai` VARCHAR(350) NOT NULL,
  `ngaythang` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`mota`, `trangthai`, `ngaythang`));

INSERT INTO `demo`.`congviec` (`mota`, `trangthai`, `ngaythang`) VALUES ('Giáng sinh cùng ny', 'chưa đến ngày', '2023-12-25');
INSERT INTO `demo`.`congviec` (`mota`, `trangthai`, `ngaythang`) VALUES ('Thi học kì 1', 'chưa hoàn thành', '12/12/2023');
INSERT INTO `demo`.`congviec` (`mota`, `trangthai`, `ngaythang`) VALUES ('Thi học kì 1 môn MMT', 'chưa hoàn thành', '2023-12-25');
INSERT INTO `demo`.`congviec` (`mota`, `trangthai`, `ngaythang`) VALUES ('Thi môn LSĐ', 'chưa hoàn thành', '12/12/2023');
INSERT INTO `demo`.`congviec` (`mota`, `trangthai`, `ngaythang`) VALUES ('Thi môn Python', 'chưa hoàn thành', '19/12/2023');



SQL phần Tài liệu:
CREATE TABLE `btl_java`.`tailieu` (
  `url` VARCHAR(200) NOT NULL,
  `khoa` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`url`, `khoa`));

  

