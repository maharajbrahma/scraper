package com.example.root.Scraper;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by root on 14/4/17.
 */


public class Home extends Fragment {
    private RecyclerView recyclerView;


    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;
    private Context context;
    private ListItem listItem;
    private TextView textviewName;
    private TextView textviewName1;
    private TextView book;
    private ProgressBar progressBar;
    private CardView cardView;
    private CardView cardViewSave;
    String member_item = "";
    int member_item1=0;
    String member_name = "";
    String name = "";
    int item = 0;
    private int SNACKBAR_TIME=2000;

    String m = "";
    private SharedPreferences preferences;
    private SharedPreferences booksPref;
    AdView adViewHome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        cardView = (CardView) view.findViewById(R.id.cardview);
        cardViewSave = (CardView) view.findViewById(R.id.cardviewSave);
        cardView.setVisibility(View.INVISIBLE);
        cardViewSave.setVisibility(View.INVISIBLE);
        preferences = getActivity().getSharedPreferences("ID_PREF", Context.MODE_PRIVATE);
        booksPref = getActivity().getSharedPreferences("BOOK_PREF", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        listItems = new ArrayList<>();
        recyclerView.setAdapter(new MyAdapter(listItems, context));
        book = (TextView) view.findViewById(R.id.textview_book);

        if (isNetworkAvailable() == true) {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT);
            snackbar.setDuration(SNACKBAR_TIME);
            snackbar.setText("You are online");
            snackbar.show();
            if (preferences.contains("MemberID")) {
                m = preferences.getString("MemberID", "");
                new DownloadTask().execute(m);

            }

            else {
                cardViewSave.setVisibility(View.VISIBLE);
                final EditText member_id = (EditText) view.findViewById(R.id.editText_ID);
                member_id.setText(preferences.getString("MemberID", ""));
                Button save = (Button) view.findViewById(R.id.button_SAVE);

                save.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        m = member_id.getText().toString();
                        editor.putString("MemberID", m);
                        editor.commit();
                        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(member_id.getWindowToken(), 0);
                        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT);
                        snackbar.setDuration(SNACKBAR_TIME);
                        snackbar.setText("Saved");
                        snackbar.show();
                        new DownloadTask().execute(m);

                    }
                });


            }
        }
        else {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT);
            snackbar.setDuration(SNACKBAR_TIME);
            snackbar.setText("You are offline");
            snackbar.show();
            member_name = booksPref.getString("MemberName", "");
            new ShowBook().execute("");


        }


        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");

    }

    private void scrape(String id) {
        listItems.clear();


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Document doc = Jsoup.connect("http://webopac.cit.ac.in/memberstatus.aspx")
                        .data("__VIEWSTATE", "/wEPDwUKLTM1NzczNTkxNw9kFgJmD2QWAgIBD2QWAgIDD2QWJAIDDw8WAh4HVmlzaWJsZWhkZAIFDw8WAh8AaGRkAgcPDxYCHwBoZGQCCQ8PFgIfAGhkZAILDw8WAh8AaGRkAg0PDxYCHwBoZGQCDw8PFgIfAGhkZAIRDw8WAh8AaGRkAhMPDxYCHwBoZGQCFQ8PFgIfAGhkZAIXDw8WAh8AaGRkAhkPDxYCHwBoZGQCGw8PFgIfAGhkZAIdDzwrAAsAZAIfDw8WAh8AaGRkAiEPPCsACwBkAiMPDxYCHwBoZGQCJQ88KwALAGRkMSxw6dKF1XbXMbOcFTso6csxYAw=")
                        .data("__EVENTVALIDATION", "/wEWAwKO1aEbAvTZiLYOAtaW9bIDiZAxtbxnxVjqL9fHHYjbRBJBA14=")
                        .data("ctl00$CPHmaster$txtMemcd", id)
                        .data("ctl00$CPHmaster$btnsearch", "Search Member")
                        .post();
                Element member = doc.getElementById("ctl00_CPHmaster_lblmemname");
                member_name = member.text();
                if (member_name.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT);
                    snackbar.setDuration(SNACKBAR_TIME);
                    snackbar.setText("Enter correct member id");
                    snackbar.show();
                } else {
                    String member_code = doc.getElementById("ctl00_CPHmaster_lblmemcd").text();
                    String member_dept = doc.getElementById("ctl00_CPHmaster_lbldept").text();
                    String member_cat = doc.getElementById("ctl00_CPHmaster_lblcat").text();
                    String member_due = doc.getElementById("ctl00_CPHmaster_lbldue").text();
                    member_item = doc.getElementById("ctl00_CPHmaster_lblissued").text();

                    if (member_item.isEmpty()) {

                        member_item = "0";

                        member_item1 = 0;
                        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT);
                        snackbar.setDuration(SNACKBAR_TIME);
                        snackbar.setText("You have no books");
                        snackbar.show();
                        book.setText("You've 0 books in your card");
                    }
                    member_item1 = Integer.parseInt(member_item);
                    final SharedPreferences.Editor editor = preferences.edit();
                    final SharedPreferences.Editor bookEdit = booksPref.edit();
                    editor.putString("MemberName", member_name);
                    editor.putString("MemberDept", member_dept);
                    editor.putString("MemberCategory", member_cat);
                    //editor.putString("MemberItem", member_item);
                    bookEdit.putInt("MemberItem", member_item1);
                    bookEdit.putString("MemberName", member_name);
                    bookEdit.putString("MemberID", member_code);

                    Elements table = doc.getElementsByAttributeValue("id", "ctl00_CPHmaster_DgIssued");
                    Elements rows = table.select("tr");
                    int i = 1;
                    for (Element row : table.select("tr:gt(0)")) {
                        Elements col = row.select("td");
                        String books = col.get(0).text();
                        bookEdit.putString("Book" + i, books);
                        String access = col.get(1).text();
                        bookEdit.putString("Access" + i, access);
                        String data = col.get(3).text();
                        bookEdit.putString("Due" + i, data);
                        String[] info = data.split(" ");
                        String date = info[0];
                        String[] mandd = date.split("/");
                        String month = mandd[0];
                        String day = mandd[1];
                        String due = month + "/" + day;
                        switch (access) {
                            case "13728":
                                books = "Numerical Methods In Engineering & Science With Programs In C,C++";
                                break;
                            case "13727":
                                books = "Numerical Methods In Engineering & Science With Programs In C,C++";
                                break;
                        }
                        listItem = new ListItem(books, access, date);
                        listItems.add(listItem);
                        i = i + 1;
                    }
                    editor.commit();
                    bookEdit.commit();
                }


            } catch (IOException ex) {
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT);
                snackbar.setDuration(SNACKBAR_TIME);
                snackbar.setText("Could not fetch");
                snackbar.show();
            }
        }
    }

    private void show() {
        String books;
        String access;
        String data;
        String due;
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
                String month = mandd[0];
                String day = mandd[1];
                due = month + "/" + day;
                if (access.equals("13738")) {
                    books = "Numerical Methods In Engineering & Science With Programs In C,C++";
                }
                listItem = new ListItem(books, access, date);
                listItems.add(listItem);
            }
        }

    }

    class DownloadTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            scrape(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            textviewName = (TextView) getActivity().findViewById(R.id.textviewNav);
            context = getActivity();
            adapter = new MyAdapter(listItems, context);
            recyclerView.setAdapter(adapter);
            cardView.setVisibility(View.VISIBLE);
            if (member_item.equals("")) {
                book.setText("");
            } else {
                if (member_item.equals("1")) {
                    book.setText("You've " + member_item + " book in your card");
                } else {
                    book.setText("You've " + member_item + " books in your card");
                }
            }
            if (member_name.equals("")) {
                textviewName.setText("Hello, scraper");
            } else {
                textviewName.setText(member_name);
            }


        }

        @Override
        protected void onPreExecute() {
            cardViewSave.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);


        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }

    class ShowBook extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            show();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);

            context = getActivity();
            adapter = new MyAdapter(listItems, context);
            recyclerView.setAdapter(adapter);
            cardView.setVisibility(View.VISIBLE);
            if (item == 0) {
                book.setText("");
            } else {
                if (item == 1) {
                    book.setText("You've " + item + " book in your card");
                } else {
                    book.setText("You've " + item + " books in your card");
                }
            }

        }

        @Override
        protected void onPreExecute() {
            cardViewSave.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);


        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}