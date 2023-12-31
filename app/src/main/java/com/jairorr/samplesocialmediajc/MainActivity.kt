package com.jairorr.samplesocialmediajc

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jairorr.samplesocialmediajc.data.MemberUser
import com.jairorr.samplesocialmediajc.ui.MyScreen
import com.jairorr.samplesocialmediajc.ui.screen.login.LoginViewModel
import com.jairorr.samplesocialmediajc.ui.screen.navigation.MyNavigation
import com.jairorr.samplesocialmediajc.ui.theme.SampleSocialMediaJCTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<BodyViewModel>()
    private val viewLoginModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            SampleSocialMediaJCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    BodyContainer(viewModel = viewModel)
                    MyNavigation(
                        viewModel = viewModel,
                        viewLoginModel = viewLoginModel
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyContainer(
    modifier: Modifier = Modifier,
    viewModel: BodyViewModel,
    navController: NavController,
) {
    Scaffold(
        content = {
            BodyScreen(
                modifier = modifier.padding(it),
                viewModel = viewModel,
                navController = navController
            )
        },
        topBar = { MyAppBar() })
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar() {
    val context = LocalContext.current
    TopAppBar(
        title = {
            Text(text = "Instagram")
        },
        actions = {
            IconButton(onClick = {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
            }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "")
            }
            IconButton(onClick = {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
            }) {
                Icon(imageVector = Icons.Default.Email, contentDescription = "")
            }
        }
    )
}


@Composable
fun BodyScreen(
    modifier: Modifier = Modifier,
    viewModel: BodyViewModel,
    navController: NavController,
) {
    val context = LocalContext.current
    Column(modifier = modifier.padding(24.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.refreshBoth()
                        Toast
                            .makeText(context, "Refresh data", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .background(Color.LightGray)
                    .padding(10.dp)
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "Refresh content",
                    modifier = modifier.align(
                        Alignment.Center
                    )
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            LazyRow(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                item {
                    viewModel.listHistories.value.forEach {
                        ItemHistory(modifier.padding(horizontal = 5.dp), it.name)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { navController.navigate(MyScreen.RegistrationScreen.route) }
            ) {
                Text(text = "Register users")
            }
            Button(
                onClick = { navController.navigate(MyScreen.LoginScreen.route) }
            ) {
                Text(text = "Login")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            item {
                viewModel.listMembers.value.forEach {
                    ItemMembers(modifier.padding(vertical = 2.dp), memberUser = it)
                }
            }
        }
    }
}

@Preview(widthDp = 200, heightDp = 100)
@Composable
fun ItemHistory(modifier: Modifier = Modifier, text: String = "Jairo") {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .height(50.dp)
            .width(70.dp)
            .background(Color.LightGray)
            .border(0.5.dp, Color.Black)
            .clickable {
                Toast
                    .makeText(context, text, Toast.LENGTH_SHORT)
                    .show()
            }
    ) {
        Text(
            text = text,
            modifier = modifier.align(Alignment.Center),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp
        )
    }
}

@Composable
fun ItemMembers(modifier: Modifier = Modifier, memberUser: MemberUser) {
    Box(
        modifier = modifier
            .background(Color.LightGray)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(vertical = 5.dp)
                .fillMaxSize()
        ) {
            Text(text = "${memberUser.MemberId}", modifier = Modifier.fillMaxWidth())
            Text(
                text = memberUser.name,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${memberUser.lastTransactionAmount}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}
