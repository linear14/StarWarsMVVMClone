# [001]스타일설정_themes.xml.md

## 진행
- themes.xml 기본 스타일 설정
- colors.xml 테마 색상 설정
- strings.xml 사전 설정
- dimens.xml 사전 설정
- styles.xml 사용 텍스트 스타일 설정 
- 사용할 이미지 추가

## 학습
```
📌 themes.xml에서 전체 화면에 적용되는 속성을 지정할 수 있음
```

themes.xml에서 기본 색상정도만 정하는 줄 알았는데,  
화면 전체의 default 속성들을 여기서 모두 지정할 수 있음

예를 들어, 아래의 코드는  
`android:colorBackground`, `android:textColor` 의 속성들을 통해  
기본 배경색과 글자색을 정해두고 사용할 수 있는 것이다.
```xml
<style name="AppTheme.NoActionBar" parent="Theme.MaterialComponents.DayNight.NoActionBar">

    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryVariant">@color/colorPrimaryVariant</item>
    <item name="colorPrimaryDark">@color/colorPrimaryVariant</item>    

    ...

    <item name="android:colorBackground">@color/colorBackground</item>
    <item name="android:textColor">@color/colorTextDefault</item>
    
</style>
```