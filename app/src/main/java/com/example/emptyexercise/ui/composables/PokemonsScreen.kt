package com.example.emptyexercise.ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.emptyexercise.R
import com.example.emptyexercise.ui.models.PokeCard
import com.example.emptyexercise.ui.models.ViewState
import com.example.emptyexercise.ui.theme.BackgroundGradiant
import com.example.emptyexercise.ui.theme.ThemeColors

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PokemonScreen(modifier: Modifier = Modifier, viewState: ViewState) {
    var cardToFeature by remember { mutableStateOf<PokeCard?>(null) }
    val showFeaturedCard by remember { derivedStateOf { cardToFeature != null } }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = BackgroundGradiant),
    ) {
        AnimatedContent(
            targetState = viewState,
            transitionSpec = {
                ContentTransform(
                    sizeTransform = null,
                    targetContentEnter = slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up),
                    initialContentExit = slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up)
                )
            }
        ) {
            when (it) {
                is ViewState.Error -> {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(R.string.an_error_occurred))
                        Text(text = it.message ?: "😵")
                    }
                }

                ViewState.Loading -> {
                    val spin = rememberInfiniteTransition().animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(2000, easing = LinearEasing)
                        )
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            modifier = Modifier.rotate(spin.value),
                            model = "https://www.kombatcards.co.uk/storage/2022/09/Pokeback-1-350x350.png",
                            contentDescription = ""
                        )
                    }
                }

                is ViewState.PokemonFound -> {
                    CardsGrid(
                        modifier = Modifier.fillMaxSize(),
                        cards = it.cards,
                        onClick = { cardToFeature = it }
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = showFeaturedCard,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            cardToFeature?.let {
                FeaturedCard(
                    pokeCard = it,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ThemeColors.background.copy(alpha = .8f))
                        .clickable { cardToFeature = null }
                )
            }
        }
    }
}

@Composable
fun CardsGrid(
    modifier: Modifier = Modifier,
    cards: List<PokeCard>,
    onClick: (PokeCard) -> Unit
) {
    Box(modifier = modifier) {
        // this is liable to cause some frame drops on debug builds
        LazyVerticalGrid(columns = GridCells.Fixed(4)) {
            items(cards, key = { it.id }) { card ->
                Card(
                    modifier = Modifier.clickable { onClick(card) },
                    pokeCard = card
                )
            }
        }
    }
}

@Composable
fun Card(
    modifier: Modifier = Modifier,
    pokeCard: PokeCard
) {
    val context = LocalContext.current

    // this builder allows us to first load a low-res thumbnail that fades into a higher resolution.
    // this lightens the load a bit when the user is scrolling through the list

    val request = remember {
        ImageRequest.Builder(context)
            .data(pokeCard.smallImageUrl)
            .placeholderMemoryCacheKey(pokeCard.smallImageUrl)
            .crossfade(true)
            .build()
    }
    AsyncImage(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        model = request,
        contentDescription = pokeCard.name
    )
}

@Composable
fun FeaturedCard(
    modifier: Modifier = Modifier,
    pokeCard: PokeCard
) {
    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = ThemeColors.onSurface.copy(alpha = .5f),
                modifier = Modifier
                    .background(ThemeColors.surface, shape = CircleShape)
                    .padding(8.dp)
            )
        }

        if (isError) {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                Text(stringResource(R.string.this_content_could_not_be_loaded))
            }
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                onSuccess = { isLoading = false },
                onLoading = { isLoading = true },
                onError = {
                    isError = true
                    isLoading = false
                },
                modifier = Modifier.fillMaxWidth(fraction = .8f),
                model = pokeCard.largeImageUrl,
                contentDescription = pokeCard.name
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White,
                text = stringResource(
                    R.string.art_by_artist_name,
                    pokeCard.name,
                    pokeCard.artist

                )
            )
        }
    }
}
