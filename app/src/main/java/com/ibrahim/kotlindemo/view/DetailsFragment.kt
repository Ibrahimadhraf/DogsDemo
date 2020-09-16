package com.ibrahim.kotlindemo.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.ibrahim.kotlindemo.R
import com.ibrahim.kotlindemo.databinding.FragmentDetailsBinding
import com.ibrahim.kotlindemo.model.DogPalette
import com.ibrahim.kotlindemo.viewModel.DetailViewModel


class DetailsFragment : Fragment() {
    private lateinit var detailsViewModel: DetailViewModel
    private lateinit var dataBinding:FragmentDetailsBinding
    private var dogUid = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding=DataBindingUtil.inflate(inflater ,R.layout.fragment_details ,container ,false)
        return dataBinding.root
        //inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        arguments?.let {
            dogUid = DetailsFragmentArgs.fromBundle(it).dogUid


        }
        detailsViewModel.fetch(dogUid)
        observeDogsDetailsViewModel()


    }

    private fun observeDogsDetailsViewModel() {
       detailsViewModel.dogDetails.observe(viewLifecycleOwner, Observer { dogs ->
          /*  dogName.text = dogs.dogBreed
            dogTemperament.text = dogs.temperament
            dogPurpose.text = dogs.bredFor
            lifeSpan.text = dogs.lifeSpan
            context?.let {
                dogImages.loadImage(dogs.imageUrl, getProgressDrawable(it))
            }*/
         dogs?.let {
             dataBinding.dog=it
             it.imageUrl?.let {url->
                 setBackgroundColor(url)
             }
         }


        })

    }
    private fun setBackgroundColor(url:String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object :CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                         Palette.from(resource)
                             .generate{palette ->

                                 val intColor=palette?.vibrantSwatch?.rgb ?:0
                                 val imagePalette=DogPalette(intColor)
                                 dataBinding.palette=imagePalette
                             }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }
}