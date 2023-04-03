package com.didi.weatherapp.android.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.didi.weatherapp.android.R
import com.didi.weatherapp.android.feed.CardHeaderImage
import com.didi.weatherapp.android.feed.dateFormatted
import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.model.WeatherAlertDetail
import com.didi.weatherapp.model.Zone
import com.didi.weatherapp.repository.fake.FakeWeatherAlertsRepository
import kotlinx.datetime.toInstant
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun preview(){
    val fakeAlert = FakeWeatherAlertsRepository.fakeAlerts[0]
    val zones = listOf<Zone>(Zone("MZT675", true))
    AlertDetailsScreen(
        WeatherAlertDetail(fakeAlert.id, fakeAlert.startDateUTC, fakeAlert.endDateUTC,
            fakeAlert.sender, fakeAlert.desc, fakeAlert.severity, fakeAlert.certainty,
            fakeAlert.urgency, zones, fakeAlert.instruction)
    )
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun AlertDetailsRoute(viewModel: AlertDetailsViewModel) {

    val alertState by viewModel.wAlert.collectAsStateWithLifecycle()
    AlertDetailsScreen(alertState)

}

//TODO
/*the same image of the alert -> If you long tap on the image you can drag it wherever you want on the screen
the names of the affected zones (& whether or not they are a “radarStation”). You can see ids/URLs of the affected zones under the “ affectedZones” node
the instructions (BONUS: make the instructions and description text to contain only 2 lines and expand if you click on the text itself)
*/

@Composable
fun AlertDetailsScreen(alert: WeatherAlertDetail?) {

    alert?.let { alert ->
        Column {

            Row(Modifier.zIndex(100f)) {
                //TODO maybe download smaller/bigger images based on screen-size
                DraggableCardHeaderImage(alert.pictureUrl)
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Column {


                    Spacer(modifier = Modifier.height(12.dp))


                    val publishDate = alert.startDateUTC.toInstant()
                    val formattedDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        dateFormatted(publishDate)
                    } else {
                        publishDate.toString()
                    }

                    val endDate = alert.endDateUTC.toInstant()
                    val formattedDateEnd = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        dateFormatted(endDate)
                    } else {
                        endDate.toString()
                    }


                    Text("Period: $formattedDate  ->  $formattedDateEnd", style = MaterialTheme.typography.bodyMedium,)
                    Spacer(modifier = Modifier.height(12.dp))


                    val duration = endDate.minus(publishDate)
                    Text("Duration: $duration", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(12.dp))


                    Text("Severity: ${alert.severity}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(12.dp))


                    Text("Certainty: ${alert.certainty}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Urgency: ${alert.urgency}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(12.dp))

                    if(!alert.desc.isNullOrEmpty()){
                        ExpandableText("Description: ${alert.desc}")
                        Spacer(modifier = Modifier.height(12.dp))
                    }


                    Text("Affected zones: ", style = MaterialTheme.typography.bodyMedium)
                    for(az in alert.affectedZones){
                        if(!az.id.isNullOrEmpty()){
                            Row{
                                Text(text = az.id, style = MaterialTheme.typography.bodyMedium)
                                if(az.isRadarStation){
                                    Icon(modifier = Modifier.padding(5.dp,0.dp,0.dp,0.dp),
                                        painter = painterResource(R.drawable.ic_radar),
                                        tint = Color.Red,
                                        contentDescription = "radar_icon"
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                        }


                    }
                    Spacer(modifier = Modifier.height(9.dp))

                    if(!alert.instruction.isNullOrEmpty()){
                        ExpandableText("Instructions: ${alert.instruction}")
                        Spacer(modifier = Modifier.height(12.dp))
                    }


                    Text("Source: ${alert.sender}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(12.dp))



                }
            }


        }

    }
}

@Composable
fun DraggableCardHeaderImage(
    headerImageUrl: String?
) {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(Color.Blue)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                },
            contentScale = ContentScale.Crop,
            model = headerImageUrl,
            contentDescription = null, // decorative image

        )
}

@Composable
fun ExpandableText(text: String){

    var showMore by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .animateContentSize(animationSpec = tween(100))
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { showMore = !showMore }) {

        if (showMore) {
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        } else {
            Text(text = text, maxLines = 2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
