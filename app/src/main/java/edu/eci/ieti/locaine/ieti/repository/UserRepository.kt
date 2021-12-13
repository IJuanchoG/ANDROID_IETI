package edu.eci.ieti.locaine.ieti.repository

import android.util.Log
import androidx.lifecycle.LiveData
import edu.eci.ieti.locaine.ieti.datasource.RestDataSource
import edu.eci.ieti.locaine.ieti.datasource.RestLocaineDataSource
import edu.eci.ieti.locaine.ieti.datasource.loginDto.LoginDto
import edu.eci.ieti.locaine.ieti.datasource.loginDto.TokenDto
import edu.eci.ieti.locaine.ieti.model.User
import edu.eci.ieti.locaine.ieti.model.UserDao
import kotlinx.coroutines.delay
import javax.inject.Inject

interface UserRepository {

    suspend fun getNewUser(): User
    suspend fun deleteUser(toDelete: User)
    suspend fun loginUser(loginDto: LoginDto): TokenDto
    fun getAllUser(): LiveData<List<User>>
}

class UserRepositoryImp @Inject constructor(
    private val dataSource: RestDataSource,
    private val dataLocaineSource: RestLocaineDataSource,
    private val userDao: UserDao
): UserRepository {
    override suspend fun getNewUser(): User {

        delay(2000)
        val name = dataSource.getUserName().results[0].name!!
        val location = dataSource.getUserLocation().results[0].location!!
        val picture = dataSource.getUserPicture().results[0].picture!!
        val user = User(name.first, name.last, location.city, picture.thumbnail, "","")
        userDao.insert(user)
        return user
    }

    override suspend fun deleteUser(toDelete: User) =  userDao.delete(toDelete)

    override suspend fun loginUser(loginDto: LoginDto): TokenDto {
        return dataLocaineSource.getTokenAuth(loginDto)
    }


    override fun getAllUser(): LiveData<List<User>> = userDao.getALl()

}