# OSRS Planner – CI Ready

This project is preconfigured for **GitHub Actions** and **Codemagic** to build APKs automatically.

## Option A — GitHub Actions (free)

1. Create a new empty repository on GitHub (private or public).
2. Upload the contents of this folder (or push via Git).
3. Ensure your default branch is `main` or `master`.
4. After pushing, go to **Actions** tab → you should see a workflow named **Android CI (Debug APK)** running.
5. When it finishes, download the artifact **OSRS-Planner-Debug-APK** → it contains `app-debug.apk` (unsigned).

> You can sign later in Play Console or add signing to CI when you have a keystore.

## Option B — Codemagic (very simple UI)

1. Sign up at codemagic.io and connect your GitHub repo.
2. In your repo root, keep `codemagic.yaml` (already included).
3. Start a new build with the **android_debug** workflow.
4. When it finishes, download the APK from build **Artifacts**.

## Notes

- The CI builds **Debug** APK (no keystore needed). 
- To produce a Release AAB/APK with signing, add a keystore and environment variables:
  - `CM_KEYSTORE` (base64 of your keystore file),
  - `CM_KEYSTORE_PASSWORD`,
  - `CM_KEY_ALIAS`,
  - `CM_KEY_PASSWORD`,
  and run `:app:bundleRelease` or `:app:assembleRelease`.
