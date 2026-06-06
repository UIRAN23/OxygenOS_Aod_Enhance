package com.op.aod.enhance.hook

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.op.aod.enhance.hook.AodSettingsHook.hookAodAllDaySupportSettings
import com.op.aod.enhance.hook.SingleClickBlockHook.hookSingleClickWakeUpBlock

/**
 * OxygenOS AOD Enhance - 主入口
 *
 * Только две функции:
 * 1. AOD весь день — включает переключатель в настройках для OxygenOS
 * 2. Блокировка касания AOD — экран не пробуждается при касании AOD, только через кнопку питания
 *
 * Адаптировано под OxygenOS 16.0.7
 */
object MainHook : YukiBaseHooker() {

    override fun onHook() {
        loadApp(name = SYSTEM_UI) {
            AodConfigReader.startObserve(AodConfigReader.currentContext())
            runCatching { hookSingleClickWakeUpBlock() }
        }
        loadApp(name = OPLUS_AOD) {
            runCatching { hookAodAllDaySupportSettings() }
        }
    }

    private const val SYSTEM_UI = "com.android.systemui"
    private const val OPLUS_AOD = "com.oplus.aod"

}
