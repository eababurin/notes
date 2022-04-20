package ru.eababurin.notes.ui.fragment;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

import ru.eababurin.notes.R;
import ru.eababurin.notes.engine.Memo;

public class ListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeToolbar();
        initializingListOfNotes(view);

        view.findViewById(R.id.fab).setOnClickListener(v -> {
            showNotification();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        String name = "name";
        String descriptionText = "Description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
        channel.setDescription(descriptionText);

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireActivity(), "CHANNEL_ID");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText(getResources().getString(R.string.notification_description))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat.from(requireActivity()).notify(42, builder.build());
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_context_open:
                Toast.makeText(requireActivity(), R.string.opening, Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_context_edit:
                Toast.makeText(requireActivity(), R.string.toast_editing, Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_context_remove:
                Snackbar.make(getActivity().findViewById(R.id.textView), R.string.snackbar_remove_description, Snackbar.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void initializingListOfNotes(View view) {
        LinearLayout linearLayout = view.findViewById(R.id.list_of_notes);

        if (Memo.memos.size() == 0) {
            createFirstMemo();
        }

        drawListOfNotes(linearLayout);

        if (Memo.memos.size() != 0) {
            view.findViewById(R.id.notes_are_missing_text_size).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.notes_are_missing_text_size).setVisibility(View.VISIBLE);
        }
    }

    private void createFirstMemo() {
        Memo nm = new Memo(
                "Ваша первая заметка",
                "Содержание заметки можно поменять в любой момент!",
                new Date());
    }

    private void drawListOfNotes(LinearLayout linearLayout) {
        for (Memo memo : Memo.memos) {
            LinearLayout.LayoutParams headerParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams contentParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            headerParams.setMargins(16, 16, 16, 0);
            contentParams.setMargins(16, 4, 16, 0);

            TextView headerTextView = new TextView(getContext());
            headerTextView.setText(memo.getHeader());
            headerTextView.setTextColor(getResources().getColor(R.color.black));
            headerTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.header_text_size));

            TextView contentTextView = new TextView(getContext());
            contentTextView.setText(memo.getContent());
            contentTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.content_text_size));
            contentTextView.setLines(2);

            headerTextView.setLayoutParams(headerParams);
            contentTextView.setLayoutParams(contentParams);

            linearLayout.addView(headerTextView);
            linearLayout.addView(contentTextView);

            registerForContextMenu(headerTextView);
            registerForContextMenu(contentTextView);

            headerTextView.setOnClickListener(v -> {
                if (isLandscapeConfiguration()) {
                    openNoteInLandscapeConfiguration(memo);
                } else {
                    openNoteInPortraitConfiguration(memo);
                }
            });

            contentTextView.setOnClickListener(v -> {
                if (isLandscapeConfiguration()) {
                    openNoteInLandscapeConfiguration(memo);
                } else {
                    openNoteInPortraitConfiguration(memo);
                }
            });
        }
    }

    private void initializeToolbar() {
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setHasOptionsMenu(true);
    }

    private void openNoteInPortraitConfiguration(Memo memo) {
        NoteFragment noteFragment = NoteFragment.newInstance(memo);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_list_of_notes, noteFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void openNoteInLandscapeConfiguration(Memo memo) {
        NoteFragment noteFragment = NoteFragment.newInstance(memo);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_viewing_a_note, noteFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private boolean isLandscapeConfiguration() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}