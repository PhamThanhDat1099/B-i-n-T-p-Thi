package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import util.FileHelper;

public class QLSinhVien {

    private ArrayList<SinhVien> dsSinhVien;

    public QLSinhVien() {
        dsSinhVien = new ArrayList<>();
    }

    // Constructor với danh sách sinh viên đầu vào
    public QLSinhVien(ArrayList<SinhVien> dsSinhVien) {
        this.dsSinhVien = dsSinhVien;
    }

    // Getter và Setter cho danh sách sinh viên
    public ArrayList<SinhVien> getDsSinhVien() {
        return dsSinhVien;
    }

    public void setDsSinhVien(ArrayList<SinhVien> dsSinhVien) {
        this.dsSinhVien = dsSinhVien;
    }

    // Đọc danh sách sinh viên từ file
    public void DocDanhSachSinhVien(String filename) {
        ArrayList<String> data = FileHelper.readFileText(filename); 
        dsSinhVien.clear();  // Xóa danh sách cũ

        for (String item : data) {
            String[] arr = item.split(";"); // Tách dữ liệu theo dấu ;
            SinhVien sv = new SinhVien();   // Tạo đối tượng SinhVien mới
            sv.setMaso(arr[0]);
            sv.setHoten(arr[1]);
            sv.setGioitinh(Boolean.parseBoolean(arr[2]));
            sv.setDiemTB(Double.parseDouble(arr[3]));
            dsSinhVien.add(sv); // Thêm vào danh sách
        }
    }

    // Ghi danh sách sinh viên ra file
    public boolean GhiDanhSachSinhVien(String filename) {
        ArrayList<String> data = new ArrayList<>();
        for (SinhVien sv : dsSinhVien) {
            String info = sv.getMaso() + ";" + sv.getHoten() + ";" + sv.isGioitinh() + ";" + sv.getDiemTB();
            data.add(info);  // Chuỗi thông tin sinh viên
        }
        return FileHelper.writeFileText(filename, data);  // Ghi file
    }

    // Thêm sinh viên vào danh sách
    public boolean themSV(SinhVien sv) {
        // Kiểm tra 
        if (dsSinhVien.contains(sv)) {
            return false; 
        }
        dsSinhVien.add(sv); 
        return true;
    }

    // Xóa sinh viên khỏi danh sách dựa trên mã số
    public boolean xoaSV(String maso) {
        // Tạo đối tượng SinhVien mới với mã số cần xóa
        SinhVien sv = new SinhVien(maso);
        if (dsSinhVien.contains(sv)) {
            dsSinhVien.remove(sv); 
            return true;
        }
        return false;
    }

    // Sắp xếp danh sách sinh viên theo học lực (dựa trên điểm trung bình)
    public void sapXepTheoHocLuc() {
        Comparator<SinhVien> cmp = (sv1, sv2) -> Double.compare(sv1.getDiemTB(), sv2.getDiemTB());
        Collections.sort(dsSinhVien, cmp); // Sắp xếp theo điểm trung bình
    }

    // In danh sách sinh viên 
    public void inDanhSach() {
        for (SinhVien sv : dsSinhVien) {
            System.out.println(sv);  
        }
    }
}
