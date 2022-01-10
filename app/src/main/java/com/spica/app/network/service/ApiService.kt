package com.spica.app.network.service

import com.skydoves.sandwich.ApiResponse
import com.spica.app.model.YDateList
import retrofit2.http.GET


@Suppress("unused")
interface ApiService {


  // get dateList
  @GET("datelist")
  suspend fun getDateList(): ApiResponse<YDateList>


}