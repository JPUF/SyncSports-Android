package com.jlbennett.syncsports


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.jlbennett.syncsports.databinding.FragmentSyncBinding


class SyncFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentSyncBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sync, container, false)

        binding.chatButton.setOnClickListener {
            findNavController().navigate(R.id.action_syncFragment_to_chatFragment)
        }

        return binding.root
    }


}
