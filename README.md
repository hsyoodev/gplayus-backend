# GPlayUs / Google Play 비공개 테스트 품앗이 서비스
> 참고 : [Backend Wiki](https://github.com/hsyoodev/gplayus-backend/wiki)

사용자가 앱을 등록하여 테스터를 모집하거나 다른 사용자의 앱 테스터로 참여하는 반응형 웹 서비스

// TODO : 전체 서비스 gif

Demo👉 [https://www.gplayus.com/](https://www.gplayus.com/)

## ⛏️ 기술 스택

### Backend

![Static Badge](https://img.shields.io/badge/-Java%2017-%23007396?style=for-the-badge&logo=java&logoColor=white)
![Static Badge](https://img.shields.io/badge/-gradle-%2302303A?style=for-the-badge&logo=gradle&logoColor=white)
![Static Badge](https://img.shields.io/badge/-spring%20boot%203-%236DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Static Badge](https://img.shields.io/badge/-spring%20data%20jpa-%236DB33F?style=for-the-badge&logo=springdatajpa&logoColor=white)
![Static Badge](https://img.shields.io/badge/-spring%20security-%236DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)

### Database

![Static Badge](https://img.shields.io/badge/-mysql-%234479A1?style=for-the-badge&logo=mysql&logoColor=white)

### Authentication

![Static Badge](https://img.shields.io/badge/-oauth%202.0-%2302303A?style=for-the-badge&logo=oauth&logoColor=white)
![Static Badge](https://img.shields.io/badge/-jwt-%2302303A?style=for-the-badge&logo=jwt&logoColor=white)

### Infra

![Static Badge](https://img.shields.io/badge/-amazon%20ec2-%23FF9900?style=for-the-badge&logo=amazonec2&logoColor=white)
![Static Badge](https://img.shields.io/badge/-amazon%20rds-%23527FFF?style=for-the-badge&logo=amazonrds&logoColor=white)
![Static Badge](https://img.shields.io/badge/-docker-%232496ED?style=for-the-badge&logo=docker&logoColor=white)
![Static Badge](https://img.shields.io/badge/-dev%20containers-%2302303A?style=for-the-badge&logo=devcontainers&logoColor=white)
![Static Badge](https://img.shields.io/badge/-nginx-%23009639?style=for-the-badge&logo=nginx&logoColor=white)

## 📁 패키지 구조

```bash
📦backend
 ┣ 📂config
 ┃ ┣ 📜AuditorAwareImpl.java
 ┃ ┣ 📜JpaAuditingConfig.java
 ┃ ┗ 📜SecurityConfig.java
 ┣ 📂controller
 ┃ ┣ 📜AppController.java
 ┃ ┗ 📜MemberController.java
 ┣ 📂domain
 ┃ ┣ 📜App.java
 ┃ ┣ 📜Base.java
 ┃ ┣ 📜Member.java
 ┃ ┗ 📜Tester.java
 ┣ 📂dto
 ┃ ┣ 📂request
 ┃ ┃ ┣ 📜AppRequest.java
 ┃ ┃ ┗ 📜MemberRequest.java
 ┃ ┗ 📂response
 ┃ ┃ ┣ 📜AppResponse.java
 ┃ ┃ ┣ 📜MemberResponse.java
 ┃ ┃ ┗ 📜TesterResponse.java
 ┣ 📂enums
 ┃ ┣ 📜AppStatus.java
 ┃ ┣ 📜AuthorizationType.java
 ┃ ┣ 📜MaxAgeType.java
 ┃ ┣ 📜MemberRole.java
 ┃ ┣ 📜TesterStatus.java
 ┃ ┗ 📜TokenType.java
 ┣ 📂exception
 ┃ ┗ 📜CustomAccessDeniedHandler.java
 ┣ 📂jwt
 ┃ ┣ 📜JwtExceptionFilter.java
 ┃ ┣ 📜JwtFilter.java
 ┃ ┗ 📜JwtUtil.java
 ┣ 📂oauth2
 ┃ ┣ 📜GoogleOAuth2User.java
 ┃ ┣ 📜GoogleOAuth2UserService.java
 ┃ ┗ 📜GoogleOAuth2UserSuccessHandler.java
 ┣ 📂repository
 ┃ ┣ 📜AppRepository.java
 ┃ ┣ 📜MemberRepository.java
 ┃ ┗ 📜TesterRepository.java
 ┣ 📂service
 ┃ ┣ 📜AppService.java
 ┃ ┗ 📜MemberService.java
 ┗ 📜BackendApplication.java
```

## 👀 주요 기능

### 구글 로그인

- 사용자는 구글 계정으로 로그인 및 회원가입을 진행한다.

  // TODO : 구글 로그인 gif

### 앱 등록

- 사용자는 앱을 등록하여 비공개 테스터를 모집할 수 있다.

  // TODO : 앱 등록 gif

### 앱 찾기

- 사용자는 비공개 테스터를 모집하는 앱을 확인할 수 있다.

  // TODO : 앱 찾기 gif

### 마이앱

- 사용자가 등록한 앱을 상태별로 확인할 수 있다.

  // TODO : 마이앱 gif

### 마이테스터

- 사용자가 비공개 테스터로 지원한 앱을 상태별로 확인할 수 있다.

  // TODO : 마이테스터 gif
  
