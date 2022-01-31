package com.example.easycarpoolapp.fragment.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto

class PostPassengerDetailViewModel : ViewModel(){

    private val repository : PostRepository? = PostRepository.getInstance()

    public var roomInfo : MutableLiveData<ChatRoomDto> = MutableLiveData()


    //방정보를 서버로 부터 받아 roomInfo에 저장
    public fun createRoom(driver : String, passenger : String, driverNickname : String, passengerNickname : String){
        val dto = ChatRoomDto(driver = driver, passenger = passenger, driverNickname = driverNickname, passengerNickname = passengerNickname)
        repository?.createChatRoom(dto, roomInfo)
    }



}