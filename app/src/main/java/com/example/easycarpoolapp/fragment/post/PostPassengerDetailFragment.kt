package com.example.easycarpoolapp.fragment.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentPostDetailBinding
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto


//현재 태워주세요게시글에 대한 PostDetail 창 -> nickname변수는 passenger
// 현재 게시글을 선택한 사용자는 Driver 추후 수정여부 판단
class PostPassengerDetailFragment : Fragment() {

    interface Callbacks{
        //방생성후 방 UUID를 서버로 부터 전달받아 Observer패턴에 의해 Fragment이동
        public fun onSendMesageSelected(dto : ChatRoomDto)
    }




    companion object{
        public fun getInstance(item: PostPassengerDto): PostPassengerDetailFragment{
            val bundle = Bundle().apply {
                putSerializable("email", item.email)    //UI에 띄우지 않지만 Message전송을 위해 데이터 저장
                putSerializable("nickname", item.nickname)
                putSerializable("gender", item.gender)
                //putSerializable("rate", item.rate)  추후 기능 추가
                putSerializable("departure", item.departure)
                putSerializable("destination", item.destination)
                putSerializable("date", item.departureDate)
                putSerializable("time", item.departureTime)
                putSerializable("message", item.message)
            }
            return PostPassengerDetailFragment().apply { arguments = bundle }
        }
    }//companion object



    private lateinit var binding : FragmentPostDetailBinding
    private var callbacks : Callbacks? = null
    private val viewModel : PostPassengerDetailViewModel by lazy {
        ViewModelProvider(this).get(PostPassengerDetailViewModel::class.java)
    }

    var email : String? = null
    var nickname : String? = null
    var gender : String? = null
    var departure : String? =null
    var destination : String? = null
    var date : String? = null
    var time : String? = null
    var message : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callbacks = context as Callbacks?

        email = arguments?.getString("email")
        nickname = arguments?.getString("nickname")
        gender = arguments?.getString("gender")
        departure = arguments?.getString("departure")
        destination = arguments?.getString("destination")
        date = arguments?.getString("date")
        time = arguments?.getString("time")
        message = arguments?.getString("message")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_detail, container, false)
        setUI()

        binding.btnSendMessage.setOnClickListener {
            //게시글 작성자에게 메시지 보내기 -> 대화창 생성및 이동
            viewModel.createRoom(driver = LocalUserData.getEmail()!!,
                passenger = email!!,
                driverNickname = LocalUserData.getNickname()!!,
                passengerNickname = nickname!!
                )
        }

        return binding.root
    }//onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.roomInfo.observe(viewLifecycleOwner, Observer {
            callbacks!!.onSendMesageSelected(dto = it)
        })

    }


    private fun setUI(){

        binding.textNickname.text = nickname
        binding.textGender.text = gender
        binding.textDeparture.text = departure
        binding.textDestination.text = destination
        binding.textDate.text = "날짜 : "+date+"  시간 : "+time
        binding.textMessage.text = message

    }//setUI

}