package edu.eci.ieti.locaine.ieti.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.eci.ieti.locaine.ieti.datasource.loginDto.LoginDto
import edu.eci.ieti.locaine.ieti.datasource.loginDto.TokenDto
import edu.eci.ieti.locaine.ieti.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepository
): ViewModel(){

    fun login(email: String, password: String): LiveData<TokenDto> {
        val token = MutableLiveData<TokenDto>()
        viewModelScope.launch(Dispatchers.IO) {
            var loginDto = LoginDto(email = email, password = password)
            token.postValue(userRepo.loginUser(loginDto = loginDto))

        }
        return token
    }
}