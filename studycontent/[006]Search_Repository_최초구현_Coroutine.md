# [006]Search Repository 최초구현_Coroutine
## 진행
- Coroutine 의존성 추가
- Remote Data Source Module 생성 (Koin)
- Retrofit으로 받아올 모델 생성
- Retrofit으로 검색 결과 받아오는 Api 사용 -> Repository 구현
- Remote <-> Domain Mapper 생성

## 학습내용 1
```
📌 본 프로젝트에서의 Repository 생성 패턴
```
레트로핏을 사용하여 원격 데이터를 가져오는 방식을 예로 들어, 본 프로젝트에서 Repository 생성 방식을 공부해본다.  

세가지 모듈의 관점에서 볼 수 있다.
### Remote
레트로핏을 사용하여 서버에 데이터를 요청하는 모듈이다. 따라서, 그 응답에 해당하는 모델을 미리 준비해두어야 값을 받아올 수 있다.

Moshi 라이브러리를 JSON Converter로 사용했기 때문에, 형식에 맞추어 사용해주면 된다.
```kotlin
data class SearchResponse(
    val count: Int,
    val next: Int,
    val previous: Int,
    val results: List<CharacterResponse>
)

data class CharacterResponse(
    val name: String,
    @field:Json(name = "birth_year") val birthYear: String,
    val height: String,
    val url: String
)
```

또한, 이 응답을 위해 호출하는 Retrofit Service가 필요하다. 지연되어 사용 될 예정이기에 suspend 키워드를 붙인다.
```kotlin
// interface StarWarsApiService

@GET("people/")
suspend fun searchCharacters(@Query("search") params: String): SearchResponse
```

```
❓ Repository를 왜 Domain의 인터페이스를 구현하는 방식으로 생성하는지 모르겠다. 이 부분은 충분히 공부 후 다시 작성하도록 하겠다.
```

마지막으로, API를 호출하기 위해 Repository를 만들어준다. 모듈로 분리되어 있는 자체로 Prsenter과 Data간의 Coupling을 느슨하게 만들어주지만, Repository가 존재함으로 그 분리는 확실해진다.  

Repository 생성은 Domain의 인터페이스를 구현하는 방식으로 만들어준다. (아마 이러한 방식때문에 인터페이스 구현하는 것 같은데, Domain에는 Presenter 영역에서 필요한 정보들을 Remote에 요청하는 명세를 Interface 형태로 제공하는 이유로 생각중이다.)

데이터 호출 과정에서는 Coroutine Flow를 사용하여 연속적인 데이터를 emit하도록 했다.
```kotlin
class CharacterSearchRepository(
    private val apiService: StarWarsApiService
): ICharacterSearchRepository {

    override suspend fun searchCharacters(characterName: String): Flow<List<Character>> = flow {
        val searchResponse = apiService.searchCharacters(characterName)
        val starWarsCharacters = mutableListOf<Character>()
        for(starWarsCharacter in searchResponse.results) {
            starWarsCharacters.add(starWarsCharacter.toDomain())
        }
        emit(starWarsCharacters)
    }
}
```

결과를 `toDomain()`을 사용하여 처리하는 것을 볼 수 있다. 이는 Remote에서 받아온 데이터를 Domain의 스타일로 바꿀 필요가 있기 때문이다. (서버에서 받아오는 데이터는 CharacterResponse 이지만, 실질적으로 return해주는 값은 Character 임을 알 수 있다. 이는 Domain 영역에서 사용하는 Model이다.) 따라서, 이를 매핑 시켜주기 위해, 또 다른 Mapper를 생성해준다.

```kotlin
internal fun CharacterResponse.toDomain(): Character {
    return Character(this.name, this.birthYear, this.height, this.url)
}
```


### Domain
도메인 영역에서는 모델로 사용할 데이터 클래스를 만들어준다.
```kotlin
data class Character(
    val name: String,
    val birthYear: String,
    val height: String,
    val url: String
)
```

Presenter가 요구하는 명세를 Remote에 알려주기 위하여, 관련 인터페이스 역시 존재해야한다. (Data Source는 Presenter가 무슨일을 하는지 전혀 몰라야한다. 반대의 경우도 마찬가지)
```kotlin
interface ICharacterSearchRepository {
    suspend fun searchCharacters(characterName: String): Flow<List<Character>>
}
```

### Presenter
제작한 Repository를 사용하기 위해 Koin을 사용하여 모듈 제작을 해준다. 이 때, 인터페이스를 타입 파라미터로 받는 것을 확인하면 될 것 같다.
```kotlin
val remoteDataSourceModule = module {

    single<ICharacterSearchRepository> { CharacterSearchRepository(apiService = get()) }
}
```

## 참고자료
- [안드로이드 Repository 패턴에 대한 고찰](https://vagabond95.me/posts/android-repository-pattern/)
