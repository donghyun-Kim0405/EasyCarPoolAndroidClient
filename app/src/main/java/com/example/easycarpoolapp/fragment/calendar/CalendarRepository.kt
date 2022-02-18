package com.example.easycarpoolapp.fragment.calendar

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.OKHttpHelper
import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.post.PostRepository
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class CalendarRepository private constructor(val context : Context){

    private val TAG : String = "CalendarRepository"
    private val BASEURL :String = "http://"+ NetworkConfig.getIP()+":8080"


    companion object{
        private var INSTANCE : CalendarRepository? = null

        fun init(context : Context){
            if(INSTANCE == null){
                INSTANCE = CalendarRepository(context)
            }

        }//init
        fun getInstance() : CalendarRepository?{
            return INSTANCE?:
            throw Exception("Repository sould be initialized")
        }//getInstance()

        //해당 repository를 참조하는 클래스가 소멸될 경우 반드시 onDestroy를 호출
        fun onDestroy(){
            INSTANCE = null
        }//onDestroy
    }//companion object


    //=============================================================================================
    fun getPostData(postItems: MutableLiveData<ArrayList<PostDto>>) {

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(CalendarAPI::class.java)
        val call = api.getPostDataCall(LocalUserDto(email = LocalUserData.getEmail()))
        call.enqueue(object : Callback<ArrayList<PostDto>>{
            override fun onResponse(
                call: Call<ArrayList<PostDto>>,
                response: Response<ArrayList<PostDto>>
            ) {
                val body = response.body()
                if(body!=null){
                    postItems.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<PostDto>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })
    }//getPostData()



}