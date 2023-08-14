package com.example.startrekquizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.startrekquizapp.classes.BottomNavItem
import com.example.startrekquizapp.classes.Captain
import com.example.startrekquizapp.classes.Screen
import com.example.startrekquizapp.classes.Starship


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavBar(
                        items = Constants.getNavItems(),
                        navController = navController,
                        onItemClick = { navController.navigate(it.route) }
                    )
                }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    Navigation(navHostController = navController)
                }
            }
        }
    }
}

@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "home") {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navHostController = navHostController)
        }
        composable(route = Screen.QuizScreen.route) {
            QuizScreen(navHostController = navHostController)
        }
        composable(route = Screen.CaptainsScreen.route) {
            CaptainsScreen()
        }
        composable(route = Screen.StarshipsScreen.route) {
            StarshipsScreen()
        }
        composable(route = Screen.ResultsScreen.route + "/{correct}", listOf(
            navArgument("correct") {
                type = NavType.IntType
                defaultValue = 0
            }
        )) {
            val correct: Int? = it.arguments?.getInt("correct")
            correct?.let { corr -> ResultsScreen(correct = corr, navHostController) }
        }
    }
}

@Composable
fun BottomNavBar(items: List<BottomNavItem>, navController: NavController, onItemClick: (BottomNavItem) -> Unit) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(modifier = Modifier) {
        items.forEach { item ->
            val selected = item.name == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = item.icon, contentDescription = item.name)
                        Text(text = item.name, textAlign = TextAlign.Center, fontSize = 10.sp)
                    }
                }
            )
        }
    }

}

@Composable
fun HomeScreen(navHostController: NavHostController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "App logo")
        Text("Star Trek: The Quiz App", fontStyle = FontStyle.Italic, fontSize = 24.sp)
        Text("Read up on the captains and starships of Star Trek, then take a quiz to see how much you've learned!", fontSize = 18.sp)
        Button(onClick = { navHostController.navigate("quiz") }) {
            Text("Engage!")
        }
    }
}

@Composable
fun AnswerItem(index: Int, answer: String, selected: Int, onClick: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected == index, onClick = { onClick(index) })
        Text(answer)
    }
}

@Composable
fun QuizScreen(navHostController: NavHostController) {
    val questions = Constants.getQuestions()
    var currentQuestionIdx by remember { mutableStateOf(0) }
    val currentQuestion = questions[currentQuestionIdx]
    var buttonEnabled by remember { mutableStateOf(false) }
    var correct by remember { mutableStateOf(0) }
    var selected by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Question ${currentQuestionIdx + 1} of 4")
        Text(currentQuestion.question)
        Column {
            currentQuestion.answers.forEachIndexed { index, answer ->
                AnswerItem(index, answer, selected) {
                    selected = it
                    buttonEnabled = true
                }
            }
        }
        Button(
            enabled = buttonEnabled,
            onClick = {
                // Did they pick the right answer?
                if (selected == currentQuestion.correctAnswer) {
                    correct++
                }
                // Determine if we move to the next question or show the results
                if (currentQuestionIdx < 3) {
                    currentQuestionIdx++
                    buttonEnabled = false
                    selected = -1
                } else {
                    navHostController.navigate(Screen.ResultsScreen.withIntArgs(correct))
                }
            }
        ) {
            if (currentQuestionIdx < 3) {
                Text("Next")
            } else {
                Text("Finish")
            }
        }
    }
}

@Composable
fun CaptainListItem(data: Captain) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(data.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(data.description, fontSize = 16.sp)
    }
}

@Composable
fun CaptainsScreen() {
    val captains = Constants.getCaptains()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier) {
            LazyColumn(Modifier.weight(1f)) {
                items(items = captains, key = { it.id }) {
                    CaptainListItem(it)
                }
            }
        }
    }
}

@Composable
fun StarshipListItem(data: Starship) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(data.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(data.registry, fontSize = 16.sp, fontStyle = FontStyle.Italic)
        Text(data.description, fontSize = 16.sp)
    }
}
@Composable
fun StarshipsScreen() {
    val starships = Constants.getStarships()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier) {
            LazyColumn(Modifier.weight(1f)) {
                items(items = starships, key = { it.id }) {
                    StarshipListItem(it)
                }
            }
        }
    }
}

@Composable
fun ResultsScreen(correct: Int, navHostController: NavHostController) {
    val resultComment = Constants.getResultComment(correct)
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
        Text("You got $correct out of 4 right")
        Text(resultComment!!.comment)
        Button(onClick = { navHostController.navigate("quiz") }) {
            Text("Try again")
        }
    }
}