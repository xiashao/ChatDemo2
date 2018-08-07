package com.example.xinxin.chatdemo.data;

import android.text.TextUtils;

import java.util.ArrayList;

public class Row {
    public Tbl tbl;
    public String tblName;
    public String setName;
    public String rowStr = "";
    private final String TAG = "Row";

    public Row() {
    }

    public Row(String str) {
//        rowStr = str;
//        Log.i(TAG, "Row rowStr  " + str);
        rowStr = dealRowStr(str);
//        Log.i(TAG, "Row rowStr2  " + rowStr);
    }

    private String dealRowStr(String rowStr) {
        if (TextUtils.isEmpty(rowStr)) return rowStr;
        String temp = rowStr;
        if (temp.endsWith(Separator.semicolon)) {
            temp = temp.replace(Separator.semicolon, "");
        }
        if (!temp.endsWith(Separator.Comma)) {
            temp = temp + Separator.Comma;
        }
        return temp;
    }

    public String getVal(String colName) {
        int i1 = rowStr.indexOf(colName + Separator.Sign);
        if (i1 == -1)
            return "";
        //  int i2 = rowStr.indexOf(Separator.Comma, i1) == -1 ? rowStr.length() : rowStr.indexOf(Separator.Comma, i1);
        int i2 = -1;
        if (rowStr.indexOf(Separator.Comma, i1) != -1) {
            i2 = rowStr.indexOf(Separator.Comma, i1);
        } else if (rowStr.indexOf(Separator.semicolon, i1) != -1) {
            i2 = rowStr.indexOf(Separator.semicolon, i1);
        } else {
            i2 = rowStr.length();
        }
        String s = rowStr.substring(i1, i2).replace(colName + Separator.Sign, "");

        return s;
    }

    public void setVal(String colName, String value) {
        int i1 = rowStr.indexOf(colName + Separator.Sign);
        if (i1 == -1) {
            if (value != null) {
                rowStr = dealRowStr(rowStr);
                rowStr += colName + Separator.Sign + value + Separator.Comma;
            }
        } else {
            int i2 = rowStr.indexOf(Separator.Comma, i1) == -1 ? rowStr.length() : rowStr.indexOf(Separator.Comma, i1);
            String s = rowStr.substring(i1, i2);
            String str = "";
            if (value != null) str = colName + Separator.Sign + value;
            rowStr = rowStr.replace(s, str);
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Row)) {
            return false;
        } else if (obj == this) {
            return true;
        } else
            return this.getVal("rowid1").equals(((Row) obj).getVal("rowid1"));
    }


    @Override
    public String toString() {
        return this.setName + Separator.at_Str + this.tblName + Separator.at_Str + this.rowStr;
    }

    public void del() {
        this.tbl.keyValues.remove(this.getVal("rowid1"));
        this.tbl.rows.remove(this);
    }

    public ArrayList<Row> getRows(String rowStrs) {
        String[] perRowStr = rowStrs.split(Separator.semicolon);
        ArrayList<Row> rows = new ArrayList<Row>();
        for (String str : perRowStr) {
            Row row = new Row(str);
            row.rowStr = str;
            rows.add(row);
        }
        return rows;
    }
}
