package com.ibrahim.kotlindemo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class DogBreed(
    @ColumnInfo(name = "bread_id")
    @SerializedName("id")
    val breadId:String?,
    @SerializedName("name")
    @ColumnInfo(name = "dog_name")
    val dogBreed:String?,
    @SerializedName("life_span")
    @ColumnInfo(name = "life_span")
    val lifeSpan:String?,
    @SerializedName("breed_group")
    @ColumnInfo(name ="breed_group" )
    val  breadGroup:String?,
    @SerializedName("bred_for")
    @ColumnInfo(name = "bred_for")
    val bredFor:String?,
    @SerializedName("temperament")
    val temperament: String?,
    @SerializedName("url")
    @ColumnInfo(name = "dog_url")
    val imageUrl: String?

){
    @PrimaryKey(autoGenerate = true)
    var uuId:Int=0
}