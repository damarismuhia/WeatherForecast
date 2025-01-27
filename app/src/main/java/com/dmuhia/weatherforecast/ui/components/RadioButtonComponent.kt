package com.dmuhia.weatherforecast.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dmuhia.weatherforecast.R


@Composable
fun RadioButtonComponent(isCelsius: Boolean,
                         onUnitSelected: (Boolean) -> Unit,
                         modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ){
        Text(
            text = stringResource(id = R.string.metrics),
            style = MaterialTheme.typography.bodyLarge,
            color =  MaterialTheme.colorScheme.primary
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            // Celsius Option
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start


            ) {
                RadioButton(
                    selected = isCelsius,
                    onClick = { onUnitSelected(true) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = stringResource(id = R.string.celcius),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Fahrenheit Option
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = !isCelsius,
                    onClick = { onUnitSelected(false) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = stringResource(id = R.string.fahrenheit),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
fun RadioButtonComponentPreview() {
    RadioButtonComponent(isCelsius = true, onUnitSelected = {})

}