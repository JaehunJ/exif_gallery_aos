Exif filter gallery (작업 중)
=============
flutter 버전의 AOS 버전
https://github.com/JaehunJ/exif_filter_gallery/

## 구조
Multi-module, MVVM, Clean architecture

## 라이브러리 의존성
Glide, Hilt, Flow, Jetpack Compose, coil

## 프로젝트 구성
|모듈 이름|의존성|기능|
|------|---|---|
|app|include(data, domain, presentation)| App entry point, permission management
|data|inclue(domain)| data source, repository impl, model impl
|domain|독립|repository interface, model interface, usecase
|presentation|include(domain)|view, viewmodel, route

## 요구조건
1. 기본적으로 디바이스내 이미지를 표시한다.
2. 화면 뎁스는 앨범선택-이미지선택-이미지 순으로 한다.
3. 필터는 이미지 선택에서 가능하다.
4. 이미지 선택뷰, 이미지 뷰에서 다른 앱으로 이미지가 공유가 가능하게 한다.

## 필터 종류
1. 만들어진 날짜 오름차순/내림차순
2. 화각별 내림차순
4. 기기 내림차순
