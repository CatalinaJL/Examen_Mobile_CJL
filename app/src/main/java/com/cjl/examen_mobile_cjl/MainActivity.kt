package com.cjl.examen_mobile_cjl

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cjl.examen_mobile_cjl.db.AppDB
import com.cjl.examen_mobile_cjl.db.Lugar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**Clase que va a contener las pantallas
enum class Pantallas{
    INICIO,
    AGREGALUGAR,
    DETALLELUGAR
}**/

//VM
class AppVM: ViewModel(){
    //val pantallaActual= mutableStateOf(Pantallas.INICIO)
}


class MainActivity : ComponentActivity() {
    // Instancia de View Model
    val appVM:AppVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Variables que contienen los recursos a utilizar en LazyColumn
        val costoXNoche= resources.getString(R.string.costoNoche)
        val traslado = resources.getString(R.string.traslado)
        // Llamada a dispatchers para usar BD
        lifecycleScope.launch(Dispatchers.IO) {
            val lugarDao= AppDB.getInstance(this@MainActivity).lugarDao()
            val cantidadRegistros = lugarDao.countRegisters()
            if(cantidadRegistros <1){
                lugarDao.insert(Lugar(0, "Kutmandu", "33,4-15,8", 1, "5000-1000", "3000", "Viaje de largo tiempo"))

            }

        }



        setContent {
           AppUI(appVM, costoXNoche, traslado)
        }
    }
}




@Composable
fun AppUI(appVM: AppVM, costoXNoche:String, traslado:String) {

    val appVM:AppVM = viewModel()
    ListadoLugarUI(appVM, costoXNoche, traslado)

   /** when(appVM.pantallaActual.value){
        Pantallas.INICIO ->{
            ListadoLugarUI(appVM, costoXNoche, traslado)
        }
        Pantallas.AGREGALUGAR -> {
            AgregarLugar()
        }
        Pantallas.DETALLELUGAR ->{
            DetalleLugar()
        }
    }**/

}




@Composable
fun ListadoLugarUI(appVM: AppVM,costoXNoche: String, traslado: String) {

    val contexto = LocalContext.current

    // variables de estado
    val (place, setPlace) = remember {mutableStateOf(emptyList<Lugar>())}

    // funcion de efecto secundario que cmabia contexto de corrutina
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val dao = AppDB.getInstance(contexto).lugarDao()
            setPlace(dao.getAll())
        }
    }
    ////
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        LazyColumn(
            modifier = Modifier
                .padding(30.dp)
                .weight(0.5f)
        ) {
            items(place){ lugar ->
                LugarItemUI(appVM,costoXNoche, traslado)
            }
        }
        Spacer(modifier = Modifier.height(1.dp))
        Button(onClick = {
           // appVM.pantallaActual.value=Pantallas.AGREGALUGAR
            val intent = Intent(contexto, AgregarLugar::class.java)
            contexto.startActivity(intent)
        }) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Agregar",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Agregar Lugar")
        }
    }



}


@Composable
fun LugarItemUI(appVM: AppVM,costoXNoche: String, traslado: String) {

    val contexto = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 25.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column() {
            Text(text = "Nombre Lugar")
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text("$costoXNoche:")
                Text("Acá va el valor de la DB")
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Text("$traslado:")
                Text("Aca va el valor de la DB")
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                IconButton(onClick = {
                    //appVM.pantallaActual.value=Pantallas.DETALLELUGAR
                    val intent = Intent(contexto, DetalleLugar::class.java)
                    contexto.startActivity(intent)
                }) {
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = "Ubicación"
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Editar"
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Borrar"
                    )
                }
            }
        }

    }
}