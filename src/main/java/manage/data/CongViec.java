package manage.data;

import javafx.beans.property.SimpleBooleanProperty;

public class CongViec {
    private String moTa, trangThai, ngayThang;
    private SimpleBooleanProperty check = new SimpleBooleanProperty(false);

    public CongViec(String moTa, String trangThai, String ngayThang) {
        this.moTa = moTa;
        this.trangThai = trangThai;
        this.ngayThang = ngayThang;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public SimpleBooleanProperty getCheck() {
        return check;
    }

    public void setChecked(boolean check) {
        this.check.set(!check);
    }

    public String toString() {
        return moTa + " " + trangThai + " " + ngayThang + " " + check.getValue();
    }
}
