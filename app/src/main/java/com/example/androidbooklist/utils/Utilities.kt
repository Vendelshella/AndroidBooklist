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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbooklist.data.BooksData.BookItem
import com.example.androidbooklist.navigation.AppScreens


/******************************************************************************
 * filterBooks(List<BookItem>, String, Int: List<BookItem>
 *
 * Realiza el filtrado doble de la lista para cuando la UI se actualice.
 *
 *****************************************************************************/
@Composable
fun filterBooks(books: List<BookItem>, genre: String, page: Int): List<BookItem> {
    val filteredBooks = remember(genre, page) {
        books.filter {
            if (genre.isEmpty())
                true
            else
                it.book.genre == genre
        }.filter { it.book.pages <= page }
    }
    return filteredBooks
}

/******************************************************************************
 * MakeGrid(args...)
 *
 * Pinta el grid principal de la UI y llama al resto de Composables
 *
 ******************************************************************************/
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
                ShowMainHeader(bookList = books)
                ShowLibraryIcon(navController)
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShowFilterPagesHeader("Filtrar por páginas")
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
                    ShowFilterPagesHeader(title = "Filtrar por géneros")
                    MakeDropdownMenu(
                        currentGenre = selectedGenre,
                        genresToFilter = genresFilter,
                        onGenreSelected = onGenreSelected
                    )
                }
            }
            PrintBooks(
                bookList = books,
                navController = navController
            )
        }
    }
}

@Composable
private fun ShowLibraryIcon(navController: NavController) {
    IconButton(
        onClick = {
            navController.navigate(
                route = AppScreens.ReadingListScreen.route
            )
        }
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.List,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
    }
}

/******************************************************************************
 * ShowMainHeader(List<BookItem>)
 *
 * Muestra un encabezado con el número de libros disponibles
 *
 ******************************************************************************/
@Composable
fun ShowMainHeader(bookList: List<BookItem>) {
    Text(
        text = "${bookList.size} libros disponibles",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(top = 8.dp)
    )
}

/******************************************************************************
 * ShowFilterHeader(String)
 *
 * Muestra el encabezado del filtrado
 *
 ******************************************************************************/
@Composable
fun ShowFilterPagesHeader(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        textAlign = TextAlign.Center
    )
}

/******************************************************************************
 * MakeSlider(List<Int>)
 *
 * Implementa el Slider que filtra por páginas
 *
 ******************************************************************************/
@Composable
fun MakeSlider(
    currentPage: Int,
    pagesToFilter: List<Int>,
    onPageSelected: (Int) -> Unit
) {

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
                onPageSelected(sliderPosition)
            },
            modifier = Modifier.padding(top = 16.dp)
        )
    }

}

/******************************************************************************
 * MakeDropdownMenu(String, List<String>, (String) -> Unit)
 *
 * Implementa el DropdownMenu que filtra por géneros
 *
 ******************************************************************************/
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
            Text(
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

/******************************************************************************
 * PrintBooks(List<BookItem>)
 *
 * Implementa el LazyVerticalGrid que muestra los libros
 *
 ******************************************************************************/
@Composable
fun PrintBooks(bookList: List<BookItem>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 14.dp)
    ) {
        items(bookList) {
            PrintBookCard(
                name =  it.book.title,
                imageUrl = it.book.cover,
                onClick = {
                    navController.navigate(route = "book_detail_screen/${it.book.ISBN}")
                }
            )
        }
    }
}

/******************************************************************************
 * PrintBookCard(BookItem)
 *
 * Implementa un elemento Card que contiene la imagen y el titulo del libro
 *
 ******************************************************************************/
@Composable
fun PrintBookCard(name: String, imageUrl: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = { onClick() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 2.dp)
                .fillMaxWidth()
                .aspectRatio(1.0f)
        )
        Text(
            text = name,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 2.dp)
                .fillMaxWidth(),
            fontSize = 12.sp
        )
    }
}