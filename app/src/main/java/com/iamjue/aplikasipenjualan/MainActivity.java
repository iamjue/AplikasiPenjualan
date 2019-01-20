package com.iamjue.aplikasipenjualan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.iamjue.aplikasipenjualan.adapter.ViewPagerAdapter;

import static com.iamjue.aplikasipenjualan.LoginActivity.TAG_ID;
import static com.iamjue.aplikasipenjualan.LoginActivity.TAG_USERNAME;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        toolbar = (Toolbar) findViewById( R.id.toolbar );
        tabLayout = (TabLayout) findViewById( R.id.tabs );
        viewPager = (ViewPager) findViewById( R.id.viewPager );

        setupToolbar();
        init();


        sharedpreferences = getSharedPreferences( LoginActivity.my_shared_preferences, Context.MODE_PRIVATE );
    }

    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar( toolbar );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }
    }

    private void init() {
        setupViewPager( viewPager );
        tabLayout.setupWithViewPager( viewPager );
    }

    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter( getSupportFragmentManager(), this );
        viewPager.setAdapter( viewPagerAdapter );
        viewPager.setOffscreenPageLimit( 3 );
        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );

        tabLayout.setTabsFromPagerAdapter( viewPagerAdapter );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected( item );
    }

    private void logout() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean( LoginActivity.session_status, false );
        editor.putString( TAG_ID, null );
        editor.putString( TAG_USERNAME, null );
        editor.commit();

        Intent intent = new Intent( MainActivity.this, LoginActivity.class );
        finish();
        startActivity( intent );
    }
}
