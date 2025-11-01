package com.example.weatherpal

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherpal.ui.screens.CurrentWeatherScreen
import com.example.weatherpal.ui.screens.DailyForecastScreen
import com.example.weatherpal.ui.theme.WeatherPalTheme
import androidx.activity.viewModels
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.example.weatherpal.models.Alert
import com.example.weatherpal.ui.screens.CurrentWeather
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

// icons from https://www.flaticon.com/search?author_id=1264
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    //@SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisplayUI(mainViewModel)
            }
        }
    }

//@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayUI(mainViewModel: MainViewModel){

    //collectAsState makes UI automatically update when weatherState changes
    val weatherState by mainViewModel.weather.collectAsState()
    val unitState by mainViewModel.unit.collectAsState()

    var locationFetched by remember { mutableStateOf(false) }

    // State to manage which alert is shown in the popup (or null if none)
    var selectedAlert by remember { mutableStateOf<Alert?>(null) }

    // Check if there are any alerts in the weather data
    val hasAlerts = weatherState?.alerts?.alertList?.isNotEmpty() == true

    // Store the selectedIndex in a state flow variable
    var selectedIndex by remember { mutableIntStateOf(0) }

    GetLocation(onLocationGranted = { coordinates ->
        // This code block is executed when GetLocation succeeds.
        if (!locationFetched) {
            mainViewModel.fetchWeather(coordinates)
            locationFetched = true // Mark as fetched to avoid re-fetching.
        }
    })

    val location = weatherState?.location?.name ?: "Loading..."

    val navController = rememberNavController()

    Scaffold(
        //Display top bar
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF3E4E32),
                    titleContentColor = Color(0xFFFAFAFA)
                ),
                title = {
                    Text(location)
                },
                actions = {
                    // Alert Button
                    if (hasAlerts) {
                        IconButton(onClick = {
                            selectedAlert = weatherState?.alerts?.alertList?.first()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Weather Alert",
                                tint = Color.Red
                            )
                        }
                    }

                    // Temperature Unit Toggle Button
                    TextButton(onClick = { mainViewModel.toggleUnit() }) {
                        Text(
                            text = if (unitState == TemperatureUnit.CELSIUS) "°C" else "°F",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFFFAFAFA)
                        )
                    }
                }
            )
        },

        //Display bottom bar
        bottomBar = {
            // Display a navigation bar
            NavigationBar (
                containerColor = Color(0xFF2F402C)
            )
            {
                // Current Weather screen
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_action_current_weather),
                            contentDescription = "Current Weather"
                        )
                    },
                    label = {
                        Text("Current Weather")
                    },
                    selected = selectedIndex == 0,
                    onClick = {
                        selectedIndex = 0
                        navController.navigate(route = "Current Weather")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedTextColor = Color.LightGray,
                        unselectedIconColor = Color.LightGray,
                        selectedTextColor = Color.White,
                        selectedIconColor = Color.White,
                        indicatorColor = Color(0xFF3E4E32)
                    )
                )

                // Daily Forecast screen
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_action_daily_forecast),
                            contentDescription = "Daily Forecast"
                        )
                    },
                    label = {
                        Text("Daily Forecast")
                    },
                    selected = selectedIndex == 1,
                    onClick = {
                        selectedIndex = 1
                        navController.navigate(route = "Daily Forecast")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedTextColor = Color.LightGray,
                        unselectedIconColor = Color.LightGray,
                        selectedTextColor = Color.White,
                        selectedIconColor = Color.White,
                        indicatorColor = Color(0xFF3E4E32)
                    )
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "Current Weather",
            modifier = Modifier.padding(innerPadding)
        )
        {
            // Render Current Weather screen
            composable(route = "Current Weather") {
                CurrentWeatherScreen(weather = weatherState, unit = unitState)
            }

            // Render Daily Forecast screen
            composable(route = "Daily Forecast") {
                DailyForecastScreen(weather = weatherState, unit = unitState)
            }
        }
        selectedAlert?.let { alert ->
            AlertPopup(alert = alert, onDismissRequest = {
                // Set selectedAlert back to null to hide the popup
                selectedAlert = null
            })
        }

    }
}

//@RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetLocation(onLocationGranted: (String) -> Unit) {
    // Remember the permission state(asking for Fine location)
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    if (permissionState.status.isGranted) {
        Log.i("TESTING", "Hurray, permission granted!")

        // Get Location
        val currentContext = LocalContext.current
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(currentContext)

        if (ContextCompat.checkSelfPermission(
                currentContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        {
            val cancellationTokenSource = CancellationTokenSource()

            Log.i("TESTING", "Requesting location...")

            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token)
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val lat = location.latitude.toString()
                        val lng = location.longitude.toString()

                        Log.i("TESTING", "Success: $lat $lng")

                        val coordinates = "$lat,$lng"

                        // call a function, like in View Model, to do something with location...
                        onLocationGranted(coordinates)
                    }
                    else {
                        Log.i("TESTING", "Problem encountered: Location returned null")
                    }
                }
        }
    }
    else {
        // Run a side-effect (coroutine) to get permission. The permission popup.
        LaunchedEffect(permissionState){
            permissionState.launchPermissionRequest()
        }
    }
}

@Composable
fun AlertPopup(alert: Alert, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    Text(
                        text = alert.headline,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Event: ${alert.event}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Effective: ${alert.effective}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Expires: ${alert.expires}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = alert.description, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    if (alert.instruction.isNotBlank()) {
                        Text(
                            text = "Instruction: ${alert.instruction}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Button(
                        onClick = onDismissRequest,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}

