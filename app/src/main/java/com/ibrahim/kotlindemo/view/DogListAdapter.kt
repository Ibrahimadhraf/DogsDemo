package com.ibrahim.kotlindemo.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.kotlindemo.R
import com.ibrahim.kotlindemo.databinding.ItemDogBinding
import com.ibrahim.kotlindemo.model.DogBreed
import com.ibrahim.kotlindemo.util.getProgressDrawable
import com.ibrahim.kotlindemo.util.loadImage
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.item_dog.view.*

class DogListAdapter(var dogList: ArrayList<DogBreed>):
    RecyclerView.Adapter<DogListAdapter.DogViewHolder>() ,DogClickListener{
    fun updateDogList(newDogsList: List<DogBreed>) {
        dogList.clear()
        dogList.addAll(newDogsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
           val  layoutInflater=LayoutInflater.from(parent.context)
       // val  view=layoutInflater.inflate(R.layout.item_dog ,parent,false)
        val  view=DataBindingUtil.inflate<ItemDogBinding>(layoutInflater  ,R.layout.item_dog ,parent ,false)
        return DogViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("Respose" , " aftergetis${dogList?.size}")

            return  dogList.size


    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
          holder.view.dog=dogList[position]
        holder.view.listener=this
//var dogUid=dogList[position].uuId
   // holder.view.dogName.text= dogList[position].dogBreed
   // holder.view.lifespan.text= dogList[position].lifeSpan
     //   holder.view.root.setOnClickListener {
          //  val action=ListFragmentDirections.actionDetailsFragment(dogUid =dogUid )

           // Navigation.findNavController(it).navigate(action)
      //  }
       // holder.view.dogImage.loadImage(dogList[position].imageUrl , getProgressDrawable(holder.view.root.context))
    }
    class DogViewHolder(val  view: ItemDogBinding): RecyclerView.ViewHolder(view.root)

    override fun dogClickListener(view: View) {
        val dogUid=view.uuId.text.toString().toInt()
        val action=ListFragmentDirections.actionDetailsFragment(dogUid =dogUid )
         Navigation.findNavController(view).navigate(action)
    }
}