
# [keumbang_resource]

본 서비스는 `귀금속 거래`를 위한 플랫폼으로, 관리자가 상품 정보를 미리 등록해두고, 사용자가 해당 상품을 선택하여 구매 또는 판매 주문을 진행할 수 있도록 합니다.

이 플랫폼을 통해 사용자 간의 원활한 거래와 커뮤니케이션을 촉진하고, 다양한 귀금속 거래 경험을 제공합니다.


## 목차

- [QuickStart](#QuickStart)
- [API 문서 및 호출 링크](#api-문서-및-호출-링크)
- [개발기간](#개발기간)
- [시나리오](#시나리오)
- [기술 스택](#기술-스택)
- [아키텍처](#아키텍처)
- [데이터베이스 모델링](#데이터베이스-모델링)
- [API 명세](#API-명세)
- [구현 기능](#구현-기능)
- [프로젝트 진행 및 이슈 관리](#프로젝트-진행-및-이슈-관리)

<br/>

## QuickStart

이 섹션에서는 본 프로젝트를 빠르게 시작하기 위한 단계들을 안내합니다.

### 1. 프로젝트 클론


```bash
git clone https://github.com/Perhona/keumbang-resource.git
cd keumbang-resource
```

### 2. 프로젝트 환경 설정

**MariaDB (11.5.2)** 를 로컬 또는 Docker로 설정합니다.  
- `.env` 파일에서 데이터베이스 접속 정보, 포트 번호, gRPC 서버 주소를 수정합니다.
- `.env.example`파일을 참조하세요.

```yaml
# 서버 설정
SPRING_PORT=port_number

# 데이터베이스 설정
SPRING_DATASOURCE_URL=jdbc:mariadb://localhost:port_number/db_name
SPRING_DATASOURCE_USERNAME=db_username
SPRING_DATASOURCE_PASSWORD=db_password

# gRPC 설정
GRPC_AUTH_SERVER_ADDRESS=auth_server_ip:auth_server_port

```

### 3. 애플리케이션 실행

- **Java 17**이 설치되어 있어야 합니다.
- **인증 서버**가 실행 중이어야 합니다.

프로젝트를 빌드합니다.

```bash
./gradlew build
```

아래 명령어로 애플리케이션을 실행합니다:

```bash
./gradlew bootRun
```

### 4. Swagger를 통한 API 문서 확인 및 Postman API 호출

[문서화](#api-문서-및-호출-링크) 섹션으로 이동하여 웹브라우저에서 문서 확인 및 API 호출을 할 수 있습니다.

<br/>

## API 문서 및 호출 링크

- **Swagger API 문서**: [Swagger UI - localhost](http://localhost:9999/swagger-ui.html)  
  Swagger를 통해 API 스펙을 확인할 수 있습니다.


- **Postman API 호출**: [Postman Collection](https://web.postman.co/workspace/f6540017-ceef-4c8c-80be-b2986cacad7a/collection/20514647-59a15e4f-0476-4d9b-85a4-e303cbc3f4ec)  
  Postman 링크를 통해 API를 직접 호출하고 테스트할 수 있습니다.

<br/>

---
## 개발기간

**2024.09.04 ~ 2024.09.11 (8일, 1인 개발)**

- **역할**: gRPC를 통한 Access Token 인증 구현, 상품/주문 등록

<br/>

## 시나리오

- 관리자는 다양한 `상품(금 99.9%, 금 99.99%)`을 등록할 수 있습니다. 등록된 상품은 가격, 설명, 타입 등 필요한 정보가 포함되어 있습니다.
- 사용자는 `인증 서버`에서 회원가입 및 로그인을 진행합니다.
- 로그인 시 발급 된 `Access Token`을 사용하여 본 서버에서 상품 조회 및 구매/판매 주문 API를 호출할 수 있습니다.
- 사용자는 관리자가 등록한 상품을 선택하여, 수량, 주문 유형(구매/판매), 배송지 정보를 입력하고 주문을 진행할 수 있습니다.
- 주문이 완료되면 해당 주문의 정보가 저장되고, 주문 번호가 생성됩니다.

<br/>

## 기술 스택

- **언어 및 프레임워크:**

  ![Java-17](https://img.shields.io/badge/Java-17-blue)  
  ![Springboot-3.2.8](https://img.shields.io/badge/Springboot-3.2.8-red)


- **데이터베이스:**

  ![MariaDB-11.5.2](https://img.shields.io/badge/MariaDB-11.5.2-blue)

<br/>

## 아키텍처

- 본 서비스는 마이크로서비스 아키텍처를 기반으로 구축되었으며, gRPC를 통한 통신과 JWT를 활용한 인증 시스템을 적용하였습니다.


## 데이터베이스 모델링

![데이터베이스 모델링](https://github.com/user-attachments/assets/9a8e556c-6bda-45fb-91e0-8fba01c09f69)


<br/>

## API 명세

| **분류** | **API 명칭** | **HTTP 메서드** | **엔드포인트** | **설명** |
| --- | --- | --- | --- | --- |
| **구매/판매 주문** | 상품 등록 | POST | /products | 관리자는 상품 정보(가격/설명/타입)를 등록할 수 있습니다. |
|  | 구매/판매 주문 | POST | /trades | 사용자는 상품 정보, 주문 수량, 배송지 정보, 주문 유형으로 주문을 진행할 수 있습니다. |

<br/>

## 구현 기능

1. **구매/판매 주문**
    - 상품
      - `상품 타입`, `상품 설명`, `상품 가격`으로 상품 등록
    - 주문
        - 사용자가 관리자가 미리 등록한 상품을 선택하여 구매 또는 판매 주문을 진행
        - `유저`가 특정 상품에 대해 주문을 진행
        - `상품 id`, `수량`, `주소`, `주문 유형` 등을 요청에 담아 전송
        - 주문이 생성되면, 거래 번호가 발급되고, 주문 내역 저장

<br/>

## 프로젝트 진행 및 이슈 관리

![이슈관리](https://github.com/user-attachments/assets/9d941fa4-de50-42b7-9483-469a37d82bd8)

<br/>
