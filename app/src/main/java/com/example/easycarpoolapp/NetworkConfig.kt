package com.example.easycarpoolapp

class NetworkConfig private constructor(){
    companion object{

        private val ip = "172.30.1.53"
        private val socketURL = "http://172.30.1.53:8080/mobileServer/websocket"

        public fun getIP() = ip
        public fun getSocketURL() = socketURL


    }

}