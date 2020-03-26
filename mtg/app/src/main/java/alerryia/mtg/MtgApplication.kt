package alerryia.mtg

import android.app.Application
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MtgApplication : Application() {

    var mtgService: MtgApi? = null

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl("https://api.magicthegathering.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mtgService = retrofit.create(MtgApi::class.java)
    }
}