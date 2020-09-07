package com.ibrahim.kotlindemo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ibrahim.kotlindemo.R
import com.ibrahim.kotlindemo.viewModel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment() {
   private lateinit var detailsViewModel :DetailViewModel

private  var dogUid=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsViewModel= ViewModelProviders.of(this).get(DetailViewModel::class.java)
        detailsViewModel.fetch()
        observeDogsDetailsViewModel()
        arguments?.let {
            dogUid=DetailsFragmentArgs.fromBundle(it).dogUid


        }

    }
    private fun observeDogsDetailsViewModel(){
        detailsViewModel.dogDetails.observe(viewLifecycleOwner , Observer {dogs->
            dogName.text=dogs.dogBreed
            dogTemperament.text=dogs.temperament
            dogPurpose.text=dogs.bredFor
            lifeSpan.text=dogs.lifeSpan

        })

    }
}