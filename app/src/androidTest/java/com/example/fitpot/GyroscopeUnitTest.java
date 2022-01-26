package com.example.fitpot;

import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

public class GyroscopeUnitTest {
    private Gyroscope gyroscope;

    @Test
    public void GyroscopeTest()
    {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        gyroscope = new Gyroscope(appContext);
        assertNotNull(gyroscope);
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                System.out.println("testing");
            }
        });
        gyroscope.register();
        gyroscope.unregister();
    }
}
