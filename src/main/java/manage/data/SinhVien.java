package manage.data;

import javafx.beans.property.SimpleBooleanProperty;

import java.time.LocalDate;

public class SinhVien {
    private String maSv, tenSv, ngaySinh, gioiTinh, email, sdt, diaChi, maLop;
    private final SimpleBooleanProperty checkBox;

    public SinhVien(String maSv, String tenSv, String ngaySinh, String gioiTinh, String email, String sdt, String diaChi, String maLop) {
        this.maSv = maSv;
        this.tenSv = tenSv;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.maLop = maLop;
        this.checkBox = new SimpleBooleanProperty(false);
    }

    public String getMaSv() {
        return maSv;
    }

    public String getTenSv() {
        return tenSv;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public String getSdt() {
        return sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getMaLop() {
        return maLop;
    }

    public SimpleBooleanProperty getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(Boolean selected) {
        this.checkBox.set(selected);
    }

    public String toString() {
        return maSv + " " + tenSv + " " + ngaySinh + " " + gioiTinh + " " + email + " " + sdt + " " + diaChi + " " + maLop;
    }

//    public ObservableValue<Boolean> checkBoxProperty() {
//        return checkBox;
//    }
}
