package com.jlbennett.syncsports


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.jlbennett.syncsports.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentChatBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_chat, container, false)

        val args: ChatFragmentArgs by navArgs()
        val matchTime = args.matchTime

        Log.i("chatTime", "MATCH ${matchTime.state} -- ${matchTime.minutes}:${matchTime.seconds}")


        return binding.root
    }


}
