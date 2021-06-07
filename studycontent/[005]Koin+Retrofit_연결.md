# [005]Koin + Retrofit 연결
## 진행
- Koin / Retrofit / Moshi 의존성 추가
- Koin 사용하여 Retrofit 빌더 및 서비스 생성 모듈 제작

## 학습내용 1
```
📌 dependencies.gradle
```

### 설명
`dependencies.gradle` 파일을 생성하여 전체 모듈에서 사용되는 의존성들을 관리할 수 있다. (꽤나 편한 것 같음) 상단에는 각 라이브러리마다 version을 기록해둔다. 하단에는 의존성들을 모은 배열을 정의해둔다.

본 프로젝트에서는 appDependencies, dataDependencies, domainDependencies로 나누었는데, 최초 모듈이 그렇게 나누어져 있어서 그런 것 같다.
```gradle
ext {
    koinVersion = '2.1.5'

    appDependencies = [
            koinAndroid           : "org.koin:koin-android:$koinVersion",
            koinLifeCycleScope    : "org.koin:koin-android-scope:$koinVersion",
            koinAndroidViewModel  : "org.koin:koin-androidx-viewmodel:$koinVersion"
    ]

    dataDependencies = [ ... ]
    ...

```

### 사용법
우선, project단위의 gradle에 아래 코드를 추가해줘야 한다.
```gradle
buildscript {
    apply from: 'dependencies.gradle'
    ...
}
```
사용 준비가 완료됐다.

각 모듈에서의 의존성을 추가하기 위해 아래와 같이 사용하면 된다.
```gradle
dependencies {
    implementation appDependencies.koinAndroid
    implementation appDependencies.koinAndroidViewModel
}
```

<br>


## 학습내용2
```
📌 Koin 모듈 제작 with Retrofit
```
### 설명
module을 한 파일 내에서 통합적으로 만든적은 있었는데, 구조적으로 만들어본적은 없었다.   

본 프로젝트에서는 기능별 모듈을 나누었다. Retrofit을 사용하여 네트워크 통신을 위한 모듈은 아래와 같다.

```kotlin
val metworkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), "api주소") }
}

internal fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

internal fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}
```
Retrofit을 사용하기 위해 `provideRetrofit` 함수를 이용하여 빌드한다. 인자로 OkHttpClient를 받는데, 생성 로직 또한 `provideOkHttpClient`로 구현해준다.  

모듈 제작은 간단하다. module 함수를 사용하여 람다식으로 구현해주면 된다. 위에서는 singleton의 형식으로 OkHttpClient와 Retrofit을 불러왔다. 특히, Retrofit을 불러오는 파라미터로 get()이 들어왔는데, 이것은 Koin이 제공하는 의존성 주입의 방식이다. 주입될 OkHttpClient를 모듈내에서 찾은 뒤, 그 리턴값을 주입해주는 것이다.

Koin을 사용하기 위해, Application을 상속받은 클래스에서 startKoin을 해준다. 아래 코드를 참고하자.
```kotlin
internal class StarwarsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // add android context
            androidContext(this@StarwarsApplication)
            modules(
                networkModule
            )
        }

    }
}
```