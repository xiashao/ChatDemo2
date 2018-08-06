package com.example.xinxin.chatdemo.data;
import com.example.xinxin.chatdemo.tool.FileTool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Set {
    public static String dataPath;
    public String name;
    public List<Tbl> tbls;

    //approotPath = "data/data/" + packageStr + "/";
    public Set(String approotPath) {
        this.name = "NewSet";
        this.dataPath = approotPath + "Data";
    }

    public Set(String setName, String approotPath) {
        this.name = setName;
        this.tbls = new ArrayList<Tbl>();
        this.dataPath = approotPath + "Data";
    }

    /**
     * 增加 tbl
     */
    public void add(Tbl tbl) {
        tbls.add(tbl);
        tbl.set = this;
        tbl.setName = this.name;
    }

    /**
     * 根据 tbl名 返回对应的 tbl
     */
    public Tbl getTbl(String tblName) {
        for (Tbl tbl : this.tbls) {
            if (tbl.name.equals(tblName)) return tbl;
        }
        return null;
    }

    public void Load() {
        String setRowPath = dataPath + "/rows/" + this.name;
        File dir = new File(setRowPath);
        if (!dir.exists()) return;
        for (File file : dir.listFiles()) {
            if (file.getName().indexOf(".dat") <= 0) continue;
            String tblName = file.getName().replace(".dat", "");
            Tbl tbl1 = this.getTbl(tblName);
            if (tbl1 == null) {
                tbl1 = new Tbl(tblName);
                this.add(tbl1);
            }
            tbl1.load();
        }
    }

    public void Load2() {
        String setRowPath = dataPath + "/rows/" + this.name;
        File dir = new File(setRowPath);
        if (!dir.exists()) return;
        for (File file : dir.listFiles()) {
            if (file.getName().indexOf(".txt") <= 0) continue;
            String tblName = file.getName().replace(".txt", "");
            Tbl tbl1 = this.getTbl(tblName);
            if (tbl1 == null) {
                tbl1 = new Tbl(tblName);
                this.add(tbl1);
            }
            tbl1.load();
        }
    }

    public void save() {
        for (Tbl tbl : this.tbls) tbl.save();
    }

    // 包括本地 和内存
    public void clearAll() {
        for (Tbl tbl : this.tbls) {
            tbl.rows.clear();
            tbl.keyValues.clear();
        }
        String setRowPath = dataPath + "/rows/" + this.name;
        File setDir = new File(setRowPath);
        FileTool.deleteFile(setDir);
    }
}
