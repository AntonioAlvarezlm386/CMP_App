package org.practice.project.navigation.main.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun BottomSheetScreen(
    name: String,
    onClick: () -> Unit
){
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 24.dp)){
                Text(
                    modifier = Modifier.clickable{ onClick() },
                    text = name,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    ) { contentPadding ->
    }

}



@Composable
fun ModalBottom(modifier: Modifier = Modifier) {


//    val scaffoldState = rememberBottomSheetScaffoldState()
//    val scope = rememberCoroutineScope()
//
//    BottomSheetScaffold(scaffoldState = scaffoldState,
//        sheetPeekHeight = 30.dp, sheetContent = {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp), contentAlignment = Alignment.Center
//            ) {
//                Button(onClick = {
//                    scope.launch {
//                        scaffoldState.bottomSheetState.partialExpand()
//                    }
//
//                }) {
//                    Text(text = "Close")
//                }
//            }
//        }) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize(), contentAlignment = Alignment.Center
//        ) {
//            Button(onClick = {
//                scope.launch {
//                    scaffoldState.bottomSheetState.expand()
//                }
//            }) {
//                Text(text = "Open")
//            }
//        }
//    }
}