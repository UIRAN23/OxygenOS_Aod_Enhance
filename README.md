# OxygenOS AOD Enhance

Упрощённый форк [ColorOS AOD Enhance](https://github.com/Qjj7679/ColorOS_Aod_Enhance) — только две функции, адаптировано под **OxygenOS 16.0.7**.

## Функции

1. **AOD весь день** — включает переключатель «AOD весь день» в настройках экрана блокировки. После включения в модуле → Настройки → Экран блокировки → Активный экран (AOD) → Время отображения → «Всегда».

2. **Блокировка касания AOD** — касание AOD не будет включать экран. Экран пробуждается только через кнопку питания.

## Что удалено

- Все функции яркости (начальная яркость, множитель яркости)
- Панорамный AOD hook
- BrightnessActivity, FeaturesActivity — теперь один экран с двумя переключателями

## Требования

- OxygenOS 16.0.7 (Android 16, API 36)
- LSPosed / Zygisk
- Root

## Сборка

```bash
./gradlew assembleRelease
```

APK будет в `app/build/outputs/apk/release/`.

## Оригинал

- [ColorOS AOD Enhance](https://github.com/Qjj7679/ColorOS_Aod_Enhance) by Qjj7679
