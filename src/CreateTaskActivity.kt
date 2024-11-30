package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.ui.theme.darkColors
import com.example.todo.ui.theme.lightColors
import kotlin.random.Random

class CreateTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecondPreview()
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Preview(showBackground = true)
    @Composable
    fun SecondPreview() {
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
            val dbSystem = TodoDBSystem(appContext)

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
                    .background(color = MaterialTheme.colorScheme.background)
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
                        // Activity Title
                        Text(
                            text = stringResource(id = R.string.create_task_title),
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

                    // Inputs Holder

                    // Inputs

                    var title by remember {
                        mutableStateOf("")
                    }

                    var des by remember {
                        mutableStateOf("")
                    }

                    var blueIsClicked by remember {
                        mutableStateOf(false)
                    }

                    val blueBoxModifier = if (blueIsClicked) {
                        Modifier
                            .border(4.dp, MaterialTheme.colorScheme.outline, shape = CircleShape)
                            .clipToBounds()
                            .clip(CircleShape)
                    } else {
                        Modifier
                            .border(0.dp, Color.Transparent, shape = CircleShape)
                            .clipToBounds()
                            .clip(CircleShape)
                    }

                    var pinkIsClicked by remember {
                        mutableStateOf(false)
                    }

                    val pinkBoxModifier = if (pinkIsClicked) {
                        Modifier
                            .border(4.dp, MaterialTheme.colorScheme.outline, shape = CircleShape)
                            .clipToBounds()
                            .clip(CircleShape)
                    } else {
                        Modifier
                            .border(0.dp, Color.Transparent, shape = CircleShape)
                            .clipToBounds()
                            .clip(CircleShape)
                    }

                    var redIsClicked by remember {
                        mutableStateOf(false)
                    }

                    val redBoxModifier = if (redIsClicked) {
                        Modifier
                            .border(4.dp, MaterialTheme.colorScheme.outline, shape = CircleShape)
                            .clipToBounds()
                            .clip(CircleShape)
                    } else {
                        Modifier
                            .border(0.dp, Color.Transparent, shape = CircleShape)
                            .clipToBounds()
                            .clip(CircleShape)
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f, fill = true),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {

                        OutlinedTextField(

                            value = title,
                            onValueChange = {
                                title = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(68.dp)
                                .border(
                                    4.dp,
                                    MaterialTheme.colorScheme.outline,
                                    shape = CircleShape
                                )
                                .padding(horizontal = 12.dp)
                                .background(Color.Transparent),
                            shape = CircleShape,
                            textStyle = TextStyle(
                                fontFamily = rubik,
                                fontWeight = FontWeight.Normal,
                                fontSize = 22.sp
                            ),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTextColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            placeholder = {
                                Text(
                                    stringResource(id = R.string.enter_task_title),
                                    style = TextStyle(
                                        fontFamily = rubik,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 22.sp,
                                        color = MaterialTheme.colorScheme.onSecondary
                                    )
                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(42.dp))

                        OutlinedTextField(

                            value = des,
                            onValueChange = {
                                des = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(68.dp)
                                .border(
                                    4.dp,
                                    MaterialTheme.colorScheme.outline,
                                    shape = CircleShape
                                )
                                .padding(horizontal = 12.dp)
                                .background(Color.Transparent),
                            shape = CircleShape,
                            textStyle = TextStyle(
                                fontFamily = rubik,
                                fontWeight = FontWeight.Normal,
                                fontSize = 24.sp
                            ),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTextColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            placeholder = {
                                Text(
                                    stringResource(id = R.string.enter_task_des),
                                    style = TextStyle(
                                        fontFamily = rubik,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 22.sp,
                                        color = MaterialTheme.colorScheme.onSecondary
                                    )
                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(42.dp))

                        Text(
                            text = stringResource(id = R.string.choose_task_theme),
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = rubik,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(86.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = blueBoxModifier
                                    .fillMaxHeight()
                                    .aspectRatio(1f)
                                    .background(Color(0xFF3F00FF), shape = CircleShape)
                                    .clickable {
                                        blueIsClicked = !blueIsClicked
                                        redIsClicked = false
                                        pinkIsClicked = false
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Love",
                                    modifier = Modifier
                                        .size(48.dp),
                                    tint = Color.White
                                )
                            }

                            Box(
                                modifier = pinkBoxModifier
                                    .fillMaxHeight()
                                    .aspectRatio(1f)
                                    .background(Color(0xFFFF77FF), shape = CircleShape)
                                    .clickable {
                                        pinkIsClicked = !pinkIsClicked
                                        redIsClicked = false
                                        blueIsClicked = false
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "Love",
                                    modifier = Modifier
                                        .size(48.dp),
                                    tint = Color.White
                                )
                            }

                            Box(
                                modifier = redBoxModifier
                                    .fillMaxHeight()
                                    .aspectRatio(1f)
                                    .background(Color(0xFFFF033E), shape = CircleShape)
                                    .clickable {
                                        redIsClicked = !redIsClicked
                                        blueIsClicked = false
                                        pinkIsClicked = false
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Home,
                                    contentDescription = "Love",
                                    modifier = Modifier
                                        .size(48.dp),
                                    tint = Color.White
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(42.dp))

                    Button(
                        onClick = {
                            if (title != "" || des != "") {
                                if (
                                    (!blueIsClicked && !pinkIsClicked && redIsClicked) ||
                                    (!blueIsClicked && pinkIsClicked && !redIsClicked) ||
                                    (blueIsClicked && !pinkIsClicked && !redIsClicked)
                                ) {
                                    val intent = Intent(appContext, MainActivity::class.java)
                                    val task = Task(
                                        0, title, des, "false", theme =
                                        if (blueIsClicked)
                                            "blue"
                                        else if (pinkIsClicked)
                                            "pink"
                                        else
                                            "red"
                                    )

                                    // add task

                                    dbSystem.createTask(
                                        task.title,
                                        task.des,
                                        task.theme
                                    )

                                    Toast.makeText(
                                        appContext,
                                        appContext.getString(R.string.task_creation_done),
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                    appContext.startActivity(intent)

                                } else {
                                    Toast.makeText(
                                        appContext,
                                        appContext.getString(R.string.empty_data_error),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    appContext,
                                    appContext.getString(R.string.empty_data_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
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
                                text = stringResource(id = R.string.create_task_title),
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
}
