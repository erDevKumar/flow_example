package com.example.flowexample.fragments

import android.graphics.Color
import android.os.Bundle
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
import kotlinx.coroutines.launch

class SubscriberFragment : Fragment(){

    private var coldSubscription: Job?=null
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
    }


    private fun subscribeForCold(){
        coldSubscription=lifecycleScope.launch {
            viewModel.coldFlow.collect{
                binding.coldValue.text=it
            }
        }
    }

}
