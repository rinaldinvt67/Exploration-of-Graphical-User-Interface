package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelPesanan extends JPanel implements PesananListener {
    private DefaultListModel<ItemMenu> model = new DefaultListModel<>();
    private JList<ItemMenu> daftar = new JList<>(model);
    private JLabel labelTotal = new JLabel("Total: Rp 0");

    public PanelPesanan() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("Pesanan"));
        setPreferredSize(new Dimension(250, 0));

        labelTotal.setFont(new Font("SansSerif", Font.BOLD, 16));

        JButton tombolBayar = new JButton("Bayar");
        JButton tombolReset = new JButton("Reset");
        tombolBayar.addActionListener(e -> bayar());
        tombolReset.addActionListener(e -> reset());

        JPanel panelTombol = new JPanel(new GridLayout(1, 2, 5, 5));
        panelTombol.add(tombolBayar);
        panelTombol.add(tombolReset);

        JPanel bawah = new JPanel(new GridLayout(2, 1, 5, 5));
        bawah.add(labelTotal);
        bawah.add(panelTombol);

        add(new JScrollPane(daftar), BorderLayout.CENTER);
        add(bawah, BorderLayout.SOUTH);

        pasangPopupHapus();
    }

    // Klik kanan pada item muncul menu "Hapus item"
    private void pasangPopupHapus() {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem menuHapus = new JMenuItem("Hapus item");
        menuHapus.addActionListener(e -> {
            int idx = daftar.getSelectedIndex();
            if(idx >= 0) {
                model.remove(idx);
                perbaruiTotal();
            }    
        });
        popup.add(menuHapus);

        daftar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { tampilkanPopup(e); }

            @Override
            public void mouseReleased(MouseEvent e) { tampilkanPopup(e); }

            private void tampilkanPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int idx = daftar.locationToIndex(e.getPoint());
                    if (idx >= 0) {
                        daftar.setSelectedIndex(idx);
                        popup.show(daftar, e.getX(), e.getY());
                    }
                }
            }
        });
    }

    @Override
    public void onMenuDipilih(ItemMenu item) {
        model.addElement(item);
        perbaruiTotal();
    }

    private int hitungTotal() {
        int total = 0;
        for (int i = 0; i < model.size(); i++) {
            total += model.get(i).getHarga();
        }
        return total;
    }

    private void perbaruiTotal() {
        labelTotal.setText("Total: " + ItemMenu.formatRupiah(hitungTotal()));
    }

    private void bayar() {
        if (model.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Belum ada pesanan.");
            return;
        }
        JOptionPane.showMessageDialog(this,
                "Total pembayaran: " + ItemMenu.formatRupiah(hitungTotal()));
        reset();
    }

    private void reset() {
        model.clear();
        perbaruiTotal();
    }
}
