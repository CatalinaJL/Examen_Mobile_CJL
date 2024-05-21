package com.cjl.examen_mobile_cjl

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class AgregarLugar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Variables que llaman a resources
        val lugarRes= resources.getString(R.string.lugar)
        val imagRef = resources.getString(R.string.imagRef)
        val latlong = resources.getString(R.string.latLong)
        val ordenRes = resources.getString(R.string.orden)
        val costoAlojamiento = resources.getString(R.string.costoAlojamiento)
        val costoTraslado = resources.getString(R.string.costoTraslado)
        val comentariosRes= resources.getString(R.string.comentarios)
        val guardarRes = resources.getString(R.string.guardar)


        setContent {
            AgregarUI(lugarRes, imagRef,latlong, ordenRes,
                costoAlojamiento, costoTraslado, comentariosRes, guardarRes)
        }
    }
}


@Composable
fun AgregarUI(
    lugarRes: String, imagRef: String, latLong: String,
    ordenRes: String, costoAlojamiento: String,
    costoTraslado: String, comentariosRes: String,
    guardarRes: String)
{
    val contexto = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(lugarRes)
        TextField(
            value = " ",
            onValueChange ={},
            modifier= Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(imagRef)
        TextField(
            value = " ",
            onValueChange ={},
            modifier= Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(latLong)
        TextField(
            value = " ",
            onValueChange ={},
            modifier= Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(ordenRes)
        TextField(
            value = " ",
            onValueChange ={},
            modifier= Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(costoAlojamiento)
        TextField(
            value = " ",
            onValueChange ={},
            modifier= Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(costoTraslado)
        TextField(
            value = " ",
            onValueChange ={},
            modifier= Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(comentariosRes)
        TextField(
            value = " ",
            onValueChange ={},
            modifier= Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(guardarRes)
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            val intent = Intent(contexto, MainActivity::class.java)
            contexto.startActivity(intent)
        }) {
            Text("Inicio")
        }


    }
}

