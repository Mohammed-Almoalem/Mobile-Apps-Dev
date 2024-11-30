package com.example.todo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.ui.theme.darkColors
import com.example.todo.ui.theme.lightColors
import kotlin.random.Random
import kotlin.reflect.KClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val dbSystem = TodoDBSystem(this)
            val tasks = dbSystem.getTasks()
            GreetingPreview(dbSystem, tasks) { refresh() }
        }
    }

    override fun onResume() {
        super.onResume()
        setContent {
            val dbSystem = TodoDBSystem(this)
            val tasks = dbSystem.getTasks()
            GreetingPreview(dbSystem, tasks) { refresh() }
        }
    }

    private fun refresh() {
        setContent {
            val dbSystem = TodoDBSystem(this)
            val tasks = dbSystem.getTasks()
            GreetingPreview(dbSystem, tasks) { refresh() }
        }
    }
}

val rubik = FontFamily(
    Font(R.font.rubik_semibold, FontWeight.SemiBold),
    Font(R.font.rubik_regular, FontWeight.Normal),
    Font(R.font.rubik_bold, FontWeight.Bold),
    Font(R.font.rubik_extrabold, FontWeight.ExtraBold),
    Font(R.font.rubik_light, FontWeight.Light),
)

@Composable
fun TaskCard(
    id: Int,
    title: String,
    des: String,
    theme: String,
    isWelcome: Boolean = false,
    onClick: () -> Unit,
    materialTheme: MaterialTheme
) {
    // Task Icon + Task text + Description

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                when (theme) {
                    "blue" -> Color(0xFF3F00FF)
                    "pink" -> Color(0xFFFF77FF)
                    "red" -> Color(0xFFFF033E)
                    else -> Color.Black
                }
            )
            .padding(
                horizontal = 40.dp,
                vertical = 38.dp
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f)
                .background(Color.Transparent)
        ) {
            // Card Icon

            Icon(
                imageVector = when (theme) {
                    "blue" -> Icons.Default.DateRange
                    "pink" -> Icons.Default.FavoriteBorder
                    "red" -> Icons.Default.Home
                    else -> Icons.Default.Warning
                },
                contentDescription = "Card Icon",
                modifier = Modifier
                    .size(86.dp),
                tint = Color.White
            )

            // Task Title

            Text(
                text = title,
                modifier = Modifier
                    .padding(top = 24.dp),
                style = TextStyle(
                    fontSize = 38.sp,
                    fontFamily = rubik,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )

            // Task Description

            Text(
                text = des,
                modifier = Modifier
                    .padding(top = 18.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = rubik,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            )

        }

        // Task Completed Button

        if (!isWelcome)

            Button(
                onClick = onClick,
                modifier = Modifier
                    .width(96.dp)
                    .aspectRatio(1f)
                    .border(4.dp, MaterialTheme.colorScheme.outline, shape = CircleShape),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Task Completed",
                    modifier = Modifier
                        .size(48.dp)
                )
            }
        // **
    } // End Column
//    } // End Material Theme

}

@Preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GreetingPreview(
    dbSystem_: TodoDBSystem = TodoDBSystem(LocalContext.current),
    tasks: MutableList<Task> = mutableListOf<Task>(
        Task(
            id = 0,
            title = "Buy Sofa",
            des = "complete it soon",
            done = "true",
            theme = "red"
        ),
        Task(
            id = 1,
            title = "Drink water",
            des = "better for health",
            done = "true",
            theme = "blue"
        )
    ),
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

        var dbSystem = dbSystem_

        var _tasks = tasks

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .safeGesturesPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Container

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 27.dp)
                    .padding(top = 10.dp)
                    .safeGesturesPadding(),
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
                    // App Title
                    Text(
                        text = stringResource(id = R.string.Title) + " (${_tasks.size})",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = rubik,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    // New Task Button

                    Button(
                        onClick = {
                            val intent = Intent(appContext, CreateTaskActivity::class.java)
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
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add a new Task",
                            modifier = Modifier
                                .size(48.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Main Card

                val pagerState = rememberPagerState {
                    tasks.size
                }

                if (_tasks.isEmpty()) // no tasks

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f, fill = true)
                            .background(Color.Blue, shape = RoundedCornerShape(50.dp))
                            .border(
                                4.dp,
                                MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .clip(shape = RoundedCornerShape(50.dp))
                            .clickable {
                                // Main Card click Event
                            }
                    ) {
                        TaskCard(
                            id = 0,
                            title = stringResource(id = R.string.welcome_title),
                            des = stringResource(id = R.string.welcome_des),
                            theme = "pink",
                            isWelcome = true,
                            onClick = {

                            },
                            materialTheme = MaterialTheme
                        )
                    } // ***********

                else

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f, fill = true)
                            .background(Color.Blue, shape = RoundedCornerShape(50.dp))
                            .border(
                                4.dp,
                                MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .clip(shape = RoundedCornerShape(50.dp))
                            .clickable {
                                // Main Card click Event
                            }
                    ) {
                        TaskCard(
                            id = _tasks[it].id,
                            title = _tasks[it].title,
                            des = _tasks[it].des,
                            theme = _tasks[it].theme,
                            onClick = {
                                dbSystem.setDone(true, _tasks[it].id)
                                onRefresh()
                            },
                            materialTheme = MaterialTheme
                        )
                    } // ***********

                Spacer(modifier = Modifier.height(42.dp))

                Button(
                    onClick = {
                        val intent = Intent(appContext, CompletedTasksActivity::class.java)
                        appContext.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.5f)
                        .border(4.dp, MaterialTheme.colorScheme.outline, shape = CircleShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.completed_tasks_title),
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontFamily = rubik,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                }

            } // close the inner container here

        } // close the outer container here
    }
}
