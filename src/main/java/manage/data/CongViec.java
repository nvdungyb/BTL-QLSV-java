package manage.data;

public class CongViec {
    private String moTa, trangThai, ngayThang;

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

    public String toString(){
        return moTa + " " + trangThai + " " + ngayThang;
    }
}
