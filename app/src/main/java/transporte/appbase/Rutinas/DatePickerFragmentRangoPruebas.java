package transporte.appbase.Rutinas;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by soled_000 on 18/7/2016.
 */

//hardCode rango de pruebas
public class DatePickerFragmentRangoPruebas extends DatePickerFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        Calendar cMin = Calendar.getInstance();
        int year = cMin.get(Calendar.YEAR);
        int month = cMin.get(Calendar.MONTH);
        int day = cMin.get(Calendar.DAY_OF_MONTH);
        cMin.set(2015, 9, 1);

        Calendar cMax = Calendar.getInstance();
        cMax.set(2015, 11, 31);
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),this, year, month, day);
        dialog.getDatePicker().setMinDate(cMin.getTimeInMillis());
        dialog.getDatePicker().setMaxDate(cMax.getTimeInMillis());
        return dialog;
    }
}
