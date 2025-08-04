package com.example.aps_true.ui.query.main;

public class QueryItem {
    private String storeName;
    private String date;
    private String time;
    private int point;

    public QueryItem(String storeName, String date, String time, int point) {
        this.storeName = storeName;
        this.date = date;
        this.time = time;
        this.point = point;
    }

    public String getStoreName() { return storeName; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getPoint() { return point; }
}
