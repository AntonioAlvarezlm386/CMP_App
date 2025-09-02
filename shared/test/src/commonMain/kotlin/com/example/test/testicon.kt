package com.example.test

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import cmp_app.composeapp.generated.resources.Res
import cmp_app.composeapp.generated.resources.compose_multiplatform
import cmp_app.composeapp.generated.resources.ic_android
import org.jetbrains.compose.resources.painterResource

@Composable
fun Icontesttest(){
    Icon(painter = painterResource(Res.drawable.ic_android), contentDescription = null)
}



