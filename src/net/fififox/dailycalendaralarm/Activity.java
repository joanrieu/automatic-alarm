package net.fififox.dailycalendaralarm;

import net.fififox.dailycalendaralarm.OffsetSpinner.OnOffsetSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * The main activity of the app, showing an On/Off switch and an offset selector.
 * @author Joan Rieu
 */
public class Activity extends android.app.Activity implements OnCheckedChangeListener, OnOffsetSetListener {

    /**
     * The preferences shared with the {@link Service}.
     */
    private Settings mSettings;

    /**
     * Starts the service and registers the event listeners for the views.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Service

        mSettings = new Settings(this);
        configureService();

        // UI

        CompoundButton toggle = (CompoundButton) findViewById(R.id.OnOffToggle);
        toggle.setChecked(mSettings.isEnabled());
        toggle.setOnCheckedChangeListener(this);

        OffsetSpinner spinner = (OffsetSpinner) findViewById(R.id.OffsetSpinner);
        spinner.setAdapter(new OffsetSpinner.OffsetAdapter(spinner, R.layout.activity_main_spinner));
        spinner.setOffset(mSettings.getOffsetInMinutes());
        spinner.setOnOffsetSetListener(this);

    }

    /**
     * Enables or disables the alarm service.
     * @param isChecked Is the service enabled?
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mSettings.setEnabled(isChecked);
        configureService();
    }

    /**
     * Changes the alarm offset.
     * @param offset The new offset in minutes.
     */
    @Override
    public void onOffsetSet(OffsetSpinner spinnerView, int offset) {
        mSettings.setOffsetInMinutes(offset);
        configureService();
    }

    /**
     * Triggers a service configuration update by sending an {@link Intent}.
     */
    private void configureService() {
        Intent intent = new Intent(this, Service.class);
        intent.setAction(Intent.ACTION_CONFIGURATION_CHANGED);
        startService(intent);
    }

}
