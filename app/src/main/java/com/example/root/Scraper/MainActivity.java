package com.example.root.Scraper;



import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // private TextView textviewName;
    private SharedPreferences preferences;
    private SharedPreferences booksPref;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Toolbar  toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println(refreshedToken);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle) changed to addDrawerListener
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_home);
        //TextView textviewName = (TextView)findViewById(R.id.textviewNav);

        preferences = getSharedPreferences("ID_PREF", Context.MODE_PRIVATE);
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView textviewName = (TextView) headerView.findViewById(R.id.textviewNav);
        if (preferences.contains("MemberID")) {
            String member_name = preferences.getString("MemberName", "");
            if (member_name.equals("")) {
                textviewName.setText("Hello scraper");
            } else {
                textviewName.setText(member_name);
            }
        }
        not();
    }

    private void s()
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        preferences = getSharedPreferences("ID_PREF", Context.MODE_PRIVATE);
        View headerView = navigationView.getHeaderView(0);
        TextView textviewName = (TextView) headerView.findViewById(R.id.textviewNav);
        if (preferences.contains("MemberID")) {
            String member_name = preferences.getString("MemberName", "");
            if (member_name.equals("")) {
                textviewName.setText("Hello scraper");
            } else {
                textviewName.setText(member_name);
            }
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

        return super.onOptionsItemSelected(item);
    }
    private void not()
    {

        booksPref = getSharedPreferences("BOOK_PREF", Context.MODE_PRIVATE);
        String books;
        String access;
        String data;
        String due;
        String member_name;
        Calendar c = Calendar.getInstance();///calendar curdate
        int curmonth = c.get(Calendar.MONTH) + 1;
        int curday = c.get(Calendar.DAY_OF_MONTH);
        int curyear = c.get(Calendar.YEAR);

        int item;
        int i = 1;


        if (booksPref.contains("MemberID")) {
            item = booksPref.getInt("MemberItem", 0);
            member_name = booksPref.getString("MemberName", "");

            for (i = 1; i <= item; i++) {
                books = booksPref.getString("Book" + i, "");
                access = booksPref.getString("Access" + i, "");
                data = booksPref.getString("Due" + i, "");
                String[] info = data.split(" ");
                String date = info[0];
                String[] mandd = date.split("/");
                int month = Integer.parseInt(mandd[0]);
                int day = Integer.parseInt(mandd[1]);
                int year = Integer.parseInt(mandd[2]);
                due = month + "/" + day;
                if (year >= curyear) {
                    if (month < curmonth) {
                        counter++;
                    }
                    if ((month == curmonth) && (day > curday)) {
                        counter++;

                    }
                    if ((month == curmonth) && (day <= curday)) {
                        //counter++;
                    }
                    if (month > curmonth) {
                        //counter++;
                    }

                } else {
                }
            }
        }
     //   NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
       // if(counter!=0) {
         //   long[] V = {500, 1000};
           // builder.setVibrate(V);
            //builder.setContentText("You have "+counter+" overdue");
            //builder.setSmallIcon(R.drawable.ic_menu_gallery);
       // }




//        Intent intent=new Intent(MainActivity.this,Home.class);
//        TaskStackBuilder stackBuilder=TaskStackBuilder.create(MainActivity.this);
  //      stackBuilder.addParentStack(Home.class);
   //     stackBuilder.addNextIntent(intent);
     //   PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT);
      //  builder.setContentIntent(pendingIntent);
     //   NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    //    nm.notify(0,builder.build());

    }
    private void displaySelectedScreen(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_home:
                fragment = new Home();
                break;
            case R.id.nav_save:
                fragment = new Save();
                break;
            case R.id.nav_share:
                fragment = new Share();
                break;
            case R.id.nav_aboutus:
                fragment = new About();
                break;
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentlayout, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }
    private void s(String member_name) {

    }


}
