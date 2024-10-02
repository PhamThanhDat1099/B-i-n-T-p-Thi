package model;

import java.util.Objects;

public final class SinhVien {
    private String maso;
    private String hoten;
    private boolean gioitinh;
    private double diemTB;

    // Constructor mặc định
    public SinhVien() {
    }

    // Constructor chỉ có mã số
    public SinhVien(String maso) {
        this.maso = maso;
    }

    // Constructor đầy đủ thông tin
    public SinhVien(String maso, String hoten, boolean gioitinh, double diemTB) {
        this.maso = maso;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        setDiemTB(diemTB);  // Sử dụng setDiemTB để kiểm tra hợp lệ
    }

    // Constructor sao chép
    public SinhVien(SinhVien sv) {
        this.maso = sv.maso;
        this.hoten = sv.hoten;
        this.gioitinh = sv.gioitinh;
        this.diemTB = sv.diemTB;
    }

    // Getter và Setter
    public String getMaso() {
        return maso;
    }

    public void setMaso(String maso) {
        this.maso = maso;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public double getDiemTB() {
        return diemTB;
    }

    // Đảm bảo điểm TB hợp lệ
    public void setDiemTB(double diemTB) {
        if (diemTB < 0 || diemTB > 10) {
            throw new IllegalArgumentException("Điểm trung bình phải nằm trong khoảng từ 0 đến 10");
        }
        this.diemTB = diemTB;
    }

    // Phương thức để lấy giới tính dạng chuỗi
    public String getGioitinhAsString() {
        return gioitinh ? "Nam" : "Nữ";
    }

    // Phương thức tính học lực
    public String getHocLuc() {
        String kq = "";
        if (diemTB < 5) {
            kq = "Yếu";
        } else if (diemTB < 6.5) {
            kq = "Trung bình";
        } else if (diemTB < 7.5) {
            kq = "Khá";
        } else if (diemTB < 9) {
            kq = "Giỏi";
        } else {
            kq = "Xuất sắc";
        }
        return kq;
    }

    // Phương thức equals kiểm tra đối tượng có bằng nhau không (dựa trên mã số)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SinhVien other = (SinhVien) obj;
        return Objects.equals(this.maso, other.maso);
    }

    // Phương thức toString để hiển thị thông tin sinh viên
    @Override
    public String toString() {
        return "SinhVien{" +
                "maso='" + maso + '\'' +
                ", hoten='" + hoten + '\'' +
                ", gioitinh=" + (gioitinh ? "Nam" : "Nữ") +
                ", diemTB=" + diemTB +
                ", hocLuc='" + getHocLuc() + '\'' +
                '}';
    }
}
