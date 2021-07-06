# MVVM-CleanArch-Starwars-Clone

## 코드 출처
Go to original source code --> [Links](https://github.com/odaridavid/Clean-MVVM-ArchComponents)


## 목적
- `Clean Architecture` 학습
- `Coroutine`의 적절한 사용법 학습
- `Motion Layout`에 대한 이해
- `Retrofit`을 사용한 올바른 통신 방법
- 기타 `Jetpack`의 적절한 사용법 습득

## 방식
- `1커밋 - 1학습` 을 원칙으로, 학습 단위별로 커밋을 실시.
- 1커밋과 함께, 새로 배운 내용들을 README로 정리
- 단, 커밋 단위가 너무 커질경우에는  
기존의 마지막 커밋 위치와, 새로 알게된 내용의 시작점 사이 코드까지 따로 커밋 가능하도록 허용

## 스타일
커밋은 `[#000]구현기능_핵심사용기술`과 같은 형식으로 진행한다.  
ex) [028]캐릭터조회_Retrofit 

학습내용은 `[#000]구현기능_핵심사용기술`과 같은 형식으로 파일명을 생성한다.  
ex) [028]캐릭터조회_Retrofit 

## 진행
커밋No | 구현 | 사용 기술 | 완료일 (학습내용 링크) 
:---: | :---: | :---: | :---: | 
 | 001 | 스타일설정 | themes.xml | [21.04.17](./studycontent/[001]스타일설정_themes.xml.md)
 | 002 | 멀티모듈 | multimodule | [21.04.18](./studycontent/[002]멀티모듈_multimodule.md)
 | 003 | DashboardActivity 화면구성 (1) | MotionLayout | [21.04.21](./studycontent/[003]DashboardActivity화면구성(1)_MotionLayout.md)
 | 004 | BaseActivity 최초 구현 | WindowInsetsController / SystemUiVisibility | [21.05.17](./studycontent/[004]BaseActivity생성_StatusBar.md)
 | 005 | Koin 및 Retrofit 연결 | dependencies.gradle / Koin / Retrofit | [21.06.07](./studycontent/[005]Koin+Retrofit_연결.md)
 | 006 | Search Repository 구현 | Coroutine Flow / Retrofit / Clean Architecture | [21.06.07](./studycontent/[006]Search_Repository_최초구현_Coroutine.md)
 | 007 | Character 검색의 최소 단위 생성 | UseCase | [21.06.09](./studycontent/[007]SearchCharacter의_최소단위_생성_UseCase.md)
 | 008 | Search ViewModel 생성 및 Observing | ViewModel / Corountine / CoroutineExceptionHandler / BigDecimal | [21.06.17](./studycontent/[008]Presentation_Layer와_Domain_Layer의_연결_및_Observing.md)
 | 009 | RecyclerView 초기설정 | Kiel Library / Kotlin Extensions | [21.07.06](./studycontent/[009]Kiel_Library를_이용한_RecyclerView_초기_설정.md)