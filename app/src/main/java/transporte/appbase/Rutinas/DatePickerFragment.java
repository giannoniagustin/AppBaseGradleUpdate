package transporte.appbase.Rutinas;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.Date;

//ojo numero meses
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener  {

    private OnDateSetListener mDateSetListener;

    public DatePickerFragment() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),this, year, month, day);
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        return dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (this.mDateSetListener != null) {
            this.mDateSetListener.onDateSet(view, year, month, day);
        }
    }
    public void setOnDateSetListener(OnDateSetListener dateSetListener) {
        this.mDateSetListener = dateSetListener;
    }

    public interface OnDateSetListener {
        void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth);
    }

}