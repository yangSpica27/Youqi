package com.spica.app.network


import com.skydoves.sandwich.ApiResponse
import com.spica.app.model.YDateList
import com.spica.app.network.service.ApiService
import javax.inject.Inject


/**
 * 网络请求的Client
 */
class AppClient
@Inject constructor(
  private val apiService: ApiService
) {


  suspend fun getDateList(): ApiResponse<YDateList> =
    apiService.getDateList()



}