package com.example.fitpot;
import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.test.platform.app.InstrumentationRegistry;

public class AccelerometerUnitTest {
    private Accelerometer accelerometer;

    @Test
    public void AccelerometerTest()
    {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        accelerometer = new Accelerometer(appContext);
        assertNotNull(accelerometer);
        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                System.out.println("testing");
            }
        });
        accelerometer.register();
        accelerometer.unregister();
    }

    /*
    @Test
    public void testShake() throws InterruptedException {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        accelerometer = new Accelerometer(appContext);
        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                System.out.println("testing");
            }
        });
        accelerometer.register();
        SensorEventListener listener = accelerometer.getSensorEventListener();
        listener.onSensorChanged(SensorManager.SENSOR_ACCELEROMETER, new float[] {0, 0, 0} );
        //Required because method only allows one shake per 100ms
        Thread.sleep(500);
        listener.onSensorChanged(SensorManager.SENSOR_ACCELEROMETER, new float[] {300, 300, 300});
        assertTrue("Counter: " + mShaker.shakeCounter, mShaker.shakeCounter > 0);
        accelerometer.unregister();
    }
     */
}
