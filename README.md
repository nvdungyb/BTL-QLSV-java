Thêm tính năng nhắn tin ( nhưng mà bị lỗi chỗ gộp cả 2 cái Server và Client vào cùng 1 file) 
Trong CSDL tạo bảng tinnhan1
Đây là SQL:

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

  
