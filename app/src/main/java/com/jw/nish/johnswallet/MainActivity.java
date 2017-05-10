package com.jw.nish.johnswallet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MyItemDragListener {
    private ItemTouchHelper myTouchHelper;

    TabLayout tabLayout;
    RecyclerView recList;
    CardAdapter ca;
    int resourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        resourceId=R.raw.payment_cards;
        ca = new CardAdapter(createList(),this);
        recList.setAdapter(ca);

        ItemTouchHelper.Callback cb = new MyItemTouchHelperCallback(ca);
        myTouchHelper = new ItemTouchHelper(cb);
        myTouchHelper.attachToRecyclerView(recList);

        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the cards accordingly
                switch (tab.getPosition()) {
                    case 0:
                        resourceId=R.raw.payment_cards;
                        ca.updateList(createList());
                        recList.setAdapter(ca);
                        break;
                    case 1:
                        resourceId=R.raw.gift_cards;
                        ca.updateList(createList());
                        recList.setAdapter(ca);
                        break;
                    case 2:
                        resourceId=R.raw.loyalty_cards;
                        ca.updateList(createList());
                        recList.setAdapter(ca);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_exit) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pay) {

        } else if (id == R.id.nav_bank) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private List<CardInfo> createList() {

        List<CardInfo> result = new ArrayList<>();
        result.clear();

        InputStream inp = this.getResources().openRawResource(resourceId);
        BufferedReader br = new BufferedReader(new InputStreamReader(inp));
        String readLine = null;

        try {
            // While the BufferedReader readLine is not null
            while ((readLine = br.readLine()) != null) {
                String[] str=readLine.split("\\|");
                CardInfo ci = new CardInfo();
                ci.label = str[0];
                ci.balance = CardInfo.BALANCE_PREFIX + str[1];
                ci.available_credit = CardInfo.AVAILABLE_CREDIT_PREFIX + str[2];
                ci.card_num = str[3];
                result.add(ci);
            }
            // Close the InputStream and BufferedReader
            inp.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    @Override
    public void onDragStart(RecyclerView.ViewHolder viewHolder) {
        myTouchHelper.startDrag(viewHolder);
    }
}
