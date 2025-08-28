package com.example.home

import cafe.adriel.voyager.core.screen.Screen
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class HomeScreen : Screen {

    @OptIn(ExperimentalUuidApi::class)
    private val randomId: String
        get() = Uuid.random().toString()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val postListScreen = rememberScreen(SharedScreen.PostList)
        val postDetailsScreen = rememberScreen(SharedScreen.PostDetails(id = randomId))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Home",
                style = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navigator.push(postListScreen) }
            ) {
                Text(
                    text = "To Post List",
                    style = MaterialTheme.typography.button
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navigator.push(postDetailsScreen) }
            ) {
                Text(
                    text = "To Post Details",
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}