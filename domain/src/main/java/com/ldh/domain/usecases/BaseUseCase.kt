package com.ldh.domain.usecases

// UseCase는 행동의 최소 단위입니다.

// P : parameter, R : result
// in은 읽기전용, out은 쓰기전용?
interface BaseUseCase<in P, out R> {
    suspend operator fun invoke(params: P): R
}