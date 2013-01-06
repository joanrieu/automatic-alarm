package net.fififox.dailycalendaralarm;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * A {@link Spinner} opening a 24h {@link TimePickerDialog} when clicked.
 * @author Joan Rieu
 */
public class OffsetSpinner extends Spinner implements OnTimeSetListener {

    /**
     * The callback interface used to indicate the offset in an {@link OffsetSpinner} has changed.
     */
    public interface OnOffsetSetListener {

        /**
         * Called when the offset in the {@link OffsetSpinner} has changed.
         * @param spinnerView The spinner in which the offset has changed.
         * @param offset The new offset.
         */
        public abstract void onOffsetSet(OffsetSpinner spinnerView, int offset);

    }

    /**
     * A special {@link Adapter} which only shows the offset from {@link OffsetSpinner#getOffset()}.
     */
    public static class OffsetAdapter extends ArrayAdapter<CharSequence> {

        /**
         * The {@link String} used to separate hours from minutes in the {@link TextView}.
         */
        private static final String HOURS_MINUTES_SEPARATOR = ":";

        /**
         * The {@link OffsetSpinner} from which the displayed offset is retrieved.
         */
        private final OffsetSpinner mOffsetSpinner;

        /**
         * Creates an ArrayAdapter containing one value.
         * @param offsetSpinner The {@link OffsetSpinner} to get the offset from.
         * @param textViewResourceId The resource id of the layout file containing your {@link TextView}.
         */
        public OffsetAdapter(OffsetSpinner offsetSpinner, int textViewResourceId) {
            super(offsetSpinner.getContext(), textViewResourceId);
            mOffsetSpinner = offsetSpinner;
            add(null);
        }

        /**
         * Returns a formatted {@link String} containing the offset from {@link OffsetSpinner#getOffset()}.
         * The text format is "H:MM".
         * @param position Ignored.
         */
        @Override
        public CharSequence getItem(int position) {
            int offset = mOffsetSpinner.getOffset();
            int hours = offset / 60;
            int minutes = offset % 60;
            return hours + HOURS_MINUTES_SEPARATOR + (minutes < 10 ? "0" : "") + minutes;
        }

    }

    /**
     * The offset displayed in the {@link OffsetSpinner}.
     */
    private int mOffset;

    /**
     * The listener called on offset changes.
     */
    private OnOffsetSetListener mOnOffsetSetListener;

    /**
     * The special one-value adapter given to the {@link Spinner}.
     */
    private OffsetAdapter mAdapter;

    /**
     * XML-compatible constructor.
     */
    public OffsetSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Define the {@link OffsetAdapter} which will display the time.
     * @param adapter The new {@link OffsetAdapter}.
     */
    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        mAdapter = (OffsetAdapter) adapter;
        super.setAdapter(mAdapter);
    }

    /**
     * Get the offset shown in the spinner.
     * @return The offset in minutes.
     */
    public int getOffset() {
        return mOffset;
    }

    /**
     * Set the offset shown in the picker.
     * @param offset The offset in minutes.
     */
    public void setOffset(int offset) {
        mOffset = offset;
        mAdapter.notifyDataSetChanged();
        if (getOnOffsetSetListener() != null) {
            getOnOffsetSetListener().onOffsetSet(this, getOffset());
        }
    }

    /**
     * Get the current listener for offset changes.
     * @return The current listener.
     */
    public OnOffsetSetListener getOnOffsetSetListener() {
        return mOnOffsetSetListener;
    }

    /**
     * Set the listener for offset changes.
     * @param onOffsetSetListener The new listener.
     */
    public void setOnOffsetSetListener(OnOffsetSetListener onOffsetSetListener) {
        mOnOffsetSetListener = onOffsetSetListener;
    }

    /**
     * Displays the offset picker dialog.
     */
    @Override
    public boolean performClick() {
        TimePickerDialog dialog = new TimePickerDialog(
                getContext(),
                this,
                getOffset() / 60,
                getOffset() % 60,
                true
        );
        dialog.show();
        return true;
    }

    /**
     * The listener bound to the selection dialog.
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        setOffset(hourOfDay * 60 + minute);
    }

}
