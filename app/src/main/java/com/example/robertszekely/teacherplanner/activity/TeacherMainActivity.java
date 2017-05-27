package com.example.robertszekely.teacherplanner.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.robertszekely.teacherplanner.fragment.FeatureListFragment;
import com.example.robertszekely.teacherplanner.fragment.IterationListFragment;
import com.example.robertszekely.teacherplanner.fragment.StudentListFragment;
import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.fragment.TaskListFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class TeacherMainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        StudentListFragment.StudentDataPassListener,
        IterationListFragment.IterationDataPassListener,
        FeatureListFragment.FeatureDataPassListener {

    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFireBase();
//        addTeacher();
//        addIteration();
//        addFeatures();
//        addTasks();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        StudentListFragment studentListFragment = new StudentListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, studentListFragment, "masterfrag")
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void setupFireBase() {
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null) {
            //user already signed in
            Log.d("-----------AUTH-------", auth.getCurrentUser().getEmail());
            Log.d("-----------AUTH-------", auth.getCurrentUser().getUid());
        } else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setProviders(
                            AuthUI.EMAIL_PROVIDER,
                            AuthUI.GOOGLE_PROVIDER,
                            AuthUI.FACEBOOK_PROVIDER,
                            AuthUI.TWITTER_PROVIDER)
                    .build(), RC_SIGN_IN);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calendar) {

        } else if (id == R.id.nav_students) {
//            navigateToActivity(StudentListActivity.class, null);
            navigateToStudentListFragment();

        } else if (id == R.id.nav_notes) {
//            navigateToActivity(NoteListActivity.class, null);

        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_settings) {
            setupFireBase();

        } else if (id == R.id.nav_logout) {
            logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("AUTH", "USER LOGGED OUT");
//                        finish();
                        setupFireBase();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void passStudentData(String data) {
        IterationListFragment detailFragment = new IterationListFragment();
        Bundle args = new Bundle();
        args.putString(IterationListFragment.DATA_RECEIVE, data);
        detailFragment.setArguments(args);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, detailFragment)
                .addToBackStack("Students Fragment")
                .commit();
    }

    @Override
    public void passIterationData(String data) {
        FeatureListFragment featureListFragment = new FeatureListFragment();
        Bundle args = new Bundle();
        args.putString(FeatureListFragment.DATA_RECEIVE, data);
        featureListFragment.setArguments(args);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, featureListFragment)
                .addToBackStack("Feature list Fragment")
                .commit();
    }

    @Override
    public void passFeatureData(String data) {
        TaskListFragment taskListFragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putString(TaskListFragment.DATA_RECEIVE, data);
        taskListFragment.setArguments(args);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, taskListFragment)
                .addToBackStack("Task list Fragment")
                .commit();
    }
}
