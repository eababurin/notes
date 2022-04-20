package ru.eababurin.notes.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.eababurin.notes.R;
import ru.eababurin.notes.engine.OnDialogListener;
import ru.eababurin.notes.ui.fragment.MyBottomSheetDialogFragment;

public class AdditionalActivity extends AppCompatActivity implements OnDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional);

        findViewById(R.id.button_bottom_sheet_dialog_fragment).setOnClickListener(v -> showBottomSheetDialogFragment());
    }

    private void showBottomSheetDialogFragment() {
        MyBottomSheetDialogFragment dialogFragment = MyBottomSheetDialogFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "MyBottomSheetDialogFragment");
    }

    @Override
    public void onDialogOk() {
        Toast.makeText(AdditionalActivity.this, getString(R.string.toast_press_ok), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogYes() {
        Toast.makeText(AdditionalActivity.this, getString(R.string.toast_press_yes), Toast.LENGTH_SHORT).show();
    }
}