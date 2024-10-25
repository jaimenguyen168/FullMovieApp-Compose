package com.example.fullmovieapp_compose.profile.data.repo

import android.app.Application
import android.content.SharedPreferences
import com.example.fullmovieapp_compose.R
import com.example.fullmovieapp_compose.profile.domain.repo.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val application: Application,
    private val prefs: SharedPreferences
): ProfileRepository {

    override suspend fun getName(): String =
        prefs.getString("name", "") ?: application.getString(R.string.name)

    override suspend fun getEmail(): String =
        prefs.getString("email", "") ?: application.getString(R.string.email)
}