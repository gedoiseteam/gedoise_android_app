package com.upsaclay.common.domain.usecase

import com.upsaclay.common.domain.repository.DrawableRepository

class GetDrawableUriUseCase(private val drawableRepository: com.upsaclay.common.domain.repository.DrawableRepository) {
    operator fun invoke(drawableId: Int) = drawableRepository.getDrawableUri(drawableId)
}