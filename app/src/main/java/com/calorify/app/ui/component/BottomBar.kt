package com.calorify.app.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.calorify.app.R
import com.calorify.app.ui.navigation.NavigationItem
import com.calorify.app.ui.navigation.Screen
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.Neutral25
import com.calorify.app.ui.theme.Neutral500
import com.calorify.app.ui.theme.Neutral700


@Composable
fun BottomBar(
    navController: NavHostController = NavHostController(LocalContext.current),
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = R.drawable.ic_navbar_home,
                selectedIcon = R.drawable.ic_navbar_home_active,
                screen = Screen.Home,
                contentDescription = "home",
            ),
            NavigationItem(
                title = stringResource(R.string.menu_scan),
                icon = R.drawable.ic_navbar_history,
                selectedIcon = R.drawable.ic_navbar_history_active,
                screen = Screen.Scan,
                contentDescription = "scan_calorie",
            ),
            NavigationItem(
                title = stringResource(R.string.menu_history),
                icon = R.drawable.ic_navbar_scan,
                selectedIcon = R.drawable.ic_navbar_scan_active,
                screen = Screen.History,
                contentDescription = "history_log",
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = R.drawable.ic_navbar_profile,
                selectedIcon = R.drawable.ic_navbar_profile_active,
                screen = Screen.Profile,
                contentDescription = "about_page",
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                val isSelected = currentRoute == item.screen.route
                BottomNavigationItem(
                    unselectedContentColor = Neutral500,
                    selectedContentColor = Neutral700,
                    modifier = modifier
                        .background(Neutral25),
                    icon = {
                        Image(
                            painter = painterResource(id = if (isSelected) item.selectedIcon else item.icon),
                            contentDescription = item.contentDescription
                        )
                    },
                    label = { Text(item.title) },
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    CalorifyTheme {
        BottomBar()
    }
}