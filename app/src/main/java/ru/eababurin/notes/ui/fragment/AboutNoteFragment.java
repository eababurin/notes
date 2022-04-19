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

import ru.eababurin.notes.R;
import ru.eababurin.notes.engine.Memo;

public class AboutNoteFragment extends Fragment {

    private final String MEMO_KEY = "memo_key";
    private Memo memo;

    public AboutNoteFragment() { }

    public static AboutNoteFragment newInstance(Memo memo) {
        AboutNoteFragment fragment = new AboutNoteFragment();

        Bundle args = new Bundle();
        args.putParcelable(fragment.MEMO_KEY, memo);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_note, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        hideAndShowMenuItems(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                Toast.makeText(requireActivity(), R.string.toast_editing, Toast.LENGTH_LONG).show();
                break;
            case R.id.action_remove:
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_list_of_notes, new ListFragment())
                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(requireActivity().getString(R.string.note_info_toolbar_title));
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            memo = getArguments().getParcelable(MEMO_KEY);

            TextView aboutIdTextView = view.findViewById(R.id.about_id_text_view);
            TextView aboutHeaderTextView = view.findViewById(R.id.about_header_text_view);
            TextView aboutDateOfCreationTextView = view.findViewById(R.id.about_date_of_creation_text_view);
            TextView aboutContentTextView = view.findViewById(R.id.about_content_text_view);

            aboutIdTextView.setText(Integer.toString(memo.getId()));
            aboutHeaderTextView.setText(memo.getHeader());
            aboutDateOfCreationTextView.setText(memo.getDateOfCreation().toString());
            aboutContentTextView.setText(memo.getContent());
        }
    }

    private void hideAndShowMenuItems(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_information).setVisible(false);
        menu.findItem(R.id.action_remove).setVisible(true);
        menu.findItem(R.id.action_edit).setVisible(true);
    }
}