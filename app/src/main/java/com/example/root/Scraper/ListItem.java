package com.example.root.Scraper;

/**
 * Created by root on 23/4/17.
 */


public class ListItem {
    private String head;
    private String access;
    private String due;

    public ListItem(String head, String access, String due) {
        this.head = head;
        this.access = access;
        this.due = due;

    }

    public String getHead() {

        return head;
    }

    public String getAccess() {
        return access;
    }

    public String getDue() {
        return due;
    }


}