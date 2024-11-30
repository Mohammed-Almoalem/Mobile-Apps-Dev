package com.example.todo

import android.content.Intent
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.ui.theme.darkColors
import com.example.todo.ui.theme.lightColors
import kotlin.random.Random

class CompletedTasksActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                val dbSystem = TodoDBSystem(this)
                val tasks = dbSystem.getTasks(isDone = true)
                ThirdPreview(tasks, dbSystem) { refresh() }
            }
        }
    }

    private fun refresh() {
        setContent {
            val dbSystem = TodoDBSystem(this)
            val tasks = dbSystem.getTasks(isDone = true)
            ThirdPreview(tasks, dbSystem) { refresh() }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun ThirdPreview(
    tasks_: MutableList<Task> = mutableListOf<Task>(),
    dbSystem: TodoDBSystem = TodoDBSystem(LocalContext.current),
    onRefresh: () -> Unit = {}
) {
    // Home Screen
    val darkTheme = isSystemInDarkTheme()
    MaterialTheme(
        colorScheme = if (darkTheme) {
            darkColors
        } else {
            lightColors
        }
    ) {

        val appContext = LocalContext.current
        var tasks = remember {
            tasks_
        }

        val rubik = FontFamily(
            Font(R.font.rubik_semibold, FontWeight.SemiBold),
            Font(R.font.rubik_regular, FontWeight.Normal),
            Font(R.font.rubik_bold, FontWeight.Bold),
            Font(R.font.rubik_extrabold, FontWeight.ExtraBold),
            Font(R.font.rubik_light, FontWeight.Light),
        )

        // outer Container

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .safeGesturesPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // inner Container

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 27.dp)
                    .padding(top = 10.dp)
                    .safeGesturesPadding() // prevent drawing content underneath the status or navigation bars
                ,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Head
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.4f)
                        .background(Color.Transparent),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Frame Title
                    Text(
                        text = stringResource(id = R.string.completed_tasks_title),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = rubik,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    // Back Button

                    Button(
                        onClick = {
                            val intent = Intent(appContext, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            appContext.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        contentPadding = PaddingValues(0.dp) // Zero so that we will be able to change the size of the icon
                    ) {
                        Icon(
                            imageVector = (
                                    if (LocalLayoutDirection.current == LayoutDirection.Ltr) {
                                        Icons.Default.ArrowForward
                                    } else
                                        Icons.Default.ArrowBack
                                    ),
                            contentDescription = "Add a new Task",
                            modifier = Modifier
                                .size(48.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f, fill = true),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {

                    if (tasks.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.empty_completed_tasks),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontFamily = rubik,
                                fontWeight = FontWeight.Normal,
                                fontSize = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center

                        ) {
                            Image(
                                painter = painterResource(
                                    id = if (darkTheme) {
                                        R.drawable.emptydark
                                    } else {
                                        R.drawable.empty
                                    }
                                ),
                                contentDescription = "empty bear",
                                modifier = Modifier
                                    .size(128.dp)
                            )
                        }

                    } else

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent)
                        ) {
                            items(tasks.size) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(84.dp)
                                        .background(Color.Transparent),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        tasks[it].title,
                                        style = TextStyle(
                                            fontSize = 22.sp,
                                            fontFamily = rubik,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth(.7f)
                                    )

                                    Button(
                                        modifier = Modifier
                                            .border(
                                                4.dp,
                                                MaterialTheme.colorScheme.outline,
                                                shape = CircleShape
                                            ),
                                        onClick = {
                                            dbSystem.setDone(false, tasks[it].id)
                                            tasks.removeAt(it)
                                            onRefresh()
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Transparent,
                                            contentColor = MaterialTheme.colorScheme.onPrimary
                                        )
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.undo),
                                            style = TextStyle(
                                                fontSize = 17.sp,
                                                fontFamily = rubik,
                                                fontWeight = FontWeight.Normal,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                        )
                                    }

                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                HorizontalDivider(
                                    color = Color(
                                        225,
                                        225,
                                        225
                                    )
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                            }
                        }

                }

                Spacer(modifier = Modifier.height(42.dp))

                Button(
                    onClick = {
                        dbSystem.clearTasks()
                        tasks.clear()
                        onRefresh()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.5f)
                        .border(4.dp, MaterialTheme.colorScheme.outline, shape = CircleShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.clear_tasks),
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontFamily = rubik,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

            } // close inner Container

        } // close outer Container
    }
}
