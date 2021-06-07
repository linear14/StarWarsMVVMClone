package com.ldh.data_remote.models

import com.squareup.moshi.Json

/*
    field를 붙이는 이유: moshi 라이브러리의 코틀린 지원 이슈
    moshi-kotlin 사용하면 되는데, 이 기능 하나때문에 추가하기엔 너무 무거움
    그래서 field 넣어주는건데, 아마 몇 년 전 이슈라서 떼도 상관 없을 것 같음.
    나중에 테스트해보기
    https://github.com/square/moshi/issues/315
 */
data class CharacterResponse(
    val name: String,
    @field:Json(name = "birth_year") val birthYear: String,
    val height: String,
    val url: String
)
