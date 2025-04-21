package com.servin.trainify.profile.usecase

import android.net.Uri
import com.servin.trainify.profile.domain.repository.ProfileRepository

class UploadPhotoUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(uri: Uri): String? {
        return repository.updateUserPhoto(uri)
    }
}