Based on Yuya Tanaka's excellent work (and Wrywulf's [updates](https://github.com/Wrywulf/TimberTreeUtils)) on [TimberTreeUtils](https://github.com/ypresto/TimberTreeUtils), but updated to work with the Firebase Crashlytics SDK.

TimberTreeUtils
===============

Set of [timber](https://github.com/JakeWharton/timber) trees for
[Crashlytics](https://firebase.google.com/docs/crashlytics) and debugging.


Usage
----

This library only contains `Timber.Tree` implementations. Just plant it to Timber.

```java
Timber.plant(new CrashlyticsLogExceptionTree(FirebaseCrashlytics.getInstance()))
```


Trees
----

### CrashlyticsLogExceptionTree (Default log level: ERROR)

Sends non-fatal exception to Crashlytics with `FirebaseCrashlytics.recordException()`.

If no throwable is passed, it generates stack trace from caller of Timber.e() or etc.
NOTE: Stack trace elements of timber code are automatically removed before sent.

### CrashlyticsLogTree (Default log level: WARN)

Records log to Crashlytics with `FirebaseCrashlytics.log()`.

Recorded logs will be shown in each Crashes/Non-Fatals report.

### ThrowErrorTree (Default log level: ERROR)

Throws LogPriorityExceededError (extends Error) if log level exceeds specified level.

Useful when dogfooding, debugging, quality assurance. DON'T plant() this on production environment.


Filtering logs
----

### Specifying minimum log level

```java
CrashlyticsLogTree tree = new CrashlyticsLogTree(Log.INFO, FirebaseCrashlytics.getInstance());
```

### Excluding log by custom logic

```java
ThrowErrorTree tree = new ThrowErrorTree(Log.ERROR, new LogExclusionStrategy() {
    @Override
    public boolean shouldSkipLog(int priority, String tag, String message, Throwable t) {
        return message.startsWith("NO_FAIL_FAST");
    }
});
```


Installation
----

Available from jitpack.

Gradle:

```groovy
// main build.gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}

// project/app build.gradle
dependencies {
    compile 'com.github.ubuntudroid.timbertreeutils:timbertreeutils:2.0.0'
}
```


LICENSE
----

```
Copyright (C) 2015 Yuya Tanaka, 2020 Sven Bendel

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
