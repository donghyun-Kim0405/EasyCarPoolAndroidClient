package com.example.easycarpoolapp.fragment.chat

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.databinding.FragmentRequestDriverDialogBinding
import com.example.easycarpoolapp.fragment.post.dto.PostDto

class RequestDriverDialogFragment(val postDto : PostDto) : DialogFragment(){

    private lateinit var binding : FragmentRequestDriverDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestDriverDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        setUI()


        return binding.root
    }//onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImage()
    }

    override fun onResume() {
        super.onResume()
        setDialogSize()

    }// onResume()


    private fun setUI(){
        binding.textNickname.text = postDto.nickname
        binding.textGender.text = postDto.gender
        binding.textDeparture.text = postDto.departure
        binding.textDestination.text = postDto.destination
        binding.textDate.text = "날짜 : "+postDto.departureDate+"  시간 : "+postDto.departureTime
        binding.textMessage.text = postDto.message

    }//setUI

    private fun setImage(){

        Glide.with(this)
            .load("http://"+ NetworkConfig.getIP()+":8080/api/image/profile?email="+postDto.email)
            .into(binding.imageViewProfile) //profile image set

        Glide.with(this)
            .load("http://"+ NetworkConfig.getIP()+":8080/api/image/car?email="+postDto.email)
            .into(binding.imageCar) //profile image set

    }//setImage()


    private fun setDialogSize(){
        val windowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        size.x // 디바이스 가로 길이
        size.y // 디바이스 세로 길이

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        val deviceHeight = size.y
        params?.width = (deviceWidth * 0.8).toInt()
        params?.height = (deviceHeight * 0.8).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }//setDialogSize()
}