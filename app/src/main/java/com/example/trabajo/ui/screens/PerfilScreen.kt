package com.example.trabajo.ui.screens

import android.Manifest
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trabajo.data.session.clearSession
import com.example.trabajo.data.session.setUserPhoto
import com.example.trabajo.data.session.userEmail
import com.example.trabajo.data.session.userName
import com.example.trabajo.data.session.userPass
import com.example.trabajo.data.session.userPhoto
import com.example.trabajo.viewmodel.PerfilViewModel
import com.example.trabajo.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.File



private fun crearUriTemporal(ctx: Context): Uri {
    val dir = File(ctx.cacheDir, "images").apply { mkdirs() } // ← coincide con file_paths.xml
    val file = File.createTempFile("foto_", ".jpg", dir)
    return FileProvider.getUriForFile(ctx, "${ctx.packageName}.fileprovider", file)
}

@Composable
fun AskCameraGalleryPermissionsOnce() {
    val ctx = LocalContext.current
    val perms = if (Build.VERSION.SDK_INT >= 33)
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES)
    else
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {}

    LaunchedEffect(Unit) {
        val falta = perms.any {
            ContextCompat.checkSelfPermission(ctx, it) != PackageManager.PERMISSION_GRANTED
        }
        if (falta) launcher.launch(perms)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()
    var showPass by remember { mutableStateOf(false) }


    val imgVM: PerfilViewModel = viewModel()
    val foto by imgVM.foto.collectAsState()

    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    var cameraUri by remember { mutableStateOf<Uri?>(null) }
    val pickFromGallery = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            imgVM.setDesdeGaleria(uri)
            scope.launch { setUserPhoto(ctx, uri) }  // <— ahora sí, dentro de una corrutina
        }
    }

    var lastPhotoUri by remember { mutableStateOf<Uri?>(null) }

    val takePicture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { ok ->
        if (ok && lastPhotoUri != null) {
            imgVM.setDesdeCamara(lastPhotoUri)
            scope.launch { setUserPhoto(ctx, lastPhotoUri!!) }
        }
    }

    LaunchedEffect(Unit) {
        val n = userName(ctx).firstOrNull()
        val e = userEmail(ctx).firstOrNull()
        val p = userPass(ctx).firstOrNull() // opcional
        val f = userPhoto(ctx).firstOrNull()

        if (!n.isNullOrBlank()) viewModel.onNombreChange(n)
        if (!e.isNullOrBlank()) viewModel.onCorreoChange(e)
        if (!p.isNullOrBlank()) viewModel.onClaveChange(p)

        f?.let { runCatching { Uri.parse(it) }.getOrNull() }
            ?.let { uri -> imgVM.setDesdeGaleria(uri) }
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(0xFF1B0D3F),   // ← fondo del panel (morado/pon el que quieras)
                drawerContentColor   = Color.White,

                ) {

                NavigationDrawerItem(
                    label = { Text("Perfil")},
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary, // Esto sirve para la cosa que dejemos puesta resalte
                        selectedTextColor      = Color.White
                    )
                )
                NavigationDrawerItem(
                    label = { Text("Juegos", color = MaterialTheme.colorScheme.onPrimary) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("games")
                    }
                )
                NavigationDrawerItem(
                    label = {Text("Crear nueva reseña", color = MaterialTheme.colorScheme.onPrimary)},
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("Newgames")
                    }
                )
            }
        }
    ){

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi perfil") },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Text("≡",
                            fontSize = 40.sp) // Con eso podemos tener el estilo hamburguesa para la APP
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { p ->
        AskCameraGalleryPermissionsOnce() // Aqui llamamos a la camarita con la galeria
        Column(
            modifier = Modifier
                .padding(p)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2B1663)),
                modifier = Modifier.size(128.dp)
            ) {
                AsyncImage(
                    model = foto,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { pickFromGallery.launch("image/*") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp)
                ) { Text("Galería") }

                Button(
                    onClick = {
                        val uri = crearUriTemporal(ctx)
                        lastPhotoUri = uri
                        takePicture.launch(uri)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp)
                ) { Text("Cámara") }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2B1663))
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Nombre", style = MaterialTheme.typography.labelMedium, color = Color.White, fontSize = 22.sp)
                    Text(estado.name, color = Color.White)

                    Text("Correo", style = MaterialTheme.typography.labelMedium, color = Color.White, fontSize = 22.sp)
                    Text(estado.correo, color = Color.White)

                    Text("Contraseña", style = MaterialTheme.typography.labelMedium, color = Color.White, fontSize = 22.sp)
                    Row {
                        val masked = "•".repeat(estado.clave.length.coerceAtLeast(4))
                        Text(if (showPass) estado.clave else masked, color = Color.White)
                        Spacer(Modifier.width(8.dp))
                        TextButton(onClick = { showPass = !showPass }) {
                            Text(if (showPass) "Ocultar" else "Ver", color = Color.White)
                        }
                    }
                }
            }

            Button(
                onClick = { navController.navigate("Actperfil") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            ) { Text("Editar datos") }

            Button(
                onClick = {
                    scope.launch {
                        clearSession(ctx)
                    }
                    viewModel.reset()
                    navController.navigate("registro") { popUpTo(0) }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            ) { Text("Cerrar sesión") }
            }
        }
    }
}