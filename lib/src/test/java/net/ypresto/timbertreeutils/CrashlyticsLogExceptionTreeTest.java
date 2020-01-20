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

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CrashlyticsLogExceptionTreeTest {
    @Test
    public void testIsLoggable() throws Exception {
        CrashlyticsLogExceptionTree tree = new CrashlyticsLogExceptionTree(FirebaseCrashlytics.getInstance());
        assertTrue(tree.isLoggable(Log.ERROR));
        assertFalse(tree.isLoggable(Log.WARN));

        tree = new CrashlyticsLogExceptionTree(Log.INFO, FirebaseCrashlytics.getInstance());
        assertTrue(tree.isLoggable(Log.INFO));
        assertFalse(tree.isLoggable(Log.DEBUG));
    }

    // TODO: Test on Crashlytics static method
}
