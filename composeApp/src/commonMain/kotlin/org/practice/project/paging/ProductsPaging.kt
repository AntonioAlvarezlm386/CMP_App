package org.practice.project.paging

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.serialization.json.Json

@Composable
fun ProductsPaging(
    name:String,
    onClick : () -> Unit,
    toTabs: ()-> Unit
){
    val viewModel = viewModel(
        initializer = {
            ProductsViewModel(
                api = ProductsApi(
                    httpClient = HttpClient(
                        engine = HttpClientEngineFactory().create()
                    ) {
                        install(Logging) {
                            logger = Logger.SIMPLE
                            level = LogLevel.ALL
                        }
                        install(ContentNegotiation) {
                            json(
                                json = Json { ignoreUnknownKeys = true }
                            )
                        }
                    }
                )
            )
        }
    )


    val state by viewModel.state.collectAsStateWithLifecycle()

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
                Text(
                    modifier = Modifier.clickable{ toTabs() },
                    text = "TAB EXAMPLE",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    ) { contentPadding ->
        val lazyListState = rememberLazyListState()

        LaunchedEffect(state.products) {
            snapshotFlow {
                lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            }
                .distinctUntilChanged()
                .collect { lastVisibleIndex ->
                    if (lastVisibleIndex == state.products.lastIndex) {
                        viewModel.loadNextItems()
                    }
                }
        }


        Spacer(modifier = Modifier.height(10.dp))




        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = contentPadding
        ) {


            items(state.products) { product ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = product.title,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$ ${product.price}"
                    )
                }
            }
            if (state.isLoadingMore) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}