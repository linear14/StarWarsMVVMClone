# [003]DashboardActivity화면구성(1)_MotionLayout

## 진행
- DashboardActivity 레아아웃 구성 (Search를 위한 레이아웃까지)

## 학습내용 1
```
📌 animateLayoutChanges 속성
```

### 설명
`ViewGroup`내에는 `LayoutTransition`이라는 클래스를 가지고 있다.  
이 클래스를 적절히 사용하면, 자식뷰들이 레이아웃을 변화시키려는 동작을 할 때 마다 특정한 애니메이션 효과를 줄 수 있다.

LayoutTransition에는 2가지 형식이 존재한다.
1. APPEARING / DISAPPEARING : 자식뷰의 Visibility가 변화할 때 실행
2. CHANGE_APPEARING / CHANGE_DISAPPEARING : 자식뷰가 ViewGroup에 추가되거나 삭제될 때 실행

그리고, 이러한 상황에서 애니메이션을 실행하는 코드를 직접 작성해야한다.

코드를 작성하는 것이 귀찮은 작업임을 알고있었는지,  
ViewGroup의 xml 속성을 이용하여 위의 효과들을 간단하게 제어 가능하도록 구성해두었다.

```xml
<ViewGroup
    android:animateLayoutChanges="true">
```
위와같은 설정을 통해, 자식뷰의 Visibility 변화 혹은 자식뷰의 추가/삭제에 대한 애니메이션이 자동으로 실행된다.  
(물론 디테일한 커스텀을 위해서는 직접 코드를 작성해야한다. 아래의 참고자료 링크를 보면서 공부하면 될듯하다.)

## 학습내용 2
```
📌 MotionLayout 내부의 <OnClick>#app:clickAction="toggle"
```
MotionLayout 전체를 포스팅하기에는 너무나 양이 방대하기 때문에..  
몰랐던 부분들만 나올때마다 정리하는 것이 좋을 것 같다.

### 설명
MotionLayout을 정의하기 위한 xml 내부에는, 그 이벤트의 시작과 끝을 정의하는 `<Transition>` 태그가 존재한다.  
그 중에서도.. 어떤 target을 클릭하는 경우 이벤트가 발생하기를 원한다면 `<OnClick>` 태그를 사용한다. 
그 내부의 `app:clickAction`속성을 이용하여 애니메이션 동장 방식을 정의할 수 있는데, 내부 값들은 아래와 같이 존재한다.

- toggle
- jumpToStart/End
- transitionToStart/End

jump 값은 시작 혹은 끝으로 점프하는것을 의미하는데, 어떤 애니메이션을 거치지 않고 바로 끝점으로 이동하게 된다.  
transition 값은 시작 혹은 끝으로 이동하는것은 맞지만, 애니메이션 동작이 발생하게 된다.  
toggle 값은 클릭을 할 때 마다 start->end 혹은 end->start 애니메이션 동작이 번갈아가면서 발생하도록 해준다.

## 참고자료
- https://medium.com/mj-studio/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%95%A0%EB%8B%88%EB%A9%94%EC%9D%B4%EC%85%98-%EB%8F%8C%EC%9E%94%EC%B9%98-part-1-feca04af34dc
- 