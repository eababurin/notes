package ru.eababurin.notes;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InformationFragment extends Fragment {

    private final Calendar calendar = Calendar.getInstance();
    Note note;

    public InformationFragment(Note note) {
        this.note = note;
    }

    public InformationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView headerTextView = view.findViewById(R.id.headerTextView);
        TextView dataOfCreationTextView = view.findViewById(R.id.dateOfCreatedTextView);

        headerTextView.setText(note.getHeader());
        dataOfCreationTextView.setText(note.getDateOfCreation());

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateCalendar();
            }

            private void updateCalendar() {
                String format = "dd MM yy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
                dataOfCreationTextView.setText(dateFormat.format(calendar.getTime()));
            }
        };

        dataOfCreationTextView.setOnClickListener(v -> new DatePickerDialog(
                requireActivity(),
                date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show());
    }
}