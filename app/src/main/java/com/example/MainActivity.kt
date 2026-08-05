package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.CandyBackgroundCanvas
import com.example.ui.components.DownloadDialog
import com.example.ui.components.FooterView
import com.example.ui.components.NavPage
import com.example.ui.components.TopHeaderBar
import com.example.ui.components.TrailerDialog
import com.example.ui.screens.CandyWorldsScreen
import com.example.ui.screens.CommunityScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.FaqScreen
import com.example.ui.screens.FeaturesScreen
import com.example.ui.screens.GalleryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LegalScreen
import com.example.ui.screens.NewsScreen
import com.example.ui.theme.CandyTheme
import com.example.viewmodel.CandyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CandyTheme {
                CandyBlastApp()
            }
        }
    }
}

@Composable
fun CandyBlastApp(
    viewModel: CandyViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentPage by viewModel.currentPage.collectAsState()
    val toastMsg by viewModel.toastMessage.collectAsState()

    var isMenuOpen by remember { mutableStateOf(false) }
    var showDownloadDialog by remember { mutableStateOf(false) }
    var showTrailerDialog by remember { mutableStateOf(false) }

    LaunchedEffect(toastMsg) {
        toastMsg?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    if (showDownloadDialog) {
        DownloadDialog(onDismiss = { showDownloadDialog = false })
    }

    if (showTrailerDialog) {
        TrailerDialog(onDismiss = { showTrailerDialog = false })
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars),
        topBar = {
            TopHeaderBar(
                currentPage = currentPage,
                onPageSelected = { page -> viewModel.selectPage(page) },
                onPlayClick = { viewModel.selectPage(NavPage.HOME) },
                onDownloadClick = { showDownloadDialog = true },
                isMenuOpen = isMenuOpen,
                onToggleMenu = { isMenuOpen = !isMenuOpen }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Ambient Floating Candy Background
            CandyBackgroundCanvas()

            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    when (currentPage) {
                        NavPage.HOME -> HomeScreen(
                            viewModel = viewModel,
                            onPlayClick = { viewModel.selectPage(NavPage.HOME) },
                            onDownloadClick = { showDownloadDialog = true },
                            onWatchTrailerClick = { showTrailerDialog = true },
                            onNavigate = { page -> viewModel.selectPage(page) }
                        )
                        NavPage.FEATURES -> FeaturesScreen(viewModel = viewModel)
                        NavPage.WORLDS -> CandyWorldsScreen(viewModel = viewModel)
                        NavPage.GALLERY -> GalleryScreen(viewModel = viewModel)
                        NavPage.NEWS -> NewsScreen(viewModel = viewModel)
                        NavPage.COMMUNITY -> CommunityScreen(viewModel = viewModel)
                        NavPage.FAQ -> FaqScreen(viewModel = viewModel)
                        NavPage.CONTACT -> ContactScreen(viewModel = viewModel)
                        NavPage.LEGAL -> LegalScreen()
                    }
                }

                // Footer section with domain tjcandyblast.com and quick links
                FooterView(
                    onPageSelected = { page -> viewModel.selectPage(page) }
                )
            }
        }
    }
}
