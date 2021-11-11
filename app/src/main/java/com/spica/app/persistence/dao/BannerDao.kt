package com.spica.app.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spica.app.model.banner.BannerData

@Dao
interface BannerDao {


  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertBanner(banners: List<BannerData>)


  @Query("SELECT * FROM bannerdata")
  suspend fun getBanners(): List<BannerData?>


  @Query("DELETE FROM bannerdata")
  suspend fun deleteAllBanner()


}