package com.example.trabajo.data.session

import android.content.Context
import android.net.Uri
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.sessionDataStore by preferencesDataStore(name = "session_prefs")

private object Keys {
    val LOGGED_IN  = booleanPreferencesKey("logged_in")

    val USER_ID    = longPreferencesKey("user_id")
    val USER_NAME  = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")

    val USER_PASS  = stringPreferencesKey("user_pass")

    val USER_PHOTO = stringPreferencesKey("user_photo")
}


fun userId(ctx: Context): Flow<Long> =
    ctx.sessionDataStore.data.map { it[Keys.USER_ID] ?: 0L }

fun userName(ctx: Context): Flow<String> =
    ctx.sessionDataStore.data.map { it[Keys.USER_NAME] ?: "" }

fun userEmail(ctx: Context): Flow<String> =
    ctx.sessionDataStore.data.map { it[Keys.USER_EMAIL] ?: "" }

fun userPass(ctx: Context): Flow<String> =
    ctx.sessionDataStore.data.map { it[Keys.USER_PASS] ?: "" }
fun isLoggedIn(context: Context): Flow<Boolean> =
    context.sessionDataStore.data.map { it[Keys.LOGGED_IN] ?: false }

fun userPhoto(ctx: Context): Flow<String?> =
    ctx.sessionDataStore.data.map { it[Keys.USER_PHOTO] }

suspend fun setUserPhoto(ctx: Context, uri: Uri?) {
    ctx.sessionDataStore.edit { prefs ->
        if (uri == null) prefs.remove(Keys.USER_PHOTO)
        else prefs[Keys.USER_PHOTO] = uri.toString()
    }
}

suspend fun setLoggedIn(context: Context, value: Boolean) {
    context.sessionDataStore.edit { it[Keys.LOGGED_IN] = value }
}

suspend fun setUser(context: Context, id: Long, name: String, email: String, pass: String) {
    context.sessionDataStore.edit {
        it[Keys.USER_ID]    = id
        it[Keys.USER_NAME]  = name
        it[Keys.USER_EMAIL] = email
        it[Keys.USER_PASS]  = pass
    }
}

suspend fun clearSession(context: Context) {
    context.sessionDataStore.edit { it.clear() }
}