package com.amarpreetsinghprojects.firebaseapp;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

   // ArrayList<String> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //arraylist.add("yes");

        final EditText taskETV = (EditText)findViewById(R.id.taskETV);
        final EditText idETV = (EditText)findViewById(R.id.taskid);
        final Button addButton = (Button)findViewById(R.id.addButton);

        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        if (fbAuth.getCurrentUser() == null){
            // signin /signup
            Intent signInIntent  = AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(
                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                    .setIsSmartLockEnabled(false)
                    .build();
            startActivityForResult(signInIntent,111);
        }

        //FirebaseAuth.getInstance().getCurrentUser().updateEmail(updatedEmail);


        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.task_noteRadiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference dbRef = database.getReference();

                dbRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> snapshotIterable = dataSnapshot.child("todo").getChildren();

                        for (DataSnapshot ds : snapshotIterable){
                            TodoClass currentTodo = ds.getValue(TodoClass.class);
                            Log.d("TAG", "onDataChange: "+currentTodo.getId());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                switch (checkedId){

                    case R.id.taskRadioButton: final TodoClass currentTODO = new TodoClass(idETV.getText().toString(),
                            taskETV.getText().toString(),
                            "Pending");
                        addButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {



                                dbRef.child("todo").child(currentTODO.getId()).setValue(currentTODO)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(MainActivity.this,"Text was added",Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        });
                        break;
                    case R.id.noteRadioButton : final Note currentNote = new Note(taskETV.getText().toString(), idETV.getText().toString());
                        addButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dbRef.child("Note").child(currentNote.getId()).setValue(currentNote)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(MainActivity.this,"Text was added",Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        });
                        break;
                }



            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==111) {
            Toast.makeText(this, "Welcome user" + FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
        }
    }
}
