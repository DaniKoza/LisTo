package com.example.listo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    private NoteAnimationView noteAnimationView;
    private Button BTN_join_room, BTN_create_room, BTN_user_manual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();

        BTN_user_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHowToUseDialog();
            }
        });

    }

    private void showHowToUseDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Usage")
                .setMessage("All you have to do to add a song to a playlist is very simple:\n 1) Go into Youtube app or site.\n" +
                        " 2) Find your wanted song.\n 3) Press on share and copy link.\n 4) Go back to the app and press the 'Add Song' button.\n" +
                        "5) Paste the link you've copied and hit the 'Add' button.")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Understood
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
//                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_info)
                .show();
    }

    private void findViews() {
        noteAnimationView = findViewById(R.id.noteAnimationView);
        noteAnimationView.setVisibility(View.VISIBLE);
        BTN_join_room = findViewById(R.id.BTN_join_room);
        BTN_create_room = findViewById(R.id.BTN_create_room);
        BTN_user_manual = findViewById(R.id.BTN_user_manual);
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAnimationView.resume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAnimationView.pause();
    }
}
