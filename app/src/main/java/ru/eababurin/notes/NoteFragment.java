package ru.eababurin.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.toolbar_menu, menu);
        hideAndShowMenuItems(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void hideAndShowMenuItems(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_select).setVisible(false);

        menu.findItem(R.id.action_information).setVisible(true);
        menu.findItem(R.id.action_remove).setVisible(true);
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

    private void initToolbarAndDrawer(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((MainActivity) requireActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(currentNote.getHeader());
        setHasOptionsMenu(true);

        // настраиваем DrawerLayout
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        // создаём ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawerLayout, toolbar,
                R.string.navigation_drower_open,
                R.string.navigation_drower_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // обработка навигационного меню
        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
//                switch (id) {
//                    case R.id.action_drawer_about:
//                        drawerLayout.close();
//                        return true;
//                    case R.id.action_drawer_exit:
//                        requireActivity().finish();
//                        return true;
//                }
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_information:
                Toast.makeText(requireActivity(), "Информвция о заметке", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_remove:
                Toast.makeText(requireActivity(), R.string.data_removed, Toast.LENGTH_LONG).show();
                requireActivity().getSupportFragmentManager().popBackStack();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbarAndDrawer(view);
        Bundle arguments = getArguments();
        //    private final Calendar calendar = Calendar.getInstance();
        Button buttonSave = view.findViewById(R.id.save_button);
        Button buttonInfo = view.findViewById(R.id.info_button);
        Button buttonRemove = view.findViewById(R.id.remove_button);
//        headerEditText = view.findViewById(R.id.header);
        //dateOfCreationEditText = view.findViewById(R.id.date_of_creation);
        contentEditText = view.findViewById(R.id.content);

        if (arguments != null) {
//            headerEditText.setText(getArguments().getString(HEADER));
//            dateOfCreationEditText.setText(getArguments().getString(DATE_OF_CREATION));
            contentEditText.setText(getArguments().getString(CONTENT));
        }

        buttonSave.setOnClickListener(v -> {
//            currentNote.setHeader(headerEditText.getText().toString());
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