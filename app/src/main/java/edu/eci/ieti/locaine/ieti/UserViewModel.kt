package edu.eci.ieti.locaine.ieti

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.eci.ieti.locaine.ieti.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepositoryImp: UserRepository
): ViewModel(){

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepositoryImp.getNewUser()
            Log.d("UserViewModel", user.toString())
        }
    }
}