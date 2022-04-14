package ru.eababurin.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NoteFragment extends Fragment {

    private static final String NOTE_INDEX = "index";
    private static final String DATE_OF_CREATION = "date";
    private static final String HEADER = "header";
    private static final String CONTENT = "content";
    private static Note currentNote;
    private EditText headerEditText, dateOfCreationEditText, contentEditText;

    @NonNull
    public static NoteFragment newInstance(@NonNull Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();

        try {
            args.putInt(NOTE_INDEX, note.getId());
            args.putString(DATE_OF_CREATION, note.getDateOfCreation());
            args.putString(HEADER, note.getHeader());
            args.putString(CONTENT, note.getContent());
            fragment.setArguments(args);
            currentNote = note;
        } catch (NullPointerException e) {
            currentNote = Note.getNoteById(1);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            requireActivity().getSupportFragmentManager().popBackStack();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        //    private final Calendar calendar = Calendar.getInstance();
        Button buttonSave = view.findViewById(R.id.save_button);
        Button buttonInfo = view.findViewById(R.id.info_button);
        Button buttonRemove = view.findViewById(R.id.remove_button);
        headerEditText = view.findViewById(R.id.header);
        //dateOfCreationEditText = view.findViewById(R.id.date_of_creation);
        contentEditText = view.findViewById(R.id.content);

        if (arguments != null) {
            headerEditText.setText(getArguments().getString(HEADER));
//            dateOfCreationEditText.setText(getArguments().getString(DATE_OF_CREATION));
            contentEditText.setText(getArguments().getString(CONTENT));
        }

        buttonSave.setOnClickListener(v -> {
            currentNote.setHeader(headerEditText.getText().toString());
//            currentNote.setDateOfCreation(dateOfCreationEditText.getText().toString());
            currentNote.setContent(contentEditText.getText().toString());

            Toast.makeText(requireActivity(), R.string.data_saved, Toast.LENGTH_LONG).show();
        });

        buttonRemove.setOnClickListener(v -> {
            Toast.makeText(requireActivity(), R.string.data_removed, Toast.LENGTH_LONG).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        buttonInfo.setOnClickListener(v -> {
            InformationFragment informationFragment = new InformationFragment(currentNote);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.information_container, informationFragment)
                    .commit();
        });
    }
}