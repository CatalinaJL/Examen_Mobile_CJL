package com.cjl.examen_mobile_cjl

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.cjl.examen_mobile_cjl.db.AppDB
import com.cjl.examen_mobile_cjl.db.Lugar
import com.cjl.examen_mobile_cjl.ui.theme.Examen_Mobile_CJLTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class DetalleVM : ViewModel(){
    // Variable para contener permisos de ubicación
    var onPermisoCamaraOk: () -> Unit = {}

    val latitud = mutableStateOf(0.0)
    val longitud = mutableStateOf(0.0)

}
class DetalleLugar : ComponentActivity() {

   val detalleVm:DetalleVM by viewModels()
    // instanciador launcher controller de CameraX
    lateinit var cameraController: LifecycleCameraController
    // Permisos CameraX
    val lanzadorPermisosCamara = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){
        if( it[android.Manifest.permission.CAMERA]?:false){
            detalleVm.onPermisoCamaraOk
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Variables que contienen los recursos a utilizar en LazyColumn
        val costoXNoche= resources.getString(R.string.costoNoche)
        val traslado = resources.getString(R.string.traslado)
        val comentariosRes= resources.getString(R.string.comentarios)

        //Se agregan elementos relacionados con el control de la camara para su uso
        cameraController = LifecycleCameraController(this)
        //metodo para vincular al ciclo de vida de la actividad la camara
        cameraController.bindToLifecycle(this)
        // Se deja definido la camara a usar por default
        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        // Llamada a dispatchers para usar BD
        lifecycleScope.launch(Dispatchers.IO) {
            val lugarDao= AppDB.getInstance(this@DetalleLugar).lugarDao()
            val cantidadRegistros = lugarDao.countRegisters()
            if(cantidadRegistros >1){
                lugarDao.getAll()
            }

        }

        setContent {
            DetalleUI(detalleVm, lanzadorPermisosCamara, costoXNoche, traslado, comentariosRes)
        }
    }
}


@Composable
fun DetalleUI(detalleVM:DetalleVM, lanzadorPermisosCamara: ActivityResultLauncher<Array<String>>,
              costoXNoche:String, traslado:String, comentariosRes:String) {

    val contexto = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Nombre Lugar", fontSize= 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(5.dp))
        Text("Imagen acá")

        Row {
            Column(){
                Text(costoXNoche, fontSize= 15.sp, fontWeight = FontWeight.Bold)
                Text("Aca va el valor desde la BD")
            }
            Column(){
                Text(traslado, fontSize= 15.sp, fontWeight = FontWeight.Bold)
                Text("Aca va el valor desde la BD")
            }
        }
        Column(){
            Text(comentariosRes, fontSize= 15.sp, fontWeight = FontWeight.Bold)
            Text("info hotel")
        }
        Row {
            IconButton(onClick = {
                lanzadorPermisosCamara.launch(arrayOf(android.Manifest.permission.CAMERA))
            }) {
                Icon(
                    Icons.Filled.AddCircle,
                    contentDescription = "Fotografia"
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
        AndroidView(
            modifier = Modifier.height(100.dp),
            factory={
                MapView(it).apply{
                    setTileSource(TileSourceFactory.MAPNIK)
                    Configuration.getInstance().userAgentValue = contexto.packageName
                    controller.setZoom(15.0)
                }
            },
            update={
                it.overlays.removeIf {true}
                it.invalidate()
                val geoPoint= GeoPoint(detalleVM.latitud.value, detalleVM.longitud.value)
                it.controller.animateTo(geoPoint)

                val marcador = Marker(it)
                marcador.position= geoPoint
                marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                it.overlays.add(marcador)
            }
        )


        Button(onClick = {
            val intent = Intent(contexto, MainActivity::class.java)
            contexto.startActivity(intent)
        }) {
            Text("Inicio")

        }


    }

}



