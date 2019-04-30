package sk.cll.masterdetail.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import sk.cll.masterdetail.db.KUser

interface KUserService {
    @GET("api")
    fun getNewUsers(@Query("ext") ext: Boolean, @Query("amount") limit: Int): Call<List<KUser>>
}