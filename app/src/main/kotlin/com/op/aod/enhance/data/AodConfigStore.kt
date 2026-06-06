package com.op.aod.enhance.data

import android.content.ContentResolver
import android.content.ContentValues

object AodConfigStore {

    @Volatile
    private var cached: AodUiConfig? = null

    fun read(resolver: ContentResolver): AodUiConfig {
        val fresh = queryOrNull(resolver)
        if (fresh != null) {
            cached = fresh
            return fresh
        }
        return cached ?: AodUiConfig()
    }

    fun write(resolver: ContentResolver, cfg: AodUiConfig) {
        val values = ContentValues().apply {
            put(AodConfigContract.KEY_ENABLE_SETTINGS_SUPPORT, cfg.enableSettingsSupport)
            put(AodConfigContract.KEY_BLOCK_SINGLE_CLICK, cfg.blockSingleClick)
        }
        resolver.update(AodConfigProvider.CONTENT_URI, values, null, null)
        cached = cfg
    }

    private fun queryOrNull(resolver: ContentResolver): AodUiConfig? {
        return runCatching {
            resolver.query(AodConfigProvider.CONTENT_URI, null, null, null, null)?.use { c ->
                if (c.moveToFirst()) {
                    AodUiConfig(
                        enableSettingsSupport = c.getString(AodConfigContract.COL_ENABLE_SETTINGS_SUPPORT)?.toBooleanStrictOrNull()
                            ?: AodConfigContract.DEFAULT_ENABLE_SETTINGS_SUPPORT,
                        blockSingleClick = c.getString(AodConfigContract.COL_BLOCK_SINGLE_CLICK)?.toBooleanStrictOrNull()
                            ?: AodConfigContract.DEFAULT_BLOCK_SINGLE_CLICK,
                    )
                } else null
            }
        }.getOrNull()
    }
}
