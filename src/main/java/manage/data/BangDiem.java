package manage.data;

public class BangDiem {
    private String maSv, tenSv, diemChu;
    private double diemChuyenCan, diemGiuaKy, diemCuoiKy, diemTongKet10, diemTongKet4;
    private int ky;

    public BangDiem(String maSv, String tenSv, double diemChuyenCan, double diemGiuaKy, double diemCuoiKy, double diemTongKet10, double diemTongKet4, String diemChu, int ky) {
        this.maSv = maSv;
        this.tenSv = tenSv;
        this.diemChuyenCan = diemChuyenCan;
        this.diemGiuaKy = diemGiuaKy;
        this.diemCuoiKy = diemCuoiKy;
        this.diemTongKet10 = diemTongKet10;
        this.diemTongKet4 = diemTongKet4;
        this.diemChu = diemChu;
        this.ky = ky;
    }

    public BangDiem(String maSv, String tenSv, double diemChuyenCan, double diemGiuaKy, double diemCuoiKy, double diemTongKet10, double diemTongKet4, String diemChu) {
        this.maSv = maSv;
        this.tenSv = tenSv;
        this.diemChuyenCan = diemChuyenCan;
        this.diemGiuaKy = diemGiuaKy;
        this.diemCuoiKy = diemCuoiKy;
        this.diemTongKet10 = diemTongKet10;
        this.diemTongKet4 = diemTongKet4;
        this.diemChu = diemChu;
    }

    public String getMaSv() {
        return maSv;
    }

    public String getTenSv() {
        return tenSv;
    }

    public double getDiemChuyenCan() {
        return diemChuyenCan;
    }

    public double getDiemGiuaKy() {
        return diemGiuaKy;
    }

    public double getDiemCuoiKy() {
        return diemCuoiKy;
    }

    public double getDiemTongKet10() {
        return diemTongKet10;
    }

    public double getDiemTongKet4() {
        return diemTongKet4;
    }

    public String getDiemChu() {
        return diemChu;
    }

    public int getKy() {
        return ky;
    }

    public String toString() {
        return maSv + " " + tenSv + " " + diemChuyenCan + " " + diemGiuaKy + " " + diemCuoiKy + " " + diemTongKet10 + " " + diemTongKet4 + " " + diemChu + " " + ky;
    }
}
