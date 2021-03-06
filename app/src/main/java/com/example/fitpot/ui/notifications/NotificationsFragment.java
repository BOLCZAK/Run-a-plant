package com.example.fitpot.ui.notifications;


import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitpot.R;
import com.example.fitpot.databinding.FragmentNotificationsBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private ToggleButton alarmToggle;
    private ToggleButton alarmRepToggle;
    private Button timePicker;
    private MaterialTimePicker materialTimePicker;
    private Calendar calendar;

    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;

    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        alarmToggle = binding.alarmToggle;
        alarmRepToggle = binding.alarmRepToggle;
        Intent notifyIntent = new Intent(getActivity(), AlarmReceiver.class);
        boolean alarmUp = (PendingIntent.getBroadcast(getActivity(), NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);
        timePicker =(Button) binding.setTime;
        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (getActivity(), NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        final AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String toastMessage;
                if(b) {
                    //Set the toast message for the "on" case
                    toastMessage = getString(R.string.alarm_on_toast);
                    long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

                    long triggerTime = SystemClock.elapsedRealtime()
                            + repeatInterval;

                    // If the Toggle is turned on, set the repeating alarm with
                    // a 15 minute interval.
                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating
                                (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                        triggerTime, repeatInterval,
                                        notifyPendingIntent);
                    }
                }else {
                    //Set the toast message for the "off" case
                    toastMessage = getString(R.string.alarm_off_toast);

                    if (alarmManager != null) {
                        alarmManager.cancel(notifyPendingIntent);
                    }

                    //mNotificationManager.cancelAll();

                }

//Show a toast to say the alarm is turned on or off
                Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT)
                        .show();
            }
        });

        alarmRepToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String toastMessage;
                if(b) {
                    //Set the toast message for the "on" case
                    toastMessage = getString(R.string.rp_al_set_up);

                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating
                                (AlarmManager.RTC_WAKEUP,
                                        calendar.getTimeInMillis(),
                                        AlarmManager.INTERVAL_DAY,
                                        notifyPendingIntent);
                    }
                }else {
                    //Set the toast message for the "off" case
                    toastMessage = getString(R.string.rp_al_set_off);

                    if (alarmManager != null) {
                        alarmManager.cancel(notifyPendingIntent);
                    }

                    //mNotificationManager.cancelAll();

                }

//Show a toast to say the alarm is turned on or off
                Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT)
                        .show();
            }
        });

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        createNotificationChannel();

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        return root;
    }

    private void showTimePicker() {

        materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();

        materialTimePicker.show(getActivity().getSupportFragmentManager(), "time_picker");

        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(materialTimePicker.getHour() > 12)
                {
                    binding.alarmTime.setText(
                            String.format("%02d", materialTimePicker.getHour()-12)
                                    + " : "
                                    + String.format("%02d", materialTimePicker.getMinute())
                                    + "PM"
                    );
                }
                else {
                    binding.alarmTime.setText(
                            String.format("%02d", materialTimePicker.getHour())
                                    + " : "
                                    + String.format("%02d", materialTimePicker.getMinute())
                                    + "AM"
                    );
                }

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());
                calendar.set(Calendar.MINUTE, materialTimePicker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void deliverNotification(Context context) {

    }

    public void createNotificationChannel() {
        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        mNotificationManager = (NotificationManager)
                getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

}