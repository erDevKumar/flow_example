package com.example.flowexample.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.flowexample.databinding.FragmentSubscriberBinding
import com.example.flowexample.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SubscriberFragment : Fragment(){

    private var coldSubscription: Job?=null
    private var hotSubscription: Job?=null
    private var commonColdSubscription: Job?=null
    private var commonHotSubscription: Job?=null
    private lateinit var binding: FragmentSubscriberBinding

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentSubscriberBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initClickListeners()
    }

    private fun initClickListeners(){
        binding.coldLabel.setOnClickListener {
            if (coldSubscription==null){
                binding.coldLabel.setBackgroundColor(Color.BLUE)
                subscribeForCold()
            }else{
                coldSubscription?.cancel()
                coldSubscription=null
                binding.coldLabel.setBackgroundColor(Color.GRAY)
            }
        }



        binding.hotLabel.setOnClickListener {
            if (hotSubscription==null){
                binding.hotLabel.setBackgroundColor(Color.BLUE)
                subscribeForHot()
            }else{
                hotSubscription?.cancel()
                hotSubscription=null
                binding.hotLabel.setBackgroundColor(Color.GRAY)
            }
        }



        binding.coldHotLabel.setOnClickListener {
            if (commonHotSubscription==null){
                binding.coldHotLabel.setBackgroundColor(Color.BLUE)
                subscribeForBoth()
            }else{
                commonHotSubscription?.cancel()
                commonHotSubscription=null
                commonColdSubscription?.cancel()
                commonColdSubscription=null
                binding.coldHotLabel.setBackgroundColor(Color.GRAY)
            }
        }
    }


    private fun subscribeForCold(){
        coldSubscription=lifecycleScope.launch {
            viewModel.coldFlow?.apply {
                onStart { Log.d("ColdFlow", "onStart") }
                    .onCompletion { Log.d("ColdFlow", "onCompletion") }
                    .onEach { Log.d("ColdFlow", "onEach: $it") }
                    .collect {
                        Log.d(" ColdFlow Collected", it)
                        binding.coldValue.text = it
                    }
            }
        }
    }
    private fun subscribeForHot(){
        hotSubscription=lifecycleScope.launch {
            viewModel.hotFlow
                .onStart { Log.d("HotFlow", "onStart") }
                .onCompletion { Log.d("HotFlow", "onCompletion") }
                .onEach { Log.d("HotFlow", "onEach: $it") }
                .collect {
                    Log.d(" HotFlow Collected", it)
                    binding.hotValue.text = it
                }
        }
    }
    private fun subscribeForBoth(){
        commonHotSubscription=lifecycleScope.launch {
            viewModel.hotFlow
                .onStart { Log.d("CommonHotFlow", "onStart") }
                .onCompletion { Log.d("CommonHotFlow", "onCompletion") }
                .onEach { Log.d("CommonHotFlow", "onEach: $it") }
                .collect {
                    Log.d(" CommonHotFlow Collected", it)
                    binding.coldHotValue.text = it
                }
        }
        commonColdSubscription=lifecycleScope.launch {
            viewModel.coldFlow?.apply {
                onStart { Log.d("CommonColdFlow", "onStart") }
                .onCompletion { Log.d("CommonColdFlow", "onCompletion") }
                .onEach { Log.d("CommonColdFlow", "onEach: $it") }
                .collect {
                    Log.d(" CommonColdFlow Collected", it)
                    binding.coldHotValue.text = it
                }
            }
        }
    }
}


