-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.11.8-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- project 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `project` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `project`;

-- 테이블 project.clothesinfo 구조 내보내기
CREATE TABLE IF NOT EXISTS `clothesinfo` (
  `ClothesID` varchar(15) NOT NULL,
  `ClothesImage` longblob DEFAULT NULL,
  `ClothesName1` varchar(255) DEFAULT NULL,
  `ClothesName2` varchar(255) DEFAULT NULL,
  `ClothesName3` varchar(255) DEFAULT NULL,
  `ClothesName4` varchar(255) DEFAULT NULL,
  `ClothesName5` varchar(255) DEFAULT NULL,
  `Price1` decimal(20,0) DEFAULT NULL,
  `Price2` decimal(20,0) DEFAULT NULL,
  `Price3` decimal(20,0) DEFAULT NULL,
  `Price4` decimal(20,0) DEFAULT NULL,
  `Price5` decimal(20,0) DEFAULT NULL,
  `SiteLink` varchar(255) DEFAULT NULL,
  `PersonalColor` varchar(20) DEFAULT NULL,
  `Gender` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`ClothesID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 테이블 데이터 project.clothesinfo:~24 rows (대략적) 내보내기
INSERT INTO `clothesinfo` (`ClothesID`, `ClothesImage`, `ClothesName1`, `ClothesName2`, `ClothesName3`, `ClothesName4`, `ClothesName5`, `Price1`, `Price2`, `Price3`, `Price4`, `Price5`, `SiteLink`, `PersonalColor`, `Gender`) VALUES
	('1', NULL, '모나코 캡모자', '드롱 나일론 숄더 백', '헤르 레이어드 가디건', '사델 리본 프린팅 스탠다드 데님 팬츠', '', 42000, 74000, 23400, 18000, NULL, 'https://ko.codibook.net/codi/8592243', 'spring', '여성'),
	('10', NULL, '몬드 펀칭니트가디건', '펌프 와이드 데님 팬츠', '에버 보스넥 나시', '심플스퀘어 숄더백', '', 23800, 42600, 16900, 16500, NULL, 'https://ko.codibook.net/codi/8596311', 'winter', '여성'),
	('11', NULL, '하이퍼 워싱 와이드 데님 팬츠', '글로 스티치 오버 자켓', '이브 캐시미어 가디건', '라운드 빅 숄더 백', '', 59500, 99000, 46000, 28800, NULL, 'https://ko.codibook.net/codi/8587952', 'winter', '여성'),
	('12', NULL, '퍼븐 덤블스타디움 점퍼', '스핀 스프라이프 티셔츠', '하이포켓와이드 데님', '캠퍼 퀼팅 백팩', '피그먼트 자수 볼캡', 56000, 15900, 34200, 87000, NULL, 'https://ko.codibook.net/codi/8577101', 'winter', '여성'),
	('13', NULL, '스프라이트 부클 니트 가디건', '피그먼트 루즈 핏 반팔 셔츠', '5포켓 코튼 팬츠 화이트', '시몬 오리진 독일군 스니커즈', '', 43900, 36900, 49900, 79200, NULL, 'https://www.musinsa.com/app/codimap/views/33975?style_type=&tag_no=&brand=&display_cnt=60&list_kind=big&sort=date&page=7', 'spring', '남성'),
	('14', NULL, '서핀 헤비웨잇 후디', '데일리 와이드 핏 연청 워싱 데님', '에어 포스 코코넛 밀크', '', '', 96000, 26900, 159000, NULL, NULL, 'https://www.musinsa.com/app/codimap/views/33942?style_type=&tag_no=&brand=&display_cnt=60&list_kind=big&sort=date&page=7', 'spring', '남성'),
	('15', NULL, '코튼 100% 헤리티지 케이블 니트', '코튼 와이드 카고 팬츠', '노트 스트링 플랫폼 샌들', '', '', 59000, 49000, 55180, NULL, NULL, 'https://www.musinsa.com/app/codimap/views/34284?style_type=casual&tag_no=&brand=&display_cnt=60&list_kind=big&sort=date&page=2', 'spring', '남성'),
	('16', NULL, '아티스트 티셔츠 머스탱 화이트', '에센셜 원턱 데님 셔츠 딥 블루', '써클 로고 데님 볼캡', '에어포스 화이트:아크아리우스 블루', '', 39200, 39200, 35100, 139000, NULL, 'https://www.musinsa.com/app/codimap/views/34488?style_type=&tag_no=&brand=&display_cnt=60&list_kind=big&sort=date&page=2', 'summer', '남성'),
	('17', NULL, '옥스포드 오버핏 반팔 셔츠', '원턱 코튼 와이드 밴딩 팬츠', '라이벌리-옐로우', '드로우스트링 슬링 백 블랙', '써니컬스틸 꼬임포인트 반지', 49900, 39500, 119000, 39900, 14310, 'https://www.musinsa.com/app/codimap/views/34525?style_type=&tag_no=&brand=&display_cnt=60&list_kind=big&sort=date&page=2', 'summer', '남성'),
	('18', NULL, '린넨 컴포터블 오버핏 셔츠', '스톤 워싱 데님 와이드 이지 팬츠 블루', '츄바스코 CONCHA CCT2014', '웨이브 로고 볼캡', NULL, 42480, 34700, 19000, 27300, NULL, 'https://www.musinsa.com/app/codimap/views/34415?style_type=&tag_no=&brand=&display_cnt=60&list_kind=big&sort=date&page=3', 'summer', '남성'),
	('19', NULL, '데님 투턱 카펜터 팬츠', 'Im Lukcy 볼캡', '클래식 슬립온 체커보드', 'Mesh Football Jersey Burgundy', NULL, 59000, 39900, 46900, 79200, NULL, 'https://www.musinsa.com/app/codimap/views/34585?style_type=&tag_no=&brand=&display_cnt=60&list_kind=big&sort=date&page=2', 'autumn', '남성'),
	('2', NULL, '더빗 싱글 자켓', '부클 골지 니트', '포엔 하프 데님, 모그 이지 벨트', '베로 스웨이드 투웨이 파우치 세트', '버킷 백', 55000, 99000, 35000, 22500, 35000, 'https://ko.codibook.net/codi/8592257', 'spring', '여성'),
	('20', NULL, '냥냥냥 티셔츠', 'Clean Deep One Tuck Denim Pants', '레이든 스니커즈', NULL, NULL, 31200, 45000, 29900, NULL, NULL, 'https://www.musinsa.com/app/codimap/views/34333?style_type=&tag_no=&brand=&display_cnt=60&list_kind=big&sort=date&page=4 ', 'autumn', '남성'),
	('21', NULL, '메테오 디어 피그먼트 후드집업', '마이유스 그래픽 오버핏 티셔츠', '알피니스트 이지 밴딩 클라이밍 팬츠', '척 79 시즈널 컬러 다크 루트', NULL, 58900, 33600, 73800, 76000, NULL, 'https://www.musinsa.com/app/codimap/views/33406?style_type=&tag_no=&brand=&display_cnt=60&list_kind=big&sort=date&page=11', 'autumn', '남성'),
	('22', NULL, '비건 레더 오버사이즈 싱글 재킷', '릴렉스드 나일론 크리즈 셔츠', '셀비지 와이드 데님 팬츠', '베이식 이탈리안 레더 벨트', '플레인 더비 슈즈', 119900, 39890, 47390, 25390, 89890, 'https://www.musinsa.com/app/codimap/views/32979?style_type=americancasual&tag_no=&brand=&display_cnt=60&list_kind=big&sort=date&page=1', 'winter', '남성'),
	('23', NULL, '냥냥냥 티셔츠', '테크 쇼츠', 'SYMBOL DAMAGE PATTCHED BALLCAP', '밀레니오 TR', NULL, 23400, 32300, 44100, 79000, NULL, 'https://www.musinsa.com/app/codimap/views/34584?style_type=casual&tag_no=&brand=&display_cnt=60&list_kind=big&sort=date&page=1', 'winter', '남성'),
	('24', NULL, '미시간 울버린 피그먼트 반팔티', '크럼플 나일론 팬츠', NULL, NULL, NULL, 24900, 89000, NULL, NULL, NULL, 'https://www.musinsa.com/app/codimap/views/14950?style_type=street&tag_no=&brand=&display_cnt=60&list_kind=big&sort=view_cnt&page=1', 'winter', '남성'),
	('3', NULL, '베럴 워싱자켓', '커몬 파이팅 트라우저', '티파니 셔팅 탱크 탑', '', '', 106000, 19800, 21600, NULL, NULL, 'https://ko.codibook.net/codi/8592252', 'spring', '여성'),
	('4', NULL, '올리 모헤어 슬리브', '벨트SET 핀턱하프팬츠', '수스 볼레로 가디건', '인디고 데님 샌들', '데니 써클 토트 백', 18000, 40000, 18000, 49800, 22400, 'https://ko.codibook.net/codi/8593905', 'summer', '여성'),
	('5', NULL, '베르디 투웨이 집업', '스웨이 투핀턱슬랙스', '리에드 스트랩 슬리브', '', '', 34000, 31500, 23000, NULL, NULL, 'https://ko.codibook.net/codi/8593968', 'summer', '여성'),
	('6', NULL, '플디 체크박시셔츠', '헤이츠 웨이브 니트 쇼츠', '프랑 레이스 탱크 탑', '파리 컬러코튼에코백', '힙제리 선글라스', 35000, 23000, 36000, 9000, 24500, 'https://ko.codibook.net/codi/8593960', 'summer', '여성'),
	('7', NULL, '메리노울 롱 코트', '로느 떡볶이 크롭 니트가디건', '데이지 일자 데님', '커피던스 블로퍼', '브론즈 버클스웨이드백', 99900, 29900, 42000, 35300, 29000, 'https://ko.codibook.net/codi/8297644', 'autumn', '여성'),
	('8', NULL, '루지 골지 티', '윈터 기모 와이드 롱 슬렉스', 'SNFBR3d206', '토피넛', '', 14000, 28900, 22900, 36000, NULL, 'https://ko.codibook.net/codi/8415901', 'autumn', '여성'),
	('9', NULL, '대체불가 와이드 데님팬츠', '카시오 엔틱 레더 시계', '절개 라운드 앵클부츠', '민트초코사각백', '', 24900, 35000, 45900, 34200, NULL, 'https://ko.codibook.net/codi/8210374', 'autumn', '여성');

-- 테이블 project.colorinfo 구조 내보내기
CREATE TABLE IF NOT EXISTS `colorinfo` (
  `ColorID` varchar(15) NOT NULL,
  `ColorHex` varchar(20) NOT NULL,
  `ColorName` varchar(100) DEFAULT NULL,
  `PersonalColor` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ColorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 테이블 데이터 project.colorinfo:~64 rows (대략적) 내보내기
INSERT INTO `colorinfo` (`ColorID`, `ColorHex`, `ColorName`, `PersonalColor`) VALUES
	('1', '#fdf9ca', '레몬 쉬폰', 'spring'),
	('10', '#70bdeb', '에어로', 'spring'),
	('11', '#3554a6', '바이올렛 블루', 'spring'),
	('12', '#2e4289', '다크 콘플라워 블루', 'spring'),
	('13', '#e5bfb2', '사막 모래색', 'spring'),
	('14', '#7f54a3', '로얄 퍼플', 'spring'),
	('15', '#aa379e', '비잔틴', 'spring'),
	('16', '#d2ac63', '어스 옐로우', 'spring'),
	('17', '#FFFDF0', '아이보리', 'summer'),
	('18', '#FAE3DD', '린넨', 'summer'),
	('19', '#EBACC7', '장미 분홍', 'summer'),
	('2', '#fc9e6a', '귤색', 'spring'),
	('20', '#C7B6DA', '라벤더 그레이', 'summer'),
	('21', '#D5D3DE', '라이트 라벤더', 'summer'),
	('22', '#9CB4E0', '페리윙클 블루', 'summer'),
	('23', '#93A5A9', '베네치안 레드', 'summer'),
	('24', '#669DC6', '블루 그레이', 'summer'),
	('25', '#EBEDA4', '창백한 노란색', 'summer'),
	('26', '#BCDCB3', '라이트 모스 그린', 'summer'),
	('27', '#9EBFAC', '캠브리지 블루', 'summer'),
	('28', '#02B484', '먼셀 그린', 'summer'),
	('29', '#C8AA9F', '실버 핑크', 'summer'),
	('3', '#feeba7', '베네치안 레드', 'spring'),
	('30', '#99735E', '간 밤색', 'summer'),
	('31', '#FC6668', '파스텔 레드', 'summer'),
	('32', '#E84365', '파라다이스 핑크', 'summer'),
	('33', '#FCF7D9', '콘실크', 'autumn'),
	('34', '#EAC048', '크레욜라 콘', 'autumn'),
	('35', '#CBA22C', '새틴 쉰 골드', 'autumn'),
	('36', '#857C41', '울스 모스 그린', 'autumn'),
	('37', '#F2B28E', '크레욜라 골드', 'autumn'),
	('38', '#F59E8D', '비비드 텐저린', 'autumn'),
	('39', '#C14927', '다크 파스텔 레드', 'autumn'),
	('4', '#ffdcbe', '창백한 오렌지색', 'spring'),
	('40', '#8C4943', '브렌디', 'autumn'),
	('41', '#87B988', '다크 씨 그린', 'autumn'),
	('42', '#379F48', '메이 그린', 'autumn'),
	('43', '#516D30', '다크 올리브 그린', 'autumn'),
	('44', '#252F17', '소나무색', 'autumn'),
	('45', '#B99386', '페일 토프', 'autumn'),
	('46', '#83646C', '딥 토프', 'autumn'),
	('47', '#53343C', '다크 퓨스', 'autumn'),
	('48', '#73225C', '비잔티움', 'autumn'),
	('49', '#E8DAAD', '쿠키앤크림', 'winter'),
	('5', '#f69d8b', '비비드 텐저린', 'spring'),
	('50', '#F6EB5D', '옥수수색', 'winter'),
	('51', '#068E4E', '스페인 그린', 'winter'),
	('52', '#1B5942', '브런즈웍 그린', 'winter'),
	('53', '#FDF2FA', '라벤더 블러셔', 'winter'),
	('54', '#E658A0', '라즈베리 핑크', 'winter'),
	('55', '#CC3C94', '마젠타 핑크', 'winter'),
	('56', '#9C3795', '바이올렛', 'winter'),
	('57', '#13A3DF', '파링', 'winter'),
	('58', '#6C9FD4', '리틀보이 블루', 'winter'),
	('59', '#293684', '코스믹 코발트', 'winter'),
	('6', '#d23b2a', '페르시아 레드', 'spring'),
	('60', '#231B57', '우주 생도', 'winter'),
	('61', '#CACACA', '차이나 실버', 'winter'),
	('62', '#90809B', '로마 실버', 'winter'),
	('63', '#3A3B3F', '오닉스', 'winter'),
	('64', '#3B2A4E', '아메리칸 퍼플', 'winter'),
	('7', '#cb2527', '소방차 레드', 'spring'),
	('8', '#fe5d4b', '토마토색', 'spring'),
	('9', '#77cfdd', '미들 블루', 'spring');

-- 테이블 project.cosmeticinfo 구조 내보내기
CREATE TABLE IF NOT EXISTS `cosmeticinfo` (
  `CosmeticID` varchar(15) NOT NULL,
  `CosmeticImage` longblob DEFAULT NULL,
  `CosmeticName` varchar(255) DEFAULT NULL,
  `Price` varchar(20) DEFAULT NULL,
  `SkinType` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`CosmeticID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 테이블 데이터 project.cosmeticinfo:~12 rows (대략적) 내보내기
INSERT INTO `cosmeticinfo` (`CosmeticID`, `CosmeticImage`, `CosmeticName`, `Price`, `SkinType`) VALUES
	('1', NULL, '아벤느 트릭세라 로션', '33000', 'OS'),
	('10', NULL, '라운드어라운드 그린티 약산성 에센스 로션', '22000', 'DR'),
	('11', NULL, '라운드어라운드 그린티 약산성 에센스 워터', '29920', 'DR'),
	('12', NULL, '소이 수플레 크림', '24000', 'DR'),
	('2', NULL, '마녀공장 갈락토미 클리어 스킨 토너', '29000', 'OS'),
	('3', NULL, '클린 아크 앰플', '15000', 'OS'),
	('4', NULL, '피토메스 안티포어 리파이닝 스킨로션', '25200', 'OR'),
	('5', NULL, '닥터지 레드 블레미쉬 시카 수딩 토너 에센스 플루이드', '19000', 'OR'),
	('6', NULL, '마녀공장 비피다 바이옴 콤플렉스 앰플', '35000', 'OR'),
	('7', NULL, '아벤느 이드랑스 딥 모이스트로션', '27000', 'DS'),
	('8', NULL, '라운드랩 1025 독도 토너', '27000', 'DS'),
	('9', NULL, '투데이위드 판테놀크림', '27000', 'DS');

-- 테이블 project.member 구조 내보내기
CREATE TABLE IF NOT EXISTS `member` (
  `ID` varchar(15) NOT NULL,
  `PW` varchar(15) NOT NULL,
  `Name1` varchar(20) NOT NULL,
  `Gender` varchar(6) NOT NULL,
  `Address` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 테이블 데이터 project.member:~4 rows (대략적) 내보내기
INSERT INTO `member` (`ID`, `PW`, `Name1`, `Gender`, `Address`) VALUES
	('1', '123', '김대훈', '남성', '거제'),
	('2', '456', '이상수', '여성', '대구'),
	('3', '789', '최대열', '여성', '부산'),
	('4', '012', '최시우', '남성', '부산');

-- 테이블 project.skininfo 구조 내보내기
CREATE TABLE IF NOT EXISTS `skininfo` (
  `MemberID` varchar(15) NOT NULL,
  `SkinType` varchar(20) DEFAULT NULL,
  `PersonalColor` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `DiagnosisDate` date DEFAULT NULL,
  PRIMARY KEY (`MemberID`),
  CONSTRAINT `skininfo_ibfk_1` FOREIGN KEY (`MemberID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 테이블 데이터 project.skininfo:~4 rows (대략적) 내보내기
INSERT INTO `skininfo` (`MemberID`, `SkinType`, `PersonalColor`, `DiagnosisDate`) VALUES
	('1', 'DRPT', 'Spring', '2024-01-15'),
	('2', 'DRNT', 'Autumn', '2024-03-10'),
	('3', 'DSPT', 'Winter', '2024-04-25'),
	('4', 'OSPW', 'Spring', '2024-05-30');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
