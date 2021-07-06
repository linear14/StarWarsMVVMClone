# [009]Kiel Library를 이용한 RecyclerView 초기 설정
## 진행
- kiel 라이브러리 사용하여 Search Result RecyclerView 생성관리
- 여러가지 Extension 생성 (Actiivty, Context, RecyclerView)
- DividerItemDecoration을 활용하여 RecyclerView Divider 구현

## 학습내용 1
```
📌 Kiel Library
```
### 무슨 라이브러리인가?
RecyclerView를 사용하기 위해 Adapter를 구현하는데, 그 내부 코드는 항상 반복적이면서도 양이 꽤 많다. 이러한 BoilerPlate 코드를 개선하는데 도움을 주는 라이브러리다. ViewType 분리 및 DiffUtil 까지 지원하던데 잘 사용하면 상당히 편리할 것 같다.
```gradle
implementation 'io.github.ibrahimyilmaz:kiel:latestVersion'
```

### 사용법

사용법은 Github에 잘 나와있지만, 기본적인 어댑터 구현에 대한 코드 정리는 하고 넘어간다.

```kotlin
adapterOf<CharacterPresentation> {
    diff(
        areItemsTheSame = { old, new -> old === new },
        areContentsTheSame = { old, new -> old.url == new.url }
    )
    register(
        layoutResource = R.layout.item_search,
        viewHolder = ::SearchedCharacterViewHolder,
        onBindViewHolder = { viewHolder, i, character ->
            viewHolder.binding.searchedCharacter = character
            viewHolder.binding.moreInfoArrowImageButton.setOnClickListener {
                onClick(character)
            }
        }
    )
}
```
코드 자체가 상당히 직관적이다. (처음 보자마자 문서없이 이해가 가능할정도였음)  
`adapterOf<T>`에서 T는 리스트 아이템들의 타입을 명시한다. `onBindViewHolder`의 람다식 내부 3번째 파라미터가 그대로 가져다 쓰는것을 볼 수 있다. 또한, `diff()`내부에서 DiffUtil 정의가 이루어짐을 확인할 수 있다.  
평소 Adapter 클래스 내의 `onBindViewHolder()` 에서 데이터를 뷰홀더에 바인딩시키는 작업을 진행하는데, Data Binding과 적절하게 혼용하여 사용한다면 코드 길이를 어마어마하게 줄일 수 있을 것이라 생각한다.  

참고로 adapterOf의 return값은 ListAdapter이다.


## 학습내용 2
```
📌 DividerItemDecoration Custom
```

### Custom이 가능하네?

최근 프로젝트 진행하면서 팀원 중 한분이 DividerItemDecoration을 사용하여 RecyclerView Item 간 Divider를 설정한것을 보았다.  
왜 생각못했는지 모르겠는데, 이게 커스텀이 될거라는 생각은 전혀 못했었다.. 평소 Divider를 xml내부에서 그려놓고, Adapter 내부에서 position에 따라 visibility를 관리하는 것이 습관이 되어서 그런 것 같다.

### 사용 코드
방식은 간단하다.  
Custom에 필요한 drawable xml을 만들어서 setDrawable()로 설정만 해주면 된다.

```kotlin
val itemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation).apply {
    setDrawable(ContextCompat.getDrawable(context, R.drawable.line_divider)!!)
}
addItemDecoration(itemDecoration)
```

## 선배님들의 개발 방식
### 여러가지 확장함수

해당 프로젝트를 클론코딩하면서 확실하게 배워가는건, Extension을 정말 잘 활용하면 깔끔한 프로그램을 만들 수 있겠다는 것이다.  

사실 ContextCompat을 이용하여 색상을 불러오는 코드 자체가 길지는 않지만, 색상을 불러오기 위해 색상코드 이외의 문자들이 나열되어 있는것을 보면... 개인적으로 가독성이 걱정된적이 한두번이 아니었다.  
```kotlin
internal fun Context.loadColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}
```
위의 방식대로 확장함수를 만들어두면 조금 더 가독성 좋은 코드를 작성할 수 있지 않을까?
```kotlin
// 이전 코드
tv1.setTextColor(ContextCompat.getColor(this, R.color.text_gray))

// 개선된 코드
tv1.setTextColor(loadColor(R.color.text_gray))
```

### 이 외에도
쓸데없는 BoilerPlate 코드들을 함수의 형태로 보이지 않는곳에 숨겨둘 수 있다. 이를통해 무엇보다 가독성이 너무너무 좋아진다는 강력한 장점이 존재한다.  

RecyclerView는 프로젝트 전반적으로 가장 많이 쓰이는 View이면서도 준비과정이 상당히 복잡하다. 이러한 부분들도 RecyclerView의 확장함수를 생성해두어 한방에 해결 가능하다.

```kotlin
internal fun RecyclerView.initRecyclerViewWithLineDecoration(context: Context) {
    val linearLayoutManager = LinearLayoutManager(context)
    val itemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation).apply {
        setDrawable(ContextCompat.getDrawable(context, R.drawable.line_divider)!!)
    }

    layoutManager = linearLayoutManager
    addItemDecoration(itemDecoration)
}
```

Toast / Snackbar 등 많이 사용되는 함수들에 대하여 미리 확장함수를 준비해두는 연습을 하면 좋을 것 같다~

## 참고자료
- [Kiel - RecyclerView Adapter Library](https://bacassf.tistory.com/80)
