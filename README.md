# GoalPulse - FIFA World Cup Live Score App

GoalPulse is a modular Android application built with Kotlin and Jetpack Compose for live World Cup coverage.

## Modules

- `app`: application shell, navigation, DI wiring
- `common`: shared primitives (`NetworkResult`, dispatchers)
- `core`: domain models, repository contracts, use cases
- `network`: Retrofit + OkHttp + websocket gateway
- `database`: Room entities, DAO, local cache
- `designsystem`: Material 3 theme, reusable UI components
- `feature_*`: feature-first UI modules

## Architecture

- Clean Architecture (Presentation / Domain / Data)
- MVVM + `StateFlow`
- Repository Pattern with DTO -> Domain mappers
- Hilt dependency injection

## Quick Start

```bash
./gradlew test
./gradlew :app:assembleDebug
```

## Realtime and Offline

- Realtime channel scaffolded in `network/RealtimeSocketClient.kt`
- Room cache scaffolded in `database`
- Repository fallback sample data included for resilient demos

## Security and Release Notes

- Firebase Analytics / Crashlytics / Messaging dependencies added
- CI workflow at `.github/workflows/android-ci.yml`
- Release minification enabled for `release` build type
- Release resource shrinking enabled for `release` build type
- App Bundle (`.aab`) output prepared for Play Store upload signing

## Play Store AAB Release

1. Copy `keystore.properties.example` to `keystore.properties`.
2. Fill in your real upload keystore path and passwords.
3. Build the Play Store bundle:

```bash
./gradlew :app:bundleRelease
```

If you prefer environment variables instead of `keystore.properties`, set:

- `GOALPULSE_UPLOAD_STORE_FILE`
- `GOALPULSE_UPLOAD_STORE_PASSWORD`
- `GOALPULSE_UPLOAD_KEY_ALIAS`
- `GOALPULSE_UPLOAD_KEY_PASSWORD`

Generated bundle output:

```text
app/build/outputs/bundle/release/app-release.aab
```

## Next hardening steps before Play Store submission

1. Replace `https://api.example.com/worldcup/` with production API and SSL pinning certificate.
2. Add encrypted token manager and refresh token workflow.
3. Add full instrumentation tests and baseline profiles.
4. Configure Firebase App Distribution and Play Console signing / store listing assets.


