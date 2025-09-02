package com.practice.designsystem

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import cmp_app.composeapp.generated.resources.Res
import cmp_app.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@Composable
fun IconTst(){
    Icon(painter = painterResource(Res.drawable.compose_multiplatform), contentDescription = null)
}