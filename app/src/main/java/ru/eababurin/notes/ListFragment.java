package ru.eababurin.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class ListFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        hideAndShowMenuItems(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void hideAndShowMenuItems(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.action_select).setVisible(true);

        menu.findItem(R.id.action_information).setVisible(false);
        menu.findItem(R.id.action_remove).setVisible(false);
    }

    // когда макет фрагмента создан и готов к отображению
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        view.findViewById(R.id.settings_button).setOnClickListener(v -> openSettings());
        initToolbarAndDrawer(view);
        initList();

        view.findViewById(R.id.fab).setOnClickListener(v -> {
            Toast.makeText(requireActivity(), "...добавление заметки...", Toast.LENGTH_LONG).show();
        });
    }

    private void initToolbarAndDrawer(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((MainActivity) requireActivity()).setSupportActionBar(toolbar);
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

    private void initList() {
        ListOfNotesFragment listOfNotesFragment = new ListOfNotesFragment();
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.list_notes, listOfNotesFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void openSettings() {
        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_list, settingsFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(requireActivity(), "ПОИСК", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_select:
                Toast.makeText(requireActivity(), "ВЫБРАТЬ", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}