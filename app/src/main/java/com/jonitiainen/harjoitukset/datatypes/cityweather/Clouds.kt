package com.jonitiainen.harjoitukset.datatypes.cityweather

import com.google.gson.annotations.SerializedName


data class Clouds (

  @SerializedName("all" ) var all : Int? = null

)