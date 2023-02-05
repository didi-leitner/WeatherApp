package com.didi.weatherapp.android.feed

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import coil.compose.AsyncImage
import com.didi.weatherapp.model.WeatherAlert


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAlertCardExpanded(
    alert: WeatherAlert,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column {
            Row {
                //TODO maybe download smaller/bigger images based on screen-size
                CardHeaderImage("https://picsum.photos/seed/"
                        +alert.id+"/600/900")//1000")
            }

            Box(
                modifier = Modifier.padding(16.dp),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        Text(alert.event, style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.fillMaxWidth((.8f)))

                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(12.dp))


                    val publishDate = alert.startDateUTC.toInstant()
                    val formattedDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        dateFormatted(publishDate)
                    } else {
                        publishDate.toString()
                    }
                    Text(
                        "Effective: $formattedDate", style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(12.dp))



                    val endDate = alert.endDateUTC.toInstant()
                    val formattedDateEnd = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        dateFormatted(endDate)
                    } else {
                        endDate.toString()
                    }
                    Text(
                        "Ends: $formattedDateEnd",
                        style = MaterialTheme.typography.bodyMedium,//.labelMedium,
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    val duration = endDate.minus(publishDate)
                    Text("Duration: $duration", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Sender: "+alert.sender, style = MaterialTheme.typography.bodySmall)

                }
            }
        }
    }
}

@Composable
fun CardHeaderImage(
    headerImageUrl: String?,
) {
    AsyncImage(
        /*placeholder = if (LocalInspectionMode.current) {
            painterResource(DesignsystemR.drawable.ic_placeholder_default)
        } else {
            // TODO b/228077205, show specific loading image visual
            null
        },*/
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        contentScale = ContentScale.Crop,
        model = headerImageUrl,
        // TODO b/226661685: Investigate using alt text of  image to populate content description
        contentDescription = null, // decorative image
    )
}



@RequiresApi(Build.VERSION_CODES.O)
fun dateFormatted(publishDate: Instant): String {

    val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy MMM dd' 'HH:mm:ss")

    return FORMATTER.withZone(ZoneId.systemDefault()).format(publishDate.toJavaInstant())
}




