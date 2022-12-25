# Zerobase Community Project

## 프로젝트 주제

- 커뮤니티 프로젝트

## 개발 목적

- 고객의 니즈와 문제를 정리
- 각종 개발 전략과 도메인 설계, 실무 디자인 패턴, 비즈니스 로직의 구현을 경험하는 것을 목표로 한다.
- 문제 → 요구사항 → 기능(feature) 도출 → 구현 방안의 기획 → 개발 계획 수립 → 실행

## 프로젝트 구조

![프로젝트구조](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/fe863040-c3df-4e9a-9c64-d020ad870881/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221223%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221223T134852Z&X-Amz-Expires=86400&X-Amz-Signature=9c1f3d33b97a1d89ad43f2bd02b6dc3a7ca4ba88b545eb77a38323023b2ed490&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject)

## 유스케이스
![image](https://user-images.githubusercontent.com/76902448/209464954-fbf50eff-002d-4294-bff0-4ef8fb222aee.png)


## ERD
![board-community-ERD](https://user-images.githubusercontent.com/76902448/209463868-52cbfe8f-f28b-4133-831a-5ce20f00807e.png)


## 개발 환경

- 개발 도구 : Intellij IDEA Ultimate 2021.3.3
- 소스코드 관리 : Git 2.39.0
- Git 호스팅 : GitHub
- Git GUI : GitKraken 8.5.0

## 사용 기술 스택

- 언어 : Java 11, 17
- 프레임워크 : SpringBoot 2.7.0
- 빌드 도구 : gradle 7.4.1
- 테스트 : JUnit5
    - 라이브러리 : Mockito, AssertJ
    - 스프링 부트 슬라이스 테스트 테크닉
    - GitHub : 테스트/빌드 Auto화
- DB : MySQL
- JPA
- 배포 : AWS or Heroku

## 프로젝트 기능

**📌 주제별 구현 기능**

- 주제1. 커뮤니티 과제
    - [ ]  게시물 검색 기능
    - [ ]  게시글 관리 (게시글 작성 / 게시글 목록 조회 / 작성된 게시글 편집 기능)
    - [ ]  게시글 댓글 기능
    - [ ]  인증 기능
    - [ ]  로그인 / 로그 아웃에 따른 편집 허가 기능 구현

**회원가입과 로그인**

- 회원가입
    - 회원가입시 이메일, 이름, 전화번호, 비밀번호 정보가 필요하다 (관리자일 경우 db에서 직접 처리)
    - 회원가입시 이미 회원가입된 `이메일`로 회원가입을 시도하면 에러를 발생한다
- 로그인
    - 로그인시 회원가입한적 없는 이메일을 이용하여 로그인을 시도하면 에러가 발생한다
    - 로그인시 비밀번호가 일치하지 않는다면 에러가 발생한다.

**게시판 글 작성**

- 로그인한 유저는 게시판 글을 작성할 수 있다
    - 로그인한 유저에게만 게시글 작성 페이지가 보이도록 한다

**게시판 글 댓글 작성**

- ~~로그인한 유저는~~ 게시판에 댓글을 작성할 수 있다

**게시판 글 목록 조회**

- 로그인한 유저는 게시판 글을 기간별로 조회할 수 있다

**게시판 글 상세보기**

- 게시판의 글은 로그인 한 유저만 볼 수 있다
    - 유저가 로그인하지 않았다면 에러를 발생한다
- 게시판 글 상세보기에서는 제목, 작성일, 수정일, 작성자, 본문의 내용이 보인다.

**게시판 글 수정**

- 유저는 자신이 쓴 게시글만 수정할 수 있다

**게시판 글 삭제**

- 유저는 자신이 쓴 게시글만 삭제할 수 있다
- 관리자는 모든 유저의 게시글을 삭제할 수 있다
