package com.op.aod.enhance.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.op.aod.enhance.data.AodConfigStore
import com.op.aod.enhance.data.AodUiConfig
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CardDefaults
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.SmallTopAppBar
import top.yukonga.miuix.kmp.extra.SuperSwitch
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.utils.scrollEndHaptic
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiuixTheme {
                MainScreen(
                    initial = AodConfigStore.read(contentResolver),
                    onSave = { cfg -> AodConfigStore.write(contentResolver, cfg) },
                    context = this
                )
            }
        }
    }
}

@OptIn(FlowPreview::class)
@Composable
private fun MainScreen(
    initial: AodUiConfig,
    onSave: (AodUiConfig) -> Unit,
    context: android.content.Context
) {
    var enableSettingsSupport by remember { mutableStateOf(initial.enableSettingsSupport) }
    var blockSingleClick by remember { mutableStateOf(initial.blockSingleClick) }
    val currentOnSave by rememberUpdatedState(onSave)

    LaunchedEffect(Unit) {
        snapshotFlow { Pair(enableSettingsSupport, blockSingleClick) }
            .drop(1)
            .debounce(300)
            .distinctUntilChanged()
            .collect { (settingsSupport, singleClick) ->
                val base = AodConfigStore.read(context.contentResolver)
                currentOnSave(
                    base.copy(
                        enableSettingsSupport = settingsSupport,
                        blockSingleClick = singleClick,
                    )
                )
            }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = "OxygenOS AOD Enhance",
                color = MiuixTheme.colorScheme.secondaryContainer,
            )
        },
        containerColor = MiuixTheme.colorScheme.secondaryContainer,
    ) { paddingValues: PaddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .scrollEndHaptic()
                .overScrollVertical()
                .padding(horizontal = 12.dp),
            contentPadding = paddingValues,
            overscrollEffect = null,
        ) {
            item {
                Card(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.defaultColors(
                        color = MiuixTheme.colorScheme.background,
                    ),
                ) {
                    SuperSwitch(
                        title = "AOD весь день",
                        summary = "Включает переключатель «AOD весь день» в настройках экрана блокировки. После включения найдите «Активный экран» → «Активный экран (АOD)» → «Время отображения» → «Всегда»",
                        checked = enableSettingsSupport,
                        onCheckedChange = { enableSettingsSupport = it },
                    )
                    SuperSwitch(
                        title = "Блокировка касания AOD",
                        summary = "Касание AOD не будет включать экран. Экран пробуждается только через кнопку питания",
                        checked = blockSingleClick,
                        onCheckedChange = { blockSingleClick = it },
                    )
                }
            }
        }
    }
}
