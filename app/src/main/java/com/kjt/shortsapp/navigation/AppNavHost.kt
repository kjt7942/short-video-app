package com.kjt.shortsapp.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kjt.shortsapp.camera.CameraScreen
import com.kjt.shortsapp.merge.MergeScreen
import com.kjt.shortsapp.overlay.OverlayEditorScreen
import com.kjt.shortsapp.result.ResultScreen

private object Routes {
    const val RECORD = "record"
    const val MERGE = "merge"
    const val OVERLAY = "overlay/{path}"
    const val RESULT = "result/{uri}"

    fun overlay(path: String) = "overlay/${Uri.encode(path)}"
    fun result(uri: String) = "result/${Uri.encode(uri)}"
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.RECORD) {
        composable(Routes.RECORD) {
            CameraScreen(onNavigateToMerge = { navController.navigate(Routes.MERGE) })
        }

        composable(Routes.MERGE) {
            MergeScreen(onMerged = { mergedPath -> navController.navigate(Routes.overlay(mergedPath)) })
        }

        composable(
            route = Routes.OVERLAY,
            arguments = listOf(navArgument("path") { type = NavType.StringType }),
        ) { backStackEntry ->
            val path = backStackEntry.arguments?.getString("path").orEmpty()
            OverlayEditorScreen(
                mergedVideoPath = path,
                onExported = { finalUri -> navController.navigate(Routes.result(finalUri)) },
            )
        }

        composable(
            route = Routes.RESULT,
            arguments = listOf(navArgument("uri") { type = NavType.StringType }),
        ) { backStackEntry ->
            val uri = backStackEntry.arguments?.getString("uri").orEmpty()
            ResultScreen(
                finalVideoUri = uri,
                onRestart = {
                    navController.navigate(Routes.RECORD) {
                        popUpTo(Routes.RECORD) { inclusive = true }
                    }
                },
            )
        }
    }
}
