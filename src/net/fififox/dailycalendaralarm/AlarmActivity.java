package net.fififox.dailycalendaralarm;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class AlarmActivity extends Activity implements OnClickListener {

    /**
     * Creates a dialog-styled activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alarm);

        Button button = (Button) findViewById(R.id.alarm_dismiss_button);
        button.setOnClickListener(this);

        TextView titleText = (TextView) findViewById(R.id.alarm_title_text);
        titleText.setText(getIntent().getStringExtra("title"));

        long time = getIntent().getLongExtra("time", System.currentTimeMillis());
        TextView timeText = (TextView) findViewById(R.id.alarm_time_text);
        timeText.setText(DateUtils.getRelativeTimeSpanString(time));

    }

    /**
     * Closes the activity when the "Dismiss" button is pressed.
     */
    @Override
    public void onClick(View v) {
        finish();
    }

    /**
     * Stops the alarm by removing the notification when the activity is closed by the user.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(getIntent().getIntExtra("id", -1));
        }
    }

}
