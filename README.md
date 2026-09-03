# ShortsApp

GitHub: https://github.com/kjt7942/short-video-app

여러 클립 촬영 → 병합 → 오버레이(텍스트/스티커) 편집 → 내보내기까지 지원하는 Android 숏폼 비디오 앱.

## 목적

카메라 앱을 켜지 않고도, 이 앱 하나로 "여러 개 짧은 클립을 찍고 → 이어 붙이고 → 텍스트/이모지를 원하는 시점·위치에 넣고 → 갤러리에 저장"까지 끝내는 숏폼 제작 도구를 만드는 것이 목표. 편집 프로그램을 따로 켜지 않아도 되는 원스톱 흐름에 집중.

## 구현하려던 주요 기능

- **클립 길이 프리셋 + 커스텀 시간** — 2/3/4초 고정 칩 외에, 사용자가 직접 초를 지정(1~30초)해 최근 2개까지 기억(`DurationPrefs`, SharedPreferences 저장)
- **자동 종료 촬영** — 선택한 시간이 지나면 자동으로 녹화 중지, 진행률 바·카운트다운 표시
- **여러 클립 이어 찍기 → 한 편으로 병합** — 클립을 여러 번 찍은 뒤 Media3 Transformer로 순서대로 합치기, 프로세스가 죽어도 이어지도록 WorkManager 백그라운드 처리
- **오버레이 편집 (텍스트/이모지)** — 병합된 영상 위에 레이어를 올리고 화면에서 직접 드래그·크기 조절
- **키프레임 애니메이션** — 레이어마다 여러 시점(keyframe)에 위치·크기를 지정하면 그 사이를 자동 보간(linear interpolation)해서 움직이는 오버레이 구현, 레이어별로 노출 구간(start~end)도 별도 설정 가능
- **오버레이 타임라인 UI** — 여러 레이어의 노출 구간과 키프레임을 한눈에 보고 편집
- **최종 내보내기 & 저장** — 오버레이까지 합성한 영상을 내보내기 알림과 함께 렌더링하고 MediaStore(갤러리)에 저장
- **결과 화면** — 저장된 최종 영상 확인, 처음부터 다시 촬영

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
