package ru.eababurin.notes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.eababurin.notes.R;
import ru.eababurin.notes.engine.OnDialogListener;

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {

    public MyBottomSheetDialogFragment() {
    }

    public static MyBottomSheetDialogFragment newInstance() {
        return new MyBottomSheetDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bottom_sheet_dialog, container, false);
        setCancelable(false);
        view.findViewById(R.id.button_ok).setOnClickListener(view1 -> {
            dismiss();
            OnDialogListener activity = (OnDialogListener) getActivity();
            if (activity != null) activity.onDialogOk();
        });

        view.findViewById(R.id.button_yes).setOnClickListener(view12 -> {
            dismiss();
            OnDialogListener activity = (OnDialogListener) getActivity();
            if (activity != null) activity.onDialogYes();
        });
        return view;
    }
}