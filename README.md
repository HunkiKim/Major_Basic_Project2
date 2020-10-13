### 전기프2 3팀 팀프로젝트

```
.
└───asset 
│   └───Langs
└───controller - 외부와 소통한 것 분류 및 처리
│   └───Controller
│   └───MainController
│   └───MemberController
└───service - controller에서 처리할때 사용
│   └───MemberService
└───repository - service에서 데이터베이스 쓰는 부분
│   └───MemberRepository
└───dto - 각 계층별 소통 위한 객체
│   └───Member
└───Main - 프로그램 메인
└───UI - 외부와 소통 자체
```

|폴더/파일|설명|
|------|---|
|Main|프로그램 돌아가는 것|
|UI|외부와 소통 자체|
|controller/*|외부와 소통한 것 분류와 처리|
|service/*|controller에서 호출, 비즈니스 로직|
|repository/*|service에서 호출, 데이터베이스 관련|
|dto/*|controller, service, repository 사이 소통 위한 객|

 