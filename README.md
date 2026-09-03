# ShortsApp

여러 클립 촬영 → 병합 → 오버레이(텍스트/스티커) 편집 → 내보내기까지 지원하는 Android 숏폼 비디오 앱.

## 화면 흐름

`촬영(Record)` → `병합(Merge)` → `오버레이 편집(Overlay)` → `결과(Result)`

- **CameraScreen** — CameraX 기반 다중 클립 녹화
- **MergeScreen** — Media3 Transformer로 클립 병합, `MergeWorker`가 백그라운드에서 처리
- **OverlayEditorScreen** — 병합된 영상 위에 텍스트/스티커 배치, 드래그·스케일 편집, `OverlayExportWorker`로 내보내기
- **ResultScreen** — 최종 영상 확인 및 미디어스토어 저장

## 기술 스택

- Kotlin, Jetpack Compose (Material3)
- CameraX 1.4.1 — 영상 촬영
- Media3 Transformer/Effect 1.5.0 — 병합, 오버레이 렌더링
- Navigation Compose — 화면 전환
- WorkManager — 프로세스 종료에도 살아남는 백그라운드 병합/내보내기
- Accompanist Permissions — 런타임 권한 처리

## 요구 사항

- minSdk 26, targetSdk/compileSdk 35
- JDK 17

## 빌드

```bash
./gradlew assembleDebug
```
