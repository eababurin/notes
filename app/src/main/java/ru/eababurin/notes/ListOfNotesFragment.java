package ru.eababurin.notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ListOfNotesFragment extends Fragment {

    private static final String CURRENT_NOTE = "CurrentNote";
    private int currentNoteId = 0;

    public ListOfNotesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_of_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            currentNoteId = savedInstanceState.getInt(CURRENT_NOTE, 0);
        }
        initList(view);

        if (isLandscape()) {
            showLandNote(currentNoteId);
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu,
                                    @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_context_open:
                Toast.makeText(requireActivity(), "Открываем...", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_context_remove:
                Toast.makeText(requireActivity(), R.string.data_removed, Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    private void initList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;

        Note[] notes = {
                new Note(1, "", "Заметка 1", "Содержание заметки 1"),
                new Note(2, "", "Заметка 2", "Содержание заметки 2"),
        };

        for (Note note : notes) {
            TextView textView = new TextView(getContext());
            textView.setText(note.getId() + ". " + note.getHeader());
            textView.setTextSize(30);
            linearLayout.addView(textView);
            registerForContextMenu(textView);

            textView.setOnClickListener(v -> {
                currentNoteId = note.getId();
                showNote(note);
            });
        }
    }

    private void showNote(Note note) {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showLandNote(note);
        } else {
            showPortNote(note);
        }
    }

    private void showPortNote(Note note) {
        NoteFragment noteFragment = NoteFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_list, noteFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void showLandNote(Note note) {
        NoteFragment noteFragment = NoteFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_note, noteFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void showLandNote(int currentNoteId) {
        NoteFragment noteFragment = NoteFragment.newInstance(Note.getNoteById(currentNoteId));
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_note, noteFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, currentNoteId);
        super.onSaveInstanceState(outState);
    }
}