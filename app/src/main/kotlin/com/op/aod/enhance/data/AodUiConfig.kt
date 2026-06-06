package com.op.aod.enhance.data

/**
 * UI 侧配置数据类 для OxygenOS AOD Enhance.
 */
data class AodUiConfig(
    val enableSettingsSupport: Boolean = AodConfigContract.DEFAULT_ENABLE_SETTINGS_SUPPORT,
    val blockSingleClick: Boolean = AodConfigContract.DEFAULT_BLOCK_SINGLE_CLICK,
)
