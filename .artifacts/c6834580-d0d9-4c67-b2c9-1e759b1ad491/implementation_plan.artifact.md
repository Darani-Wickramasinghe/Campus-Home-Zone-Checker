# Fix Compilation Errors in MainActivity.kt

The project currently fails to build due to conflicting method definitions and a missing method implementation in `MainActivity.kt`.

## Proposed Changes

### [app](file:///C:/Users/ASUS/AndroidStudioProjects/Campus-Home-Zone-Checker/app)

#### [MODIFY] [MainActivity.kt](file:///C:/Users/ASUS/AndroidStudioProjects/Campus-Home-Zone-Checker/app/src/main/java/com/example/campuszonechecker/MainActivity.kt)

1. **Remove Duplicate Method**: Remove the redundant `handleLocation(location: Location)` method definition.
2. **Implement `checkZone(location: Location)`**: Add the missing `checkZone` method to calculate the distance from the reference point and update the UI accordingly.
3. **Add Missing Imports**: Add `java.util.Locale` for string formatting.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:assembleDebug` to verify that the project builds successfully.

### Manual Verification
- Deploy the app to a device/emulator and verify that clicking "Check My Zone" correctly displays the coordinates, distance, and status.
