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

## 개발 기록 (2026-09-04)

프로젝트를 처음부터 구성하며 진행한 작업을 시간 순으로 정리.

1. **19:27 ~ 19:38 — 프로젝트 골격 구성**
   `settings.gradle.kts`, 루트/앱 `build.gradle.kts`, `gradle.properties`, `proguard-rules.pro` 작성. 리소스(`strings.xml`, `colors.xml`, `themes.xml`, 런처 아이콘) 추가.
2. **19:31 ~ 19:38 — 유틸리티 및 병합 기반 작업**
   `ExportNotifications`(내보내기 알림), `MergeWorker`(백그라운드 병합), `MediaStoreExport`(미디어스토어 저장), `ResultScreen`, `AppNavHost`, `MainActivity` 작성.
3. **20:54 — Gradle 래퍼 정리 및 `VideoMerger` 작성**
   Media3 Transformer 기반 영상 병합 로직 구현.
4. **22:25 ~ 22:26 — 오버레이 모델/직렬화/내보내기**
   `OverlayModels`(텍스트·스티커 데이터 모델), `OverlaySerialization`, `OverlayExporter`, `OverlayExportWorker` 작성.
5. **23:26 ~ 23:55 — 카메라 설정 및 프레임 유틸**
   `DurationPrefs`(촬영 길이 설정), `VideoFrameUtil`(썸네일 추출), `MergeScreen`(병합 화면 UI) 작성.
6. **00:24 ~ 01:06 — 오버레이 편집 UI 및 카메라 캡처 완성**
   `OverlayEditorScreen`(드래그·스케일 편집), `OverlayTimeline`(타임라인 UI), `VideoCaptureManager`(CameraX 녹화), `AndroidManifest.xml`, `CameraScreen` 작성 — 촬영→병합→오버레이→결과 전체 흐름 완성.
7. **01:52 — Git 저장소 초기화 및 GitHub 푸시**
   `.gitignore` 추가, 전체 소스 최초 커밋, GitHub(`kjt7942/short-video-app`) public 저장소 생성 후 푸시.
8. **01:53 — README 작성**
   프로젝트 개요, 화면 흐름, 기술 스택 문서화 후 커밋/푸시.
