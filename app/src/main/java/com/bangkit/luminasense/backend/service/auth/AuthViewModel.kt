//package com.bangkit.luminasense.backend.service.auth
//
//import com.bangkit.luminasense.backend.service.ApiService
//import com.bangkit.luminasense.backend.service.RegisterRequest
//import com.bangkit.luminasense.backend.service.auth.model.UserModel
//
//import android.content.Context
//import android.content.SharedPreferences
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.bangkit.luminasense.backend.service.LoginRequest
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class AuthViewModel(private val context: Context) : ViewModel() {
//    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
//
//    private val _loginResponse = MutableLiveData<UserModel?>()
//    val loginResponse: LiveData<UserModel?> = _loginResponse
//
//    private val _registerResponse = MutableLiveData<UserModel?>()
//    val registerResponse: LiveData<UserModel?> = _registerResponse
//
//    private val _errorMessage = MutableLiveData<String?>()
//    val errorMessage: LiveData<String?> = _errorMessage
//
//    private val _isLoggedIn = MutableLiveData<Boolean>()
//    val isLoggedIn: LiveData<Boolean> = _isLoggedIn
//
//    init {
//        checkLoginStatus()
//    }
//
//    fun login(email: String, password: String) {
//        val apiService = ApiService.create()
//        val call = apiService.login(LoginRequest(email, password))
//        call.enqueue(object : Callback<UserModel> {
//            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
//                if (response.isSuccessful) {
//                    val user = response.body()
//                    _loginResponse.value = user
//                    _isLoggedIn.value = true
//                    saveLoginStatus(true)
//                    user?.let { saveUserData(it) }
//                } else {
//                    _errorMessage.value = "Login failed"
//                }
//            }
//
//            override fun onFailure(call: Call<UserModel>, t: Throwable) {
//                _errorMessage.value = t.message
//            }
//        })
//    }
//
//    fun register(name: String, email: String, password: String, passwordConfirmation: String) {
//        val apiService = ApiService.create()
//        val call = apiService.register(RegisterRequest(name, email, password, passwordConfirmation))
//        call.enqueue(object : Callback<UserModel> {
//            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
//                if (response.isSuccessful) {
//                    _registerResponse.value = response.body()
//                } else {
//                    _errorMessage.value = "Registration failed"
//                }
//            }
//
//            override fun onFailure(call: Call<UserModel>, t: Throwable) {
//                _errorMessage.value = t.message
//            }
//        })
//    }
//
//    private fun saveLoginStatus(isLoggedIn: Boolean) {
//        sharedPreferences.edit().putBoolean("is_logged_in", isLoggedIn).apply()
//    }
//
//    private fun saveUserData(user: UserModel) {
//        sharedPreferences.edit().putString("user_data", user.toString()).apply()
//    }
//
//    fun checkLoginStatus() {
//        _isLoggedIn.value = sharedPreferences.getBoolean("is_logged_in", false)
//    }
//}
//
