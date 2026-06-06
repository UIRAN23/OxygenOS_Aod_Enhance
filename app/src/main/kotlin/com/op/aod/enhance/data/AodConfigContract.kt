package com.op.aod.enhance.data

/**
 * 配置契约 для OxygenOS AOD Enhance.
 * Только нужные функции без яркости.
 */
object AodConfigContract {

    // 列索引
    const val COL_ENABLE_SETTINGS_SUPPORT = 0
    const val COL_BLOCK_SINGLE_CLICK = 1

    const val COL_COUNT = 2

    // SharedPreferences 键名
    const val KEY_ENABLE_SETTINGS_SUPPORT = "enable_settings_support"
    const val KEY_BLOCK_SINGLE_CLICK = "block_single_click"

    // 默认值
    const val DEFAULT_ENABLE_SETTINGS_SUPPORT = true
    const val DEFAULT_BLOCK_SINGLE_CLICK = true
}
