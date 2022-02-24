package com.example.easycarpoolapp.fragment.location

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentReservedPostDialogBinding
import com.example.easycarpoolapp.fragment.chat.dto.ReservedPostDto

class ReservedPostDialogFragment(val reservedPostDtos : ArrayList<ReservedPostDto>) : DialogFragment() {

    private lateinit var binding : FragmentReservedPostDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservedPostDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        setUI()
        binding.btnCancel.setOnClickListener {
            dismiss()
        }   //다이어로그 제거

        binding.btnSendRequest.setOnClickListener {

        }


        return binding.root
    }//onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }//onViewCreated()

    override fun onResume() {
        super.onResume()
        setDialogSize()
    }//onResume()

    private fun setUI(){
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = ReservedPostAdapter(reservedPostDtos)
    }//setUI()

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

    //===================================================================================================
    inner class ReservedPostHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val date : TextView = itemView.findViewById(R.id.item_date)
        val time: TextView = itemView.findViewById(R.id.item_time)
        val driver: TextView = itemView.findViewById(R.id.item_driver)
        val passenger: TextView = itemView.findViewById(R.id.item_passenger)

        public fun bind(item : ReservedPostDto){
            date.text = date.text.toString() + item.date
            time.text = time.text.toString() + item.time
            driver.text = driver.text.toString() + item.driver
            passenger.text = passenger.text.toString() + item.passenger
        }//bind()


    }//ReservedPostHolder


    inner class ReservedPostAdapter(val items : ArrayList<ReservedPostDto>) : RecyclerView.Adapter<ReservedPostHolder>(){
        override fun onBindViewHolder(holder: ReservedPostHolder, position: Int) = holder.bind(items.get(position))
        override fun getItemCount(): Int = items.size
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservedPostHolder {
            val view = layoutInflater.inflate(R.layout.item_reserved_post_dialog, parent, false)
            return ReservedPostHolder(view)
        }
    }// ReservedPostAdapter

}