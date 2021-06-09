# [007]UseCase를 사용하여 Character 검색의 최소 단위 생성
## 진행
- BaseUseCase 생성
- SearchCharactersUseCase 생성
- usecases 모듈 생성 (Koin)

## 학습내용 1
```
📌 UseCase 생성
```
### UseCase란?
```
정확히 말하면 Application Layer에서 UseCase를 사용하는 것 같다.
하지만, Domain Layer, Application Layer 모두 비지니스 로직을 처리하기 위한 층이기 때문에, 일반적으로 Domain Layer로 기능을 합치고 UseCase역시 이 영역에 구현한다.
```
Domain Layer는 Entity, Repository, Usecase로 구성된다.  
- Entity는 모델 자체로 보면 될 것 같다. 
- Repository는 비슷한 행동들끼리 묶어서 보관하는 공간이다.
- UseCase는 행동 하나하나의 단위라고 보면 된다. (최소단위)

Repositoyr와 UseCase는 어떤점이 다른지 게시글과 관련된 로직들을 만들어보는 예시로 이해해보자. 관련 로직은 [게시글 작성, 수정, 삭제, 좋아요 활성화, 좋아요 비활성화] 등의 기능이 존재할 수 있다.


우선, 비슷한 주제까리 Repository로 묶어본다.
- PostRepository : 게시글 작성, 게시글 수정, 게시글 삭제
- LikeRepository : 좋아요 활성화, 좋아요 비활성화

UseCase는 각 기능들을 최소단위로 나눈 것이다.
- WritePostUseCase
- UpdatePostUseCase
- DeletePostUseCase
- ActivateLikeUseCase
- InActivateLikeUseCase

### 본 프로젝트에서의 구현 과정
본 프로젝트에서는 BaseUseCase를 생성해두었다.
```kotlin
interface BaseUseCase<in Parameter, out Result> {
    suspend operator fun invoke(params: Parameter): Result
}
```
내부 코드는 invoke 연산자 오버로딩을 요구한다. 또한, 인터페이스 자체에 들어온 두개의 제네릭 타입들이 각각 invoke의 파라미터 타입, 리턴 타입으로 사용됨을 알 수 있다.

이후 아래와 같이 사용하여 UseCase 사용 준비를 끝냈다.
```kotlin
typealias SearchCharactersBaseUseCase = BaseUseCase<String, Flow<List<Character>>>

class SearchCharactersUseCase(
    private val searchRepository: ICharacterSearchRepository
) : SearchCharactersBaseUseCase {

    override suspend operator fun invoke(params: String) = searchRepository.searchCharacters(params)
}
```

### 번외 : UseCase를 사용하면 좋은 점?
각 Layer에서는 다른 Layer간의 의존성을 최소화 해야한다. 만약, Presentation Layer의 ViewModel에서 Repository를 그대로 가져다 쓴다면 Data Logic까지를 신경써야하는 문제가 생긴다. 따라서, 이 부분은 UseCase가 담당하겠다는 것이다.

만약, Repository의 내용이 변하거나 사용 방식이 바뀐다면? 의존하고 있는 UseCase쪽에서의 코드 수정만 진행하면 된다. ViewModel은 전혀 영향을 받지 않고, 자신의 관심사에만 집중하면 된다.

아직 와닿지는 않지만, 비슷한 내용의 ViewModel이 있을 경우에도 이미 구현한 UseCase를 재사용만 하면 된다고 한다. 중복된 로직을 작성 할 필요가 없어진다는 것이다.

아래 참고자료는 아키텍처에 대한 설명을 정말 자세히 해놨기 때문에, 읽어보면 좋을 것 같다.


## 참고자료
- [Clean Architecture](https://namget.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-Clean-Architecture)
- [UseCase를 왜 쓰나요?](https://velog.io/@cchloe2311/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-UseCase%EB%A5%BC-%EC%99%9C-%EC%93%B0%EB%82%98%EC%9A%94)