package com.spica.app.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spica.app.model.user.UserData

@Dao
interface UserDao {


  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(user: UserData)


  @Query("DELETE FROM userdata")
  suspend fun deleteUser()


  @Query("SELECT * FROM userdata WHERE did  = 1")
  suspend fun queryUser(): UserData?


  @Query("SELECT * FROM userdata WHERE did  = 1")
   fun queryUserLiveDate(): LiveData<UserData?>

}