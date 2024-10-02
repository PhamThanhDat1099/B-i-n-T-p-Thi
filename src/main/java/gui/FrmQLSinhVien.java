package gui;

import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.QLSinhVien;
import model.SinhVien;

public class FrmQLSinhVien extends JFrame {

    private JTable tblSinhVien;
    private JButton btThem, btXoa;
    private JButton btDocFile, btGhiFile;
    private DefaultTableModel model;
    private JTextField txtMaSo, txtHoTen, txtDTB;
    private JRadioButton rdNam, rdNu;
    private JCheckBox chkSapXep;
    private static final String FILE_NAME = "data.txt";
    private final QLSinhVien qlsv = new QLSinhVien();

    public FrmQLSinhVien(String title) {
        super(title);
        createGUI();
        processEvent();
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createGUI() {
        // Tạo JTable với tiêu đề cột
        String[] columnNames = {"Mã số", "Họ tên", "Phái", "ĐTB", "Xếp loại"};
        model = new DefaultTableModel(null, columnNames);
        tblSinhVien = new JTable(model);
        JScrollPane scrollTable = new JScrollPane(tblSinhVien);

        // Tạo các điều khiển nhập liệu và nút lệnh
        JPanel p = new JPanel();
        p.add(new JLabel("Mã sinh viên"));
        p.add(txtMaSo = new JTextField(5));
        p.add(new JLabel("Họ tên"));
        p.add(txtHoTen = new JTextField(10));

        p.add(rdNam = new JRadioButton("Nam"));
        p.add(rdNu = new JRadioButton("Nữ"));
        rdNam.setSelected(true);
        ButtonGroup btgPhai = new ButtonGroup();
        btgPhai.add(rdNam);
        btgPhai.add(rdNu);

        p.add(new JLabel("Điểm TB"));
        p.add(txtDTB = new JTextField(10));

        p.add(btDocFile = new JButton("Đọc File"));
        p.add(btThem = new JButton("Thêm"));
        p.add(btXoa = new JButton("Xoá"));
        p.add(btGhiFile = new JButton("Ghi File"));

        JPanel p2 = new JPanel();
        p2.add(chkSapXep = new JCheckBox("Sắp xếp theo học lực"));

        // Add các thành phần vào cửa sổ
        add(p, BorderLayout.NORTH);
        add(scrollTable, BorderLayout.CENTER);
        add(p2, BorderLayout.SOUTH);
    }

    private void processEvent() {
        // Đọc file
        btDocFile.addActionListener((e) -> {
            qlsv.DocDanhSachSinhVien(FILE_NAME);
            loadDataToJTable();
        });

        // Thêm sinh viên
        btThem.addActionListener((e) -> {
            String error = validateInput();
            if (!error.isEmpty()) {
                JOptionPane.showMessageDialog(this, error, "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String maso = txtMaSo.getText();
            String hoten = txtHoTen.getText();
            boolean gioitinh = rdNam.isSelected();
            double diemtb = Double.parseDouble(txtDTB.getText());

            SinhVien sv = new SinhVien(maso, hoten, gioitinh, diemtb);
            if (qlsv.themSV(sv)) {
                loadDataToJTable();
                JOptionPane.showMessageDialog(this, "Đã thêm sinh viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thao tác thêm thất bại\nDo trùng mã sinh viên", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Xóa sinh viên
        btXoa.addActionListener((e) -> {
            int selectedRowIndex = tblSinhVien.getSelectedRow();
            if (selectedRowIndex < 0) {
                JOptionPane.showMessageDialog(this, "Bạn chưa chọn sinh viên cần xoá", "Thông báo", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String maso = tblSinhVien.getValueAt(selectedRowIndex, 0).toString();
            if (qlsv.xoaSV(maso)) {
                loadDataToJTable();
                JOptionPane.showMessageDialog(this, "Đã xoá sinh viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thao tác xoá thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ghi file
        btGhiFile.addActionListener((e) -> {
            if (qlsv.GhiDanhSachSinhVien(FILE_NAME)) {
                JOptionPane.showMessageDialog(this, "Đã ghi dữ liệu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Ghi dữ liệu thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sắp xếp sinh viên theo học lực
        chkSapXep.addItemListener((e) -> {
            if (chkSapXep.isSelected()) {
                qlsv.sapXepTheoHocLuc();
                loadDataToJTable();
            }
        });
    }

    // Kiểm tra tính hợp lệ của dữ liệu đầu vào
    private String validateInput() {
        StringBuilder error = new StringBuilder();
        if (txtMaSo.getText().isEmpty()) {
            error.append("Chưa nhập mã sinh viên\n");
        }
        if (txtHoTen.getText().isEmpty()) {
            error.append("Chưa nhập họ tên sinh viên\n");
        }
        try {
            Double.valueOf(txtDTB.getText());
        } catch (NumberFormatException ex) {
            error.append("Điểm trung bình phải là số\n");
        }
        return error.toString();
    }

    // Load dữ liệu từ danh sách sinh viên lên JTable
    private void loadDataToJTable() {
        model.setRowCount(0);
        for (SinhVien sv : qlsv.getDsSinhVien()) {
            model.addRow(new Object[]{
                sv.getMaso(),
                sv.getHoten(),
                sv.isGioitinh() ? "Nam" : "Nữ",
                sv.getDiemTB(),
                sv.getHocLuc()
            });
        }
    }
}
