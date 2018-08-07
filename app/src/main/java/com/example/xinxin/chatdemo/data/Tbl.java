package com.example.xinxin.chatdemo.data;

import android.text.TextUtils;

import com.example.xinxin.chatdemo.tool.FileTool;
import com.example.xinxin.chatdemo.tool.Tool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Tbl {
    public String name;
    public Set set;
    public String setName;
    public ArrayList<Row> rows;
    public HashMap<String, Row> keyValues;
    private final String TAG = "Tbl";

    public Tbl() {
        name = "NewTbl";
        rows = new ArrayList<Row>();
        keyValues = new HashMap<String, Row>();
    }

    public Tbl(String tblName) {
        name = tblName;
        rows = new ArrayList<Row>();
        keyValues = new HashMap<String, Row>();
    }

    public void addRow(Row row) {
        if (row == null) return;
//        Log.i(TAG, "addRow rowStr  " + row.rowStr);
        int indexS = row.rowStr.indexOf(Separator.semicolon);
        if (indexS != -1) row.rowStr = row.rowStr.substring(0, indexS);

        if (!row.rowStr.endsWith(Separator.Comma)) {
            row.rowStr = row.rowStr + Separator.Comma;
        }
        if (TextUtils.isEmpty(row.getVal("rowid1"))) {
            String rowid1Tep = System.currentTimeMillis() + "";
            if (keyValues.containsKey(rowid1Tep)) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    rowid1Tep = System.currentTimeMillis() + "";
                }
            }
            row.setVal("rowid1", rowid1Tep);
        }

        String key = row.getVal("rowid1");
        if (!keyValues.containsKey(key)) {
            row.tbl = this;
            row.tblName = this.name;
            row.setName = this.setName;
            rows.add(row);
            keyValues.put(key, row);
        } else {
            keyValues.get(key).rowStr = row.rowStr;
        }

    }

    public void addRows(ArrayList<Row> rows) {
        if (rows == null) return;
        for (Row row : rows) {
            addRow(row);
        }

    }

    public void delRows(ArrayList<Row> rows) {
        if (rows == null) return;
        for (Row row : rows) {
            row.del();
        }
    }

    // 修改了 filePath 和 readTextForFile方法
    public void load() {
        String getStr = "";
        String filePath = set.dataPath + "/rows/" + this.setName + "/" + this.name + ".dat";
        Tool.showLog(TAG, "filePath " + filePath); // data/data/com.example.netvmeet/Data/rows/BASE/epuserlist.txt
        File file = new File(filePath);
        if (!file.exists()) return;
        getStr = FileTool.readTextForFile(file);
        Tool.showLog(TAG, "getStr " + getStr);
        if (getStr.contains(Separator.at_Str)) {
            int atIndex = getStr.lastIndexOf(Separator.at_Str);
            if (getStr.length() > atIndex + 1) {
                getStr = getStr.substring(atIndex + 1);
            } else {
                getStr = "";
            }
        }
        String[] rowStrs = getStr.split(Separator.semicolon);
        this.rows.clear();
        this.keyValues.clear();
        for (String rowStr : rowStrs) {
            if (TextUtils.isEmpty(rowStr)) continue;
            if (TextUtils.isEmpty(rowStr.trim())) continue;
            Row row = new Row(rowStr);
            String rowid1 = row.getVal("rowid1");
            if (TextUtils.isEmpty(rowid1)) continue;
            row.tbl = this;
            row.tblName = this.name;
            row.setName = this.setName;

            Row rowPre = keyValues.get(rowid1);
            if (rowPre == null) {
                rows.add(row);
                keyValues.put(rowid1, row);
            } else {
                rows.remove(rowPre);
                keyValues.remove(rowPre);
                rows.add(row);
                keyValues.put(rowid1, row);
            }
            this.addRow(row);
        }
    }
    public void load1() {
        String getStr = "";
        String filePath = set.dataPath + "/rows/" + this.setName + "/" + this.name + ".dat";
        Tool.showLog(TAG, "filePath " + filePath); // data/data/com.example.netvmeet/Data/rows/BASE/epuserlist.txt
        File file = new File(filePath);
        if (!file.exists()) return;
        getStr = FileTool.readTextForFile(file);
        Tool.showLog(TAG, "getStr " + getStr);
        if (getStr.contains(Separator.at_Str)) {
            int atIndex = getStr.lastIndexOf(Separator.at_Str);
            if (getStr.length() > atIndex + 1) {
                getStr = getStr.substring(atIndex + 1);
            } else {
                getStr = "";
            }
        }
        String[] rowStrs = getStr.split(Separator.semicolon);
        this.rows.clear();
        this.keyValues.clear();
        for (String rowStr : rowStrs) {
            if (TextUtils.isEmpty(rowStr)) continue;
            if (TextUtils.isEmpty(rowStr.trim())) continue;
            Row row = new Row(rowStr);
            this.addRow(row);
        }
    }

    /**
     * 保存表:修改了 filePath 和 writeTextToFile方法
     */
    public void save() {
        String filePath = set.dataPath + "/rows/" + this.setName + "/" + this.name + ".dat";
        Tool.showLog(TAG, "save filePath " + filePath);
        FileTool.createFile(filePath);
        File file = new File(filePath);

        StringBuilder stringBuilder = new StringBuilder();
        for (Row row : this.rows) {
            stringBuilder.append(row.rowStr);
            stringBuilder.append(Separator.semicolon);
        }
        Tool.showLog(TAG, "save  stringBuilder.toString() " + stringBuilder.toString());
        FileTool.writeTextToFile(file, stringBuilder.toString(), false);
    }

    /**
     * 根据传入的条件进行查询，且关系
     *
     * @param strs 数组参数
     * @param like 是否模糊查询
     * @return
     */
    public ArrayList<Row> search(String[] strs, Boolean like) {
        ArrayList<Row> rows1 = new ArrayList<Row>();
        for (Row row : rows) {
            boolean ok = true;
            for (String str : strs) {
                if (str.equals("")) continue;
                if (!str.contains(Separator.Sign)) continue;
                if (str.split(Separator.Sign).length < 2) continue;
                String colString = str.split(Separator.Sign)[0];
                String valString = str.split(Separator.Sign)[1];
                if (!row.rowStr.contains(colString + Separator.Sign)) {
                    ok = false;
                    break;
                }
                if (like) {
                    if (!row.getVal(colString).contains(valString)) {
                        ok = false;
                        break;
                    }
                } else {
                    if (!row.rowStr.contains(str)) {
                        ok = false;
                        break;
                    }
                }
            }
            if (rows1.contains(row)) continue;
            if (ok) rows1.add(row);
        }
        return rows1;
    }


    public ArrayList<Row> search(String key, String value) {
        ArrayList<Row> rows1 = new ArrayList<Row>();
        for (Row row : rows) {
            if (row.getVal(key).equals(value)) {
                rows1.add(row);
            }
        }
        return rows1;
    }
    /**
     * 根据传入的条件进行查询，且关系
     *
     * @param strs 数组参数
     * @param like 是否模糊查询
     * @return
     */
    public ArrayList<Row> search(String[] strs, ArrayList<Row> rows, Boolean like) {
        ArrayList<Row> rows1 = new ArrayList<Row>();
        for (Row row : rows) {
            boolean ok = true;
            for (String str : strs) {
                if (str.equals("")) continue;
                if (!str.contains(Separator.Sign)) continue;
                if (str.split(Separator.Sign).length < 2) continue;
                String colString = str.split(Separator.Sign)[0];
                String valString = str.split(Separator.Sign)[1];
                if (!row.rowStr.contains(colString + Separator.Sign)) {
                    ok = false;
                    break;
                }
                if (like) {
                    if (!row.getVal(colString).contains(valString)) {
                        ok = false;
                        break;
                    }
                } else {
                    if (!row.rowStr.contains(str)) {
                        ok = false;
                        break;
                    }
                }
            }
            if (rows1.contains(row)) continue;
            if (ok) rows1.add(row);
        }
        return rows1;
    }

    /**
     * 根据传入的条件进行查询，且关系
     *
     * @param strs 查询条件
     * @param rows 要查询的row的集合
     * @param like 是否模糊查询
     * @return
     */
    public ArrayList<Row> search(ArrayList<String> strs, ArrayList<Row> rows, Boolean like) {
        ArrayList<Row> rows1 = new ArrayList<Row>();
        for (Row row : rows) {
            boolean ok = true;
            for (String str : strs) {
                if (str.equals("")) continue;
                if (!str.contains(Separator.Sign)) continue;
                if (str.split(Separator.Sign).length < 2) continue;
                String colString = str.split(Separator.Sign)[0];
                String valString = str.split(Separator.Sign)[1];
                if (!row.rowStr.contains(colString + Separator.Sign)) {
                    ok = false;
                    break;
                }
                if (like) {
                    if (!row.getVal(colString).contains(valString)) {
                        ok = false;
                        break;
                    }
                } else {
                    if (!row.rowStr.contains(str)) {
                        ok = false;
                        break;
                    }
                }
            }
            if (rows1.contains(row)) continue;
            if (ok) rows1.add(row);
        }
        return rows1;
    }

    /**
     * 根据传入的条件进行查询，或关系
     *
     * @param strs 查询条件
     * @param rows 要查询的row的集合
     * @return
     */
    public ArrayList<Row> search(String[] strs, ArrayList<Row> rows) {
        ArrayList<Row> rows1 = new ArrayList<Row>();
        for (Row row : rows) {
            boolean ok = false;
            for (String str : strs) {
                if (str.equals("")) continue;
                if (!str.contains(Separator.Sign)) continue;
                String colString = str.split(Separator.Sign)[0];
                String valString = str.split(Separator.Sign)[1];
                if (!row.rowStr.contains(colString + Separator.Sign)) {
                    ok = false;
                    continue;
                }
                if (row.getVal(colString).contains(valString)) {
                    ok = true;
                    break;
                }
            }
            if (rows1.contains(row)) continue;
            if (ok) rows1.add(row);
        }
        return rows1;
    }
    public void mergeRow(String rowid1, String RowsStr1, String Mac) {
        String[] strRowid1 = rowid1.split(Separator.Sign);
        rowid1 = strRowid1[strRowid1.length - 1];
        if (!this.keyValues.containsKey(rowid1)) return;
        Row row = this.keyValues.get(rowid1);
        row.rowStr = row.rowStr.replace(Mac + Separator.underline, Mac + "_" + RowsStr1 + Separator.underline);
        save();
    }
}
