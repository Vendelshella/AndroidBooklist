package com.example.androidbooklist.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbooklist.R
import com.example.androidbooklist.data.BooksData.BookItem
import com.example.androidbooklist.navigation.AppScreens

@Composable
fun filterBooks(books: List<BookItem>, genre: String, page: Int): List<BookItem> {
    val filteredBooks = remember (genre, page) {
        books.filter {
            if (genre.isEmpty())
                true
            else
                it.book.genre == genre
        }.filter { it.book.pages <= page }
    }
    return filteredBooks
}

@Composable
fun MakeGrid(
    books: List<BookItem>,
    selectedGenre: String,
    genresFilter: List<String>,
    onGenreSelected: (String) -> Unit,
    selectedPage: Int,
    pagesFilter: List<Int>,
    onPageSelected: (Int) -> Unit,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                ShowMainHeader(navController)
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShowFilterPagesHeader(stringResource(R.string.text_filter_pages))
                    MakeSlider(
                        pagesToFilter = pagesFilter,
                        currentPage = selectedPage,
                        onPageSelected = onPageSelected
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShowFilterPagesHeader(title = stringResource(R.string.text_filter_genres))
                    MakeDropdownMenu(
                        currentGenre = selectedGenre,
                        genresToFilter = genresFilter,
                        onGenreSelected = onGenreSelected
                    )
                }
            }
            Row {
                ShowNumberOfBooks(bookList = books)
            }
            PrintBooks(
                bookList = books,
                navController = navController
            )
        }
    }
}


@Composable
private fun ShowMainHeader(navController: NavController) {
    Button (
        onClick = {
            navController.navigate(
                route = AppScreens.ReadingListScreen.route
            )
        },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.text_my_library),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ShowNumberOfBooks(bookList: List<BookItem>) {
    Text(
        text = stringResource(R.string.text_books_available, bookList.size),
        style = MaterialTheme.typography.headlineLarge,
        fontStyle = FontStyle.Italic,
        modifier = Modifier.padding(top = 8.dp)
    )
}


@Composable
fun ShowFilterPagesHeader(title: String){
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun MakeSlider(
    currentPage: Int,
    pagesToFilter:List<Int>,
    onPageSelected: (Int) -> Unit) {

    val firstValue = pagesToFilter.first()
    val lastValue = pagesToFilter.last()
    var sliderPosition by remember { mutableIntStateOf(currentPage) }
    val customRange: ClosedFloatingPointRange<Float> = firstValue.toFloat()..lastValue.toFloat()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
            .padding(8.dp)
    ) {
        Text(text = sliderPosition.toString(), textAlign = TextAlign.Center)
        Slider(
            value = sliderPosition.toFloat(),
            valueRange = customRange,
            onValueChange = {
                sliderPosition = it.toInt()
                onPageSelected(sliderPosition) },
            modifier = Modifier.padding(top = 16.dp)
        )
    }

}

@Composable
fun MakeDropdownMenu(
    currentGenre: String,
    genresToFilter: List<String>,
    onGenreSelected: (String) -> Unit
) {
    var showDropDown by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
            .clickable { showDropDown = true }
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { showDropDown = true }
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = "Filter by genre"
                )
            }
            Text (
                text = currentGenre.ifEmpty { "Todos" },
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .clickable { showDropDown = true }
            )
            IconButton(
                onClick = { onGenreSelected("") }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
        }
        DropdownMenu(
            expanded = showDropDown,
            onDismissRequest = { showDropDown = false }
        ) {
            genresToFilter.forEach { filter ->
                DropdownMenuItem(
                    text = { Text(filter) },
                    onClick = {
                        onGenreSelected(filter)
                        showDropDown = false
                    }
                )
            }
        }
    }
}

@Composable
fun PrintBooks(bookList: List<BookItem>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 105.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 14.dp)
    ) {
        items(bookList.size) { index ->
            PrintBookCard(
                isbn = bookList[index].book.ISBN,
                title = bookList[index].book.title,
                cover = bookList[index].book.cover,
                navController = navController,
                )
        }
    }
}

@Composable
fun PrintBookCard(
    isbn: String,
    cover: String,
    title: String,
    navController: NavController
) {
    Card (
        modifier = Modifier.padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = { navController.navigate(route = "book_detail_screen/${isbn}") }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(cover)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 2.dp)
                .fillMaxWidth()
                .aspectRatio(1.0f)
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 2.dp)
                .fillMaxWidth(),
            fontSize = 12.sp
        )
    }
}