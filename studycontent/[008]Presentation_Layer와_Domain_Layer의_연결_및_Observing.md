# [008]Presentation Layer와 Domain Layer의 연결 및 Observing
## 진행
- SearchViewModel 생성
- BaseViewModel에서 Coroutine 활용하기
- viewModelScope
- Domain <-> Presentation Mapper 생성
- BigDecimal 사용
- Handle Coroutine Error

## 학습내용 1
```
📌 BaseViewModel with Coroutines
```
### BaseViewModel
본 프로젝트에서는 ViewModel에 기본적으로 들어가야 할 내용들을 추상 클래스로 정의해두었다. 
- 코루틴 사용 시 예외 발생 Handler (추상)
- Coroutine 생성 및 실행을 돕는 함수

공통적으로 사용 될 것 같음을 충분히 공감할 수 있다.

### CoroutineExceptionHandler
```kotlin
abstract val coroutineExceptionHandler: CoroutineExceptionHandler
```
위와 같이 정의되어 있기 때문에, 확장된 클래스에서 이를 구현해준다.

```kotlin
// 구현부
override val coroutineExceptionHandler =  CoroutineExceptionHandler { coroutineContext, throwable ->
    val message = ExceptionHandler.parse(throwable)
    onSearchError(message)
}
```
Coroutine을 사용하면서 발생하는 예외가 throwable을 통해 넘어 오면, 내부 블록에서 적당히 처리해주는 방식 같다. (오류 관련 Toast 메세지를 띄워주거나, LiveData에 기본값을 넣어주는 방법 등으로 처리할 수 있을듯) 코루틴 예외 관련된 학습은 따로 진행하고 후에 정리하도록 하겠다.

물론, 그냥 override만 한다고해서 예외 처리를 자동으로 해주는건 아니다. 구현해둔 CoroutineExceptionHandler를 사용할 코루틴에 등록해줘야한다. 이는 BaseViewModel에 정의된 코루틴 생성 및 실행 함수에 들어가도록 구현하면 된다.
```kotlin
protected fun launchCoroutine(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(coroutineExceptionHandler) {
        block()
    }
}
```

### ViewModelScope?
위의 Coroutine 생성부의 Scope를 확인하면 CoroutineScope가 아닌 `viewModelScope`임을 확인할 수 있다. 안드로이드 개발에 있어 LifeCycle 관리는 굉장히 민감하며, 이는 ViewModel에서도 마찬가지다. ViewModel이 Destroy가 되었을 경우에도 무거운 비동기작업들이 돌아가고 있을 수 있다. 이는 불필요한 작업이다. 만약 ViewModel이 더이상 필요없을 경우에도 비동기작업이 이루어져야 한다면, 애초에 ViewModel이 아닌 WorkManager 등의 환경에서 돌렸어야 한다.

결론적으로, ViewModel이 더이상 유효하지 않을 경우에는, 진행중인 비동기 작업을 모두 종료하면서 소멸되어야 한다는 것이다.
```kotlin
override fun onCleared() { 
    super.onCleared() 
    searchJob.cancel() 
}
```
이 코드를 위해 searchJob 프로퍼티가 필요 없을 경우에도 굳이 만들어야한다. (또한, CoroutineScope에서 코루틴을 생성할 때 context로 SupervisorJob을 추가로 넣어주어야 하는 것 같은데 이 부분또한 공부하게 된다면 다시 정리) 즉, 위 코드를 포함해 여럿 BoilerPlate 코드들이 생설 될 여지가 있는 것이다. (또한, 실수로 관리를 하지 않아서 런타임오류가 발생할 가능성도 존재함)

그래서, viewModelScope가 이를 관리해주겠다는 것이다. 이제 로직 작성에만 집중하면 된다.

## 학습내용 2
```
📌 BigDecimal
```
### 무엇이며, 왜 사용하나?
float 및 double 타입간의 연산, 즉 소수점이 포함된 연산에서의 문제점은 계산 결과에 오차가 발생한다는 것이다. 이것은 이진수의 근사치를 저장했다가 십진수의 표현 방식으로 보여주기 때문이다. 

BigDecimal을 사용하면 십진수로 값을 저장하고 그대로 보여주기 때문에, 계산의 정밀도를 보장한다. 숫자에 예민한 프로그램을 만들 때 유용하다. (속도가 약간 느리다고는 한다.)

### 내용
`precision`은 0이 아닌 첫 수부터 0이 아닌 마지막 수 까지의 자릿수를 의미한다. 01234.560 의 precision은 6이다.

`scale`은 0이 아닌 마지막 소수점까지의 자릿수다. 01234.560 의 scale은 2다. 단, 0.00, 0.0의 scale은 1이다.

`BigDecimal.ZERO`, `BigDecimal.ONE`, `BigDecimal.TEN` 등의 상수가 존재한다.

초기화는 String으로 하는것이 정확하다.
```kotlin
val correctBigDecimal = BigDecimal("0.01")
// val uncorrectBigDecimal = BigDecimal(0.01)
```

내용이 엄청 많기 때문에, 필요할 때 마다 아래의 참고자료를 꼭 읽는걸로! 


## 선배님들의 개발 방식
### 뷰의 Visibility와 관련된 확장함수
```kotlin
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.remove() {
    this.visibility = View.GONE
}
```

### Data와 Error를 하나의 Data Class로 관리
```kotlin
data class DashboardSearchViewState(
    val isLoading: Boolean,
    val error: Error?,
    val searchResult: List<CharacterPresentation>?
)

data class Error(@StringRes val message: Int)
```

## 참고자료
- [Easy Coroutines in Android: viewModelScope](https://tourspace.tistory.com/267)
- [Java, BigDecimal 사용법 정리](https://jsonobject.tistory.com/466)
