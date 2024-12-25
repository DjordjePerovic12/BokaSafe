package llc.bokadev.bokasafe.presentation.bay_map

import llc.bokadev.bokasafe.domain.model.MapItemFilters
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import llc.bokadev.bokasafe.R
import llc.bokadev.bokasafe.core.utils.MapItems
import llc.bokadev.bokasafe.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun NotificationPreferencesBottomSheet(
    mapItemFilters: MapItemFilters,
    onSwitchClick: (Boolean, Int) -> Unit,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    var checked by remember { mutableStateOf(mapItemFilters) }
    LaunchedEffect(mapItemFilters) {
        checked = mapItemFilters
    }


    val insets = WindowInsets.systemBars.asPaddingValues()

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(BokaBaySeaTrafficAppTheme.colors.defaultGray)
            .padding(horizontal = 25.dp)
            .padding(bottom = 25.dp)

    ) {
        item { Spacer(modifier = Modifier.height(20.dp)) }
        item {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "FILTER WHICH ITEMS TO SHOW ON MAP",
                    color = BokaBaySeaTrafficAppTheme.colors.white,
                    style = BokaBaySeaTrafficAppTheme.typography.ralewayBold16
                )
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .background(BokaBaySeaTrafficAppTheme.colors.white)
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        text = "Lighthouses",
                        color = BokaBaySeaTrafficAppTheme.colors.white,
                        style = BokaBaySeaTrafficAppTheme.typography.nunitoBold16
                    )
                }

                Switch(
                    checked = checked.lighthouses, onCheckedChange = {
                        onSwitchClick(it, MapItems.LIGHTHOUSE.mapItemTypeId)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                        uncheckedThumbColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f),
                        uncheckedTrackColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f),
                        checkedTrackColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.2f),
                        checkedBorderColor = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                        uncheckedBorderColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f)
                    ),
                    thumbContent = {
                        if (checked.lighthouses) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_tick),
                                contentDescription = null,
                            )

                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_x),
                                contentDescription = null,
                                tint = BokaBaySeaTrafficAppTheme.colors.white
                            )
                        }
                    }

                )
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .height(1.dp)
                    .background(BokaBaySeaTrafficAppTheme.colors.lightGray)
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        text = "Shipwrecks",
                        color = BokaBaySeaTrafficAppTheme.colors.white,
                        style = BokaBaySeaTrafficAppTheme.typography.nunitoBold16
                    )
                }

                Switch(
                    checked = checked.shipwrecks, onCheckedChange = {
                        onSwitchClick(it, MapItems.SHIPWRECK.mapItemTypeId)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                        uncheckedThumbColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f),
                        uncheckedTrackColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f),
                        checkedTrackColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.2f),
                        checkedBorderColor = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                        uncheckedBorderColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f)
                    ),
                    thumbContent = {
                        if (checked.shipwrecks) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_tick),
                                contentDescription = null,
                            )

                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_x),
                                contentDescription = null,
                                tint = BokaBaySeaTrafficAppTheme.colors.white
                            )
                        }
                    }

                )
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .height(1.dp)
                    .background(BokaBaySeaTrafficAppTheme.colors.lightGray)
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        text = "Prohibited anchoring zones",
                        color = BokaBaySeaTrafficAppTheme.colors.white,
                        style = BokaBaySeaTrafficAppTheme.typography.nunitoBold16
                    )

                }

                Switch(
                    checked = checked.prohibitedAnchoringZone, onCheckedChange = {
                        onSwitchClick(it, MapItems.PROHIBITED_ANCHORING_ZONE.mapItemTypeId)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                        uncheckedThumbColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f),
                        uncheckedTrackColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f),
                        checkedTrackColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.2f),
                        checkedBorderColor = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                        uncheckedBorderColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f)
                    ),
                    thumbContent = {
                        if (checked.prohibitedAnchoringZone) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_tick),
                                contentDescription = null,
                            )

                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_x),
                                contentDescription = null,
                                tint = BokaBaySeaTrafficAppTheme.colors.white
                            )
                        }
                    }

                )
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .height(1.dp)
                    .background(BokaBaySeaTrafficAppTheme.colors.lightGray)
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        text = "Anchorages",
                        color = BokaBaySeaTrafficAppTheme.colors.white,
                        style = BokaBaySeaTrafficAppTheme.typography.nunitoBold16
                    )

                }

                Switch(
                    checked = checked.anchorages, onCheckedChange = {
                        onSwitchClick(it, MapItems.ANCHORAGE.mapItemTypeId)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                        uncheckedThumbColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f),
                        uncheckedTrackColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f),
                        checkedTrackColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.2f),
                        checkedBorderColor = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                        uncheckedBorderColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f)
                    ),
                    thumbContent = {
                        if (checked.anchorages) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_tick),
                                contentDescription = null,
                            )

                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_x),
                                contentDescription = null,
                                tint = BokaBaySeaTrafficAppTheme.colors.white
                            )
                        }
                    }

                )
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .height(1.dp)
                    .background(BokaBaySeaTrafficAppTheme.colors.lightGray)
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        text = "Underwater cables",
                        color = BokaBaySeaTrafficAppTheme.colors.white,
                        style = BokaBaySeaTrafficAppTheme.typography.nunitoBold16
                    )
                }

                Switch(
                    checked = checked.underwaterCables, onCheckedChange = {
                        onSwitchClick(it, MapItems.UNDERWATER_CABLE.mapItemTypeId)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                        uncheckedThumbColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f),
                        uncheckedTrackColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f),
                        checkedTrackColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.2f),
                        checkedBorderColor = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                        uncheckedBorderColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f)
                    ),
                    thumbContent = {
                        if (checked.underwaterCables) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_tick),
                                contentDescription = null,
                            )

                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_x),
                                contentDescription = null,
                                tint = BokaBaySeaTrafficAppTheme.colors.white
                            )
                        }
                    }

                )
            }
        }
        item {
            Spacer(
                modifier = Modifier.height(1.dp).fillMaxWidth()
                    .background(BokaBaySeaTrafficAppTheme.colors.lightGray)
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        text = "Buoys",
                        color = BokaBaySeaTrafficAppTheme.colors.white,
                        style = BokaBaySeaTrafficAppTheme.typography.nunitoBold16
                    )
                }

                Switch(
                    checked = checked.buoys, onCheckedChange = {
                        onSwitchClick(it, MapItems.BUOY.mapItemTypeId)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                        uncheckedThumbColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f),
                        uncheckedTrackColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f),
                        checkedTrackColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.2f),
                        checkedBorderColor = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                        uncheckedBorderColor = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f)
                    ),
                    thumbContent = {
                        if (checked.buoys) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_tick),
                                contentDescription = null,
                            )

                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_x),
                                contentDescription = null,
                                tint = BokaBaySeaTrafficAppTheme.colors.white
                            )
                        }
                    }

                )
            }
        }

        item {
            Spacer(
                modifier = Modifier.height(25.dp)
            )
        }


        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Confirm", color = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                    style = BokaBaySeaTrafficAppTheme.typography.ralewayBold20,
                    modifier = Modifier.clickable {
                        onConfirmClick()
                    }.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Cancel", color = BokaBaySeaTrafficAppTheme.colors.white,
                    style = BokaBaySeaTrafficAppTheme.typography.ralewayBold20,
                    modifier = Modifier.clickable {
                        onCancelClick()
                    }.weight(1f),
                    textAlign = TextAlign.Center
                )


            }
        }
    }
}