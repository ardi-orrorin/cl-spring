-- liquibase formatted sql

-- changeset system:v0.0.0-create-table-employees
CREATE TABLE IF NOT EXISTS employees (
    idx                     BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '사원 ID (PK)',
    name                    VARCHAR(50) NOT NULL COMMENT '사원명',
    employee_number         CHAR(7) NOT NULL COMMENT '사번',
    hire_year               VARCHAR(4) COMMENT '입사년도',
    job_title               VARCHAR(100) COMMENT '직책명',
    current_annual_salary   BIGINT NOT NULL DEFAULT 0 COMMENT '기본급여(원)',
    employment_status       VARCHAR(20) NOT NULL COMMENT '근무상태',
    work_location           VARCHAR(100) COMMENT '근무지역',
    dept_name               VARCHAR(100) COMMENT '부서명',
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) COMMENT '사원 정보';

-- rollback DROP TABLE IF EXISTS employees CASCADE;

-- changeset system:v0.0.0-create-table-employee_details
CREATE TABLE IF NOT EXISTS employee_details (
    idx                 BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '사용자 ID (PK)',
    employee_idx        BIGINT NOT NULL COMMENT '사원 ID (FK)',
    email               VARCHAR(100) COMMENT '이메일',
    phone_number        VARCHAR(20) COMMENT '전화번호',
    remark              VARCHAR(1000) COMMENT '비고'
);
-- rollback DROP TABLE IF EXISTS employee_details CASCADE;

-- changeset system:v0.0.0-create-table-categories
CREATE TABLE IF NOT EXISTS evaluation_categories
(
    idx        BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '카테고리 ID (PK)',
    name       VARCHAR(50) NOT NULL COMMENT '카테고리명',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시'
);
-- rollback DROP TABLE IF EXISTS categories CASCADE;

-- changeset system:v0.0.0-create-table-evaluation_items
CREATE TABLE IF NOT EXISTS evaluation_items
(
    idx                         BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '평가항목 ID (PK)',
    name                        VARCHAR(100) NOT NULL COMMENT '평가항목명',
    evaluation_category_idx     BIGINT NOT NULL COMMENT '평가카테고리 ID (FK)',
    score                       INT NOT NULL DEFAULT 0 COMMENT '점수',
    is_used                     BOOLEAN NOT NULL DEFAULT TRUE COMMENT '사용여부',
    remark                      VARCHAR(1000) COMMENT '비고',
    created_at                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
);
-- rollback DROP TABLE IF EXISTS evaluation_items CASCADE;

-- changeset system:v0.0.0-create-table-employees_evaluation
CREATE TABLE IF NOT EXISTS employees_evaluations
(
    idx                        BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '사원평가 ID (PK)',
    employee_idx               BIGINT NOT NULL COMMENT '사원 ID (FK)',
    total_score                INT NOT NULL DEFAULT 0 COMMENT '총점',
    increase_rate              INT NOT NULL DEFAULT 0 COMMENT '인상률',
    next_annual_salary         BIGINT NOT NULL DEFAULT 0 COMMENT '차년도연봉',
    created_at                 TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시'
);
-- rollback DROP TABLE IF EXISTS employees_evaluation_items CASCADE;

-- changeset system:v0.0.0-create-table-employees_evaluation_items
CREATE TABLE IF NOT EXISTS employees_evaluation_items
(
    idx                         BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '사원평가항목 ID (PK)',
    employees_evaluation_idx    BIGINT NOT NULL COMMENT '사원 ID (FK)',
    evaluation_item_idx         BIGINT NOT NULL COMMENT '평가항목 ID (FK)'
);
-- rollback DROP TABLE IF EXISTS evaluation_project_memos CASCADE;

-- changeset system:v0.0.0-create-table-evaluation_projects
CREATE TABLE IF NOT EXISTS evaluation_projects (
    idx                         BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '평가프로젝트메모 ID (PK)',
    employees_evaluation_idx    BIGINT NOT NULL COMMENT '사원평가 ID (FK)',
    name                        VARCHAR(255) NOT NULL COMMENT '평가프로젝트메모명',
    participation_period        VARCHAR(255) NOT NULL COMMENT '참여기간',
    remark                      VARCHAR(1000) NOT NULL COMMENT '메모',
    created_at                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시'
);
-- rollback DROP TABLE IF EXISTS employees_evaluation_projects CASCADE;

-- changeset system:v0.0.0-create-foreign-keys
ALTER TABLE employee_details
    ADD CONSTRAINT fk_employee_details_employee_idx
        FOREIGN KEY ( employee_idx ) REFERENCES employees ( idx );

ALTER TABLE evaluation_items
    ADD CONSTRAINT fk_evaluation_items_evaluation_category_idx
        FOREIGN KEY ( evaluation_category_idx ) REFERENCES evaluation_categories ( idx );
ALTER  TABLE employees_evaluation_items
    ADD CONSTRAINT fk_employees_evaluation_items_employees_evaluation_idx
        FOREIGN KEY ( employees_evaluation_idx ) REFERENCES employees_evaluations ( idx );

ALTER TABLE employees_evaluation_items
    ADD CONSTRAINT fk_employees_evaluation_items_evaluation_item_idx
        FOREIGN KEY ( evaluation_item_idx ) REFERENCES evaluation_items ( idx );

ALTER TABLE evaluation_projects
    ADD CONSTRAINT fk_evaluation_projects_employees_evaluation_idx
        FOREIGN KEY ( employees_evaluation_idx ) REFERENCES employees_evaluations ( idx );

-- changeset system:v0.0.0-add-unique-constraints
ALTER TABLE employees
    ADD UNIQUE ( employee_number );

ALTER TABLE employee_details
    ADD UNIQUE ( employee_idx );

ALTER TABLE evaluation_categories
    ADD UNIQUE ( name );

ALTER TABLE evaluation_items
    ADD UNIQUE ( name, evaluation_category_idx );

ALTER TABLE employees_evaluations
    ADD UNIQUE ( employee_idx );

ALTER TABLE employees_evaluation_items
    ADD UNIQUE ( employees_evaluation_idx, evaluation_item_idx );

-- changeset system:v0.0.0-create-indexes
CREATE INDEX idx_employees_name_employment_status_work_location ON employees (name, employment_status, work_location);
CREATE INDEX idx_evaluation_items_evaluation_category_idx ON evaluation_items (name, evaluation_category_idx);
CREATE INDEX idx_employees_evaluations_total_score ON employees_evaluations (total_score);