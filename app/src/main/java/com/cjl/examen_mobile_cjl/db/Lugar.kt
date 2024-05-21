package com.cjl.examen_mobile_cjl.db

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lugar(
    @PrimaryKey(autoGenerate = true) val id:Int,
    var lugar:String,
    //var ImgRef: Uri,
    var latitudLongitud:String,
    var orden: Int,
    var costoAlojamiento:String,
    var costoTraslados: String,
    var comentarios: String

)
