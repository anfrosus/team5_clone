## 항해99 9기 C반 클론프로젝트 backend github 

## 🌻 프로젝트 주제
 인스타그램 클론 코딩
📅 프로젝트 기간 : 2022.10.28-2022.11.03

## 👨‍👩‍👧‍👦 Our Team 
|정성우|주재정|김재경|김재욱|김원규|
|:---:|:---:|:---:|:---:|:---:|
|BE|BE|BE|FE|FE|

## 📝 Technologies & Software Used

<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=spring&logoColor=white"/>  <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat-square&logo=SpringSecurity&logoColor=white"/>  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>   <img src="https://img.shields.io/badge/github-181717?style=flat-square&logo=github&logoColor=white"/>  

<img src="https://img.shields.io/badge/git-F05032?style=flat-square&logo=git&logoColor=white"/>  <img src="https://img.shields.io/badge/java-FF81F9?style=flat-square"/>  <img src="https://img.shields.io/badge/JSONWebToken-000000?style=flat-square&logo=JsonWebToken&logoColor=white"/>  <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white"/>  <img src="https://img.shields.io/badge/IntelliJIDEA-000000?style=flat-square&logo=IntelliJIDEA&logoColor=white"/>  <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=Postman&logoColor=white"/>  <img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=Notion&logoColor=white"/>

<img src="https://img.shields.io/badge/AmazonS3-569A31?style=flat-square&logo=AmazonS3&logoColor=white"/>  <img src="https://img.shields.io/badge/AmazonEC2-FF9900?style=flat-square&logo=AmazonEC2&logoColor=white"/>  <img src="https://img.shields.io/badge/AmazonRDS-527FFF?style=flat-square&logo=AmazonRDS&logoColor=white"/>  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>  <img src="https://img.shields.io/badge/Ubuntu-E95420?style=flat-square&logo=Ubuntu&logoColor=white"/>

## 🏀 Trouble Shooting & 아쉬운점
s3 이미지 업로드 시 여러개의 사진을 받아올 때 통신에 문제가 있었습니다. 
MultipartFile의 리스트로 받아오다가 MultipartHttpServeletRequest 객체를 사용하여 키값을 통해 받아와 해결하였습니다.

지속적인 수정과 배포 단계에서 번거로움을 겪었고 CI/CD에 대해 알게되어 공부하였지만 적용해보지 못한 것이 아쉬웠습니다.

Https를 이용하여 클라이언트가 서버와 주고받을때 필요한 SSL 인증서 적용 
기존에 사용했던 가비아 도메인으로 진행이 되지 않아서 도메인을 다른곳으로 옮기고 cerboot설치시 404 type: unauthorized 에러

무한스크롤 및 페이징 처리를 테스트 형식으로 구현해 봤으나 시간관계상 적용해보지 못한 점이 아쉬웠습니다.

