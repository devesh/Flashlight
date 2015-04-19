/*
 * Copyright 2012 Devesh Parekh
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package devesh.flashlight;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Flashlight extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        // Flashlights should be bright.
        WindowManager.LayoutParams params = window.getAttributes();
        params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
        // params.buttonBrightness was added in API level 8. The additional brightness is not worth the added code size.
        // The screen will go to max brightness even without the following line, but the API doesn't guarantee it.
        window.setAttributes(params);
        window.addFlags(
                // Use the power button to turn flashlight off and on, even if you have a lock screen.
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        // We won't do anything with touch events. Don't bother sending them to us.
                        // Unfortunately, there is a platform bug that still exists in 4.2 (!) that causes ANRs if this
                        // flag is set.
                        // | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        // A flashlight that turns itself off isn't a good flashlight.
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        // Turn the screen on if it isn't already on when launching (e.g., from ADB).
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }
}
