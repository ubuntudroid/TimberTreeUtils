/*
 * Copyright (C) 2015 Yuya Tanaka, 2020 Sven Bendel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ypresto.timbertreeutils;

import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

/**
 * An implementation of {@link Timber.Tree} which sends log to Crashlytics.
 *
 * @author ypresto
 * @see CrashlyticsLogExceptionTree
 */
public class CrashlyticsLogTree extends Timber.Tree {
    private final int mLogPriority;
    private final LogExclusionStrategy mLogExclusionStrategy;
    private final FirebaseCrashlytics mCrashlytics;

    /**
     * Create instance with default log priority of WARN.
     *
     * @param crashlytics FirebaseCrashlytics instance
     */
    public CrashlyticsLogTree(FirebaseCrashlytics crashlytics) {
        this(Log.WARN, crashlytics);
    }

    /**
     * @param logPriority Minimum log priority to send log. Expects one of constants defined in {@link Log}.
     * @param crashlytics FirebaseCrashlytics instance
     */
    public CrashlyticsLogTree(int logPriority, FirebaseCrashlytics crashlytics) {
        this(logPriority, null, crashlytics);
    }

    /**
     * @param logPriority          Minimum log priority to send log. Expects one of constants defined in {@link Log}.
     * @param logExclusionStrategy Strategy used to skip logging.
     * @param crashlytics FirebaseCrashlytics instance
     */
    public CrashlyticsLogTree(int logPriority, @Nullable LogExclusionStrategy logExclusionStrategy, FirebaseCrashlytics crashlytics) {
        // Ensure crashlytics class is available, fail-fast if not available.
        FirebaseCrashlytics.class.getCanonicalName();
        mLogPriority = logPriority;
        mLogExclusionStrategy = logExclusionStrategy != null ? logExclusionStrategy : NullLogExclusionStrategy.INSTANCE;
        mCrashlytics = crashlytics;
    }

    @Override
    protected boolean isLoggable(@Nullable String tag, int priority) {
        return priority >= mLogPriority;
    }

    @Override
    protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) {
        if (mLogExclusionStrategy.shouldSkipLog(priority, tag, message, t)) {
            return;
        }

        String formattedMessage = LogMessageHelper.format(priority, tag, message);
        mCrashlytics.log(formattedMessage);
    }
}
