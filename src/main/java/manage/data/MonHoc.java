package manage.data;

import javafx.beans.property.SimpleBooleanProperty;

public class MonHoc {
    private int tinChi;
    private String tenMon, maMon;
    private SimpleBooleanProperty checkBox = new SimpleBooleanProperty(false);

    public MonHoc(String tenMon, String maMon, int soTinChi) {
        this.tenMon = tenMon;
        this.maMon = maMon;
        this.tinChi = soTinChi;
    }

    public String getTenMon() {
        return tenMon;
    }

    public String getMaMon() {
        return maMon;
    }

    public int getTinChi() {
        return tinChi;
    }

    public SimpleBooleanProperty getCheckBox() {
        return checkBox;
    }

    public String toString() {
        return maMon + " " + tenMon + " " + tinChi;
    }

    public void setCheckBox(boolean status) {
        checkBox.set(status);
    }
}
