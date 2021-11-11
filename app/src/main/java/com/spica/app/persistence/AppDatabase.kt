package com.spica.app.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spica.app.model.banner.BannerData
import com.spica.app.model.user.UserData
import com.spica.app.persistence.dao.BannerDao
import com.spica.app.persistence.dao.UserDao

@Database(entities = [BannerData::class, UserData::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

  abstract fun bannerDao(): BannerDao

  abstract fun UserDao(): UserDao

}