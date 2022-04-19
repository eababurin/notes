package ru.eababurin.notes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ru.eababurin.notes.R;
import ru.eababurin.notes.engine.Memo;

public class NoteFragment extends Fragment {

    private TextView contentTextView;
    private Memo currentMemo;
    private final String MEMO_KEY = "MEMO_KEY";

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

        contentTextView = view.findViewById(R.id.view_note_text_view);

        if (arguments != null) {
            currentMemo = getArguments().getParcelable(MEMO_KEY);
            contentTextView.setText(currentMemo.getContent());
        }

        initToolbarAndDrawer(view);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        hideAndShowMenuItems(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                Toast.makeText(requireActivity(), R.string.toast_editing, Toast.LENGTH_LONG).show();
                break;
            case R.id.action_remove:
                Toast.makeText(requireActivity(), R.string.data_removed, Toast.LENGTH_LONG).show();
                requireActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.action_information:
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_viewing_a_note, AboutNoteFragment.newInstance(currentMemo))
                        .addToBackStack("")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static NoteFragment newInstance(Memo memo) {
        NoteFragment fragment = new NoteFragment();

        Bundle args = new Bundle();
        args.putParcelable(fragment.MEMO_KEY, memo);
        fragment.setArguments(args);

        return fragment;
    }

    private void hideAndShowMenuItems(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);

        menu.findItem(R.id.action_information).setVisible(true);
        menu.findItem(R.id.action_remove).setVisible(true);
        menu.findItem(R.id.action_edit).setVisible(true);
    }

    private void initToolbarAndDrawer(View view) {
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(currentMemo.getHeader());
        setHasOptionsMenu(true);
    }
}