## DB Setting
```sql
-- 데이터 베이스 생성
CREATE DATABASE IF NOT EXISTS `clSpring` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
       
-- 계정 생성
CREATE USER 'account'@'%' IDENTIFIED BY 'password';
       
-- 권한 설정
GRANT ALL PRIVILEGES ON `clSpring`.* TO 'account'@'%';
FLUSH PRIVILEGES;
```