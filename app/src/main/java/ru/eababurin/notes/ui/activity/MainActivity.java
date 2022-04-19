package ru.eababurin.notes.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import ru.eababurin.notes.R;
import ru.eababurin.notes.ui.fragment.AboutFragment;
import ru.eababurin.notes.ui.fragment.ListFragment;
import ru.eababurin.notes.ui.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    // фрагмент для редактирования/создания новой заметки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeToolbar();
        initializeDrawer();

        if (savedInstanceState == null) getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_list_of_notes, new ListFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        hideAndShowOptionsMenuItems(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, R.string.action_search_toast, Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideAndShowOptionsMenuItems(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(true); // кнопка поиска

        menu.findItem(R.id.action_information).setVisible(false); // кпопка "инфо о заметке"
        menu.findItem(R.id.action_edit).setVisible(false); // кнопка "изменить"
        menu.findItem(R.id.action_remove).setVisible(false); // кнопка "удалить"
    }

    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
    }

    private void initializeDrawer() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                findViewById(R.id.toolbar),
                R.string.navigation_drower_open,
                R.string.navigation_drower_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_drawer_all_notes:
                        openListNotesFragment();
                        drawerLayout.close();
                        return true;
                    case R.id.action_drawer_about:
                        openAboutFragment();
                        drawerLayout.close();
                        return true;
                    case R.id.action_drawer_open_settings:
                        openSettingsFragment();
                        drawerLayout.close();
                        return true;
                    case R.id.action_drawer_exit:
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    private void openAboutFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_list_of_notes, new AboutFragment())
                .addToBackStack("")
                .commit();
    }

    private void openListNotesFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_list_of_notes, new ListFragment())
                .commit();
    }

    private void openSettingsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_list_of_notes, new SettingsFragment())
                .addToBackStack("")
                .commit();
    }
}