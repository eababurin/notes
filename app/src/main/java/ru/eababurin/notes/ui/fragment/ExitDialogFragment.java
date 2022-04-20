package ru.eababurin.notes.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.eababurin.notes.R;

public class ExitDialogFragment extends DialogFragment {

    public static final String TAG = "ExitDialogFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.exit_dialog_title)
                .setMessage(R.string.exit_dialog_message)
                .setIcon(R.drawable.ic_baseline_red_alert_24)
                .setCancelable(false)
                .setPositiveButton(R.string.exit_dialog_positive_button, (dialogInterface, i) -> {
                    Toast.makeText(requireActivity(), R.string.exit_dialog_toast_exit, Toast.LENGTH_SHORT).show();
                    requireActivity().finish();
                })
                .setNegativeButton(R.string.exit_dialog_nevative_button, (dialogInterface, i) -> Toast.makeText(requireActivity(), R.string.exit_dialog_toast_stay, Toast.LENGTH_SHORT).show())
                .setNeutralButton(R.string.exit_dialog_neutral_button, null)
                .show();
    }
}