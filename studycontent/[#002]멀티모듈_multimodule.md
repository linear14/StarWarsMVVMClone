# [#002]멀티모듈_multimodule

## 진행
- 여러가지 Module 추가 (data-local, data-remote, domain)

## 학습내용 1
```
📌 한 프로젝트 내에서 여러 모듈로 프로젝트 관리가 가능함
```

### 개요

여태껏 기본 생성되는 app module만을 사용하여 작업을 해왔었다.  

복잡한 구조의 프로젝트를 진행하다 보면, 한 개의 모듈에 여러 관심사를 주제로 한 개별의 package들을 만들어 관리를 하는 일이 수두룩했다.  
package의 순서가 data-domain-presentation 으로 보장하게 만들기 힘들 뿐더러, 코드 수정을 위해 위치를 찾고, 분석하기도 너무나 번거로웠다. 이는 더 효율적인 방법이 없을까라는 의문을 항상 가지도록 했다.  

그 해결책으로 Multi-Module을 제시할 수 있을 것 같다.

### 소개

`Multi-Module` 을 사용하여 추가적인 Module을 생성하면 발생하는 이점들이 존재한다.

1. 빌드 속도가 빨라짐
2. Unit Test 용이
3. 관심사 분리가 가능해짐

사실 1,2,3 모두 소스 코드가 독립성을 띄게 된다는 맥락에서 보면 당연하게 느껴질 것 같다.

대표적인 Module은 아래와 같다.
- Java Or Kotlin Library
- Android Library
- Phone & Tablet Module

`Java Or Kotlin Library`는 순수 Java / Kotlin 언어만을 사용하는 라이브러리를 만들 때 사용한다. 안드로이드 프레임워크로부터 독립적으로 사용할 때 사용한다.  
`Android Library`는 안드로이드 프레임워크의 기능을 필요로 할 때 사용한다. 가령 AppCompatActivity 혹은 Context등에 접근할 필요가 있을 경우가 그 예가 된다.  
`Phone & Tablet Module`은 자동 생성되는 app Module과 같은 기능을 하게된다. 빌드 시 `.apk`파일을 생성한다.


### 사용법
제일 상위 디렉토리에서 [마우스 우클릭] -> [New] -> [Module] 을 클릭하여, 모듈 선택 창을 띄울 수 있다.  
<img src="img/002_01.jpg" width="400px">

모듈을 선택하고 이름/패키지 설정을 해주면 된다.

생성된 모듈은 기능을 분리한 독립적인 라이브러리로 생각하면 된다. 따라서, 앱에서 내부 코드에 접근하기 위해서는 `gradle`에 의존성을 주입해주어야 한다.

```gradle
implementation project(':domain')
implementation project(':data-local')
implementation project(':data-remote')
```

## 적용

원본 프로젝트에서는 Clean Architecture의 각 Layer를 Module로 분리하려는 의도를 보였다.  
따라서, 아래의 4개의 Module로 나누었다.

1. app Module (Presentation Layer - View, ViewModel)
2. domain Module (Domain Layer - UseCase)
3. data-local Module (Data Layer - Room, Repository)
4. data-remote Module (Data Layer - Retrofit, Repository)

app Module은 Phone & Tablet Module,
data-local Module은 Android Library,
domain, data-remote Module은 Java / Kotlin Module 로 설정되었다.


## 참고자료
- https://medium.com/@kimdohun0104/%EB%AA%A8%EB%86%80%EB%A6%AC%ED%8B%B1%EC%97%90%EC%84%9C-%EB%A9%80%ED%8B%B0-%EB%AA%A8%EB%93%88%EB%A1%9C-%ED%83%88%EC%B6%9C%ED%95%98%EA%B8%B0-android-7d92db03a96
- https://footcode.postype.com/post/3673100