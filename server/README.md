## Server 개발 세팅

### DB 초기 세팅(MySQL)

#### 1. MySQL 설치 (Docker)

```bash
## 8버전 이상
docker run --name mysql -e MYSQL_ROOT_PASSWORD=password -d mysql:8.0
```

#### 2. 데이터 베이스 생성

```sql
CREATE DATABASE IF NOT EXISTS `clSpring` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 3. 계정 생성 및 권한 설정

```sql
-- 계정 생성
CREATE USER 'account'@'%' IDENTIFIED BY 'password';

-- 권한 설정
GRANT ALL PRIVILEGES ON `clSpring`.* TO 'account'@'%';
FLUSH PRIVILEGES;
```

#### 4. DML 생성 (Liquibase 사용)

서버 실행시 Liquibase를 통해 DML을 생성합니다.
sql 파일 [Link](./src/main/resources/db/changelog/mysql/v0.0.0_create_tables.sql)

#### 5. application.yml 설정

develop 환경 프로필 추가로 설정합니다.
기본 프로필값 ([./src/main/resources/application.yml](./src/main/resources/application.yml))

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/clSpring?useSSL=false&serverTimezone=UTC
    username: account
    password: password
```
