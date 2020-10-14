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
|service/*|controller에서 호출, 기능 자체|
|repository/*|service에서 호출, 데이터베이스 관련|
|dto/*|controller, service, repository 사이 소통 위한 객|


구현 예
 1. 프로그램 실행
 2. **Main** - 무결성 검사 및 각 화면 실행
 3. **Main** - 현재 화면을 **MainController**로 설정
    * **MainController** - 메인 화면 
        1. 메뉴 보여주고 입력 대기 (예> 1. 직원 선택 및 관리)
        2. 입력에 따라 다음 화면 리턴 (예> **new MemberController()**)
 4. **Main** - 리턴 값에 따라 현재 화면을 **MemberController**로 설정
    * **MemberController** - 회원 관련 화면
        1. 회원 검색 보여주고 입력 대기 (예> 전기프)
        2. 멤버 검색 메소드를 사용해야함
            * **MemberService** - 멤버에서 쓰는 기능, 여기선 검색만 보여줌
                1. 이름은 잘 들어왔는가? / 규칙에 맞는가? 아니면 throw Exception
                2. 다 통과했으면 데이터베이스에 검색 요청 **MemberRepository**
                3. 검색 잘 됐는가? 아니면 throw Exception
                4. 잘 됐으면 데이터를 (필요한대로 조작해서) return 
        3. 만약 오류가 발생했으면 캐치해서 화면에 전달하고 처리
        4. 성공시 그 데이터를 토대로 다음 작업 호출