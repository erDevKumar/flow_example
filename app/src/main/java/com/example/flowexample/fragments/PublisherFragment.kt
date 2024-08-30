package com.example.flowexample.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.flowexample.databinding.FragmentPublisherBinding
import com.example.flowexample.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class PublisherFragment : Fragment(){

    private lateinit var binding: FragmentPublisherBinding


    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentPublisherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
    }


    private suspend fun publishCold(){
        Log.d("publishCold",binding.coldInput.text.toString())
        viewModel.publishCold(binding.coldInput.text.toString())
    }


    private suspend fun publishHot(){
        Log.d("publishHot",binding.hotInput.text.toString())
        viewModel.publishHot(binding.hotInput.text.toString())
    }

    private fun initClickListeners(){
        binding.coldPublish.setOnClickListener {
            lifecycleScope.launch { publishCold() }
        }
        binding.hotPublish.setOnClickListener {
            lifecycleScope.launch { publishHot() }
        }
    }


}
