package com.jlbennett.syncsports


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jlbennett.syncsports.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentChatBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_chat, container, false)

        return binding.root
    }


}
