package com.newsmvi.ui.main.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.newsmvi.data.model.ArticleDTO
import com.newsmvi.ui.main.intent.MainIntent
import com.newsmvi.ui.main.viewmodel.MainViewModel
import com.newsmvi.ui.main.viewstate.MainViewState


@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.mainIntent.send(MainIntent.getTopRatedNews)
    }

    val data = viewModel.newsState.collectAsStateWithLifecycle()

    when (data.value) {
        is MainViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is MainViewState.Error -> {

        }

        MainViewState.Idle -> {

        }

        is MainViewState.Success -> {
            Scaffold { paddingValues ->
                LazyColumn(modifier = Modifier.padding(paddingValues)) {
                    items((data.value as MainViewState.Success).data) { item: ArticleDTO ->
                        Article(item) {}
                    }
                }

            }

        }
    }
}

@Composable
fun Article(article: ArticleDTO, onItemClick: (ArticleDTO) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(4.dp)
        .clickable {
            onItemClick(article)
        }) {
        Row(modifier = Modifier.height(120.dp)) {
            ArticleImage(urlToImage = article.urlToImage, title = article.title)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                ArticleTitle(title = article.title)
                ArticleDescription(description = article.description)
            }
        }
    }
}

@Composable
fun ArticleImage(urlToImage: String, title: String?) {
    AsyncImage(
        model = urlToImage,
        contentDescription = title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(150.dp)
    )
}

@Composable
fun ArticleTitle(title: String) {
    Text(
        text = title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun ArticleDescription(description: String) {
    Text(
        text = description,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.titleSmall
    )
}