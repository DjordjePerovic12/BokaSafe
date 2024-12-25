package llc.amplitudo.flourish_V2.core.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun ShowLottie(modifier: Modifier = Modifier, lottie: Int) {
    val compositionResult =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(lottie))

    val progress by animateLottieCompositionAsState(
        compositionResult.value,
        isPlaying = true,
        iterations = 1,
        speed = 1f
    )
    LottieAnimation(
        modifier = modifier,
        composition = compositionResult.value,
        progress = { progress }
    )
}