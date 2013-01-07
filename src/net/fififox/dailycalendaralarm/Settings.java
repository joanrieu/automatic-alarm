package net.fififox.dailycalendaralarm;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Provides access to the app's settings, shared by {@link SetupActivity} and {@link AlarmService}.
 * @author Joan Rieu
 */
public class Settings {

    /**
     * The name of the {@link SharedPreferences} file storing all the settings.
     */
    private static final String SETTINGS_FILE = "settings";

    /**
     * The {@link SharedPreferences} key for the app's global On/Off state.
     */
    private static final String ENABLED_KEY = "enabled";

    /**
     * The default global On/Off state.
     */
    private static final boolean ENABLED_DEFAULT = false;

    /**
     * The {@link SharedPreferences} key for the alarm offset in minutes.
     */
    private static final String OFFSET_KEY = "offset_minutes";

    /**
     * The default alarm offset in minutes.
     */
    private static final int OFFSET_DEFAULT = 90;

    /**
     * The {@link SharedPreferences} key for the last time the alarm rang.
     */
    private static final String LAST_ALARM_TIME_KEY = "last_alarm_time";

    /**
     * The {@link SharedPreferences} key for the next alarm's event title.
     */
    private static final String NEXT_ALARM_TITLE_KEY = "next_alarm_title";

    /**
     * The {@link SharedPreferences} key for the next time the will ring.
     */
    private static final String NEXT_ALARM_TIME_KEY = "next_alarm_time";

    /**
     * The settings this class wraps.
     */
    private final SharedPreferences mPrefs;

    /**
     * Retrieves the settings from the context.
     * @param context The app's context.
     */
    public Settings(Context context) {
        mPrefs = context.getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    /**
     * @see #ENABLED_KEY
     */
    public boolean isEnabled() {
        return mPrefs.getBoolean(ENABLED_KEY, ENABLED_DEFAULT);
    }

    /**
     * @see #ENABLED_KEY
     */
    public void setEnabled(boolean enabled) {
        mPrefs.edit().putBoolean(ENABLED_KEY, enabled).apply();
    }

    /**
     * @return The alarm offset in milliseconds.
     * @see #OFFSET_KEY
     */
    public long getOffsetInMillis() {
        return getOffsetInMinutes() * 60 * 1000;
    }

    /**
     * @return The alarm offset in minutes.
     * @see #OFFSET_KEY
     */
    public int getOffsetInMinutes() {
        return mPrefs.getInt(OFFSET_KEY, OFFSET_DEFAULT);
    }

    /**
     * @see #OFFSET_KEY
     */
    public void setOffsetInMinutes(int offsetInMinutes) {
        mPrefs.edit().putInt(OFFSET_KEY, offsetInMinutes).apply();
    }

    /**
     * @see #LAST_ALARM_TIME_KEY
     */
    public long getLastAlarmTime() {
        return mPrefs.getLong(LAST_ALARM_TIME_KEY, 0);
    }

    /**
     * @see #LAST_ALARM_TIME_KEY
     */
    public void setLastAlarmTime(long lastAlarmTime) {
        mPrefs.edit().putLong(LAST_ALARM_TIME_KEY, lastAlarmTime).apply();
    }

    /**
     * @see #NEXT_ALARM_TITLE_KEY
     */
    public String getNextAlarmTitle() {
        return mPrefs.getString(NEXT_ALARM_TITLE_KEY, null);
    }

    /**
     * @see #NEXT_ALARM_TITLE_KEY
     */
    public void setNextAlarmTitle(String nextAlarmTitle) {
        mPrefs.edit().putString(NEXT_ALARM_TITLE_KEY, nextAlarmTitle).apply();
    }

    /**
     * @see #NEXT_ALARM_TIME_KEY
     */
    public long getNextAlarmTime() {
        return mPrefs.getLong(NEXT_ALARM_TIME_KEY, 0);
    }

    /**
     * @see #NEXT_ALARM_TIME_KEY
     * @param nextAlarmTime
     */
    public void setNextAlarmTime(long nextAlarmTime) {
        mPrefs.edit().putLong(NEXT_ALARM_TIME_KEY, nextAlarmTime).apply();
    }

    /**
     * Clears the title and the time of the next alarm.
     * @see {@link #NEXT_ALARM_TITLE_KEY}
     * @see {@link #NEXT_ALARM_TIME_KEY}.
     */
    public void clearNextAlarm() {
        mPrefs.edit().remove(NEXT_ALARM_TITLE_KEY).remove(NEXT_ALARM_TIME_KEY).apply();
    }

}
