package id.aejeky.data.local

import android.content.Context

class SessionManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        "jeky_session",
        Context.MODE_PRIVATE
    )

    fun saveLoginSession(
        token: String,
        customerId: Long,
        name: String,
        email: String?,
        noHp: String?
    ) {
        sharedPreferences.edit()
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .putString(KEY_TOKEN, token)
            .putLong(KEY_CUSTOMER_ID, customerId)
            .putString(KEY_NAME, name)
            .putString(KEY_EMAIL, email)
            .putString(KEY_NO_HP, noHp)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getToken(): String {
        return sharedPreferences.getString(KEY_TOKEN, "") ?: ""
    }

    fun getCustomerId(): Long {
        return sharedPreferences.getLong(KEY_CUSTOMER_ID, -1L)
    }

    fun getName(): String {
        return sharedPreferences.getString(KEY_NAME, "") ?: ""
    }

    fun getEmail(): String {
        return sharedPreferences.getString(KEY_EMAIL, "") ?: ""
    }

    fun getNoHp(): String {
        return sharedPreferences.getString(KEY_NO_HP, "") ?: ""
    }

    fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_TOKEN = "token"
        private const val KEY_CUSTOMER_ID = "customer_id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_NO_HP = "no_hp"
    }
}