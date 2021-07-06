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
`adapterOf<T>`에서 T의 역할은, 리스트 아이템들의 타입이다. `onBindViewHolder`의 람다식 내부 3번째 파라미터가 그대로 가져다 쓰는것을 볼 수 있다. 또한, `diff()`내부에서 DiffUtil 정의가 이루어짐을 확인할 수 있다.

참고로 adapterOf의 return값은 ListAdapter이다.


## 참고자료
- [Kiel - RecyclerView Adapter Library](https://bacassf.tistory.com/80)
