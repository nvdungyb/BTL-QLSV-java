package manage.data;

import java.util.HashMap;

public class HashMapStudent {
    private static HashMap<String, SinhVien> hashSinhVien = new HashMap<>();

    public static HashMap<String, SinhVien> getHashSinhVien() {
        return hashSinhVien;
    }

    public void addStudent(String maSinhVien, SinhVien sinhVien) {
        hashSinhVien.put(maSinhVien, sinhVien);
    }
}
