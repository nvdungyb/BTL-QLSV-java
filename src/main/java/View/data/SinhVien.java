package View.data;

public class SinhVien {
    private String maSv, tenSv, ngaySinh, gioiTinh, email, sdt, diaChi, maLop;
    public SinhVien(String maSv, String tenSv, String ngaySinh, String gioiTinh, String email, String sdt, String diaChi, String maLop){
        this.maSv = maSv;
        this.tenSv = tenSv;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.maLop = maLop;

    };

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

    public String toString(){
        return maSv + " " + tenSv + " " + ngaySinh + " " + gioiTinh + " " + email + " " + sdt + " " + diaChi + " " + maLop;
    }
}
