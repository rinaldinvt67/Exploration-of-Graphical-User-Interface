package GUI;
import javax.swing.*;
import java.awt.*;

public class FrameKasir extends JFrame {
    public FrameKasir() {
        super("Kasir Kafe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        PanelPesanan panelPesanan = new PanelPesanan();

        ItemMenu[] minuman = {
            new ItemMenu("Kopi Hitam", 10000),
            new ItemMenu("Matcha", 20000),
            new ItemMenu("Latte", 22000),
            new ItemMenu("Teh Tarik", 12000),
            new ItemMenu("Jus Jeruk", 15000)
        };

        ItemMenu[] makanan = {
            new ItemMenu("Roti Bakar", 14000),
            new ItemMenu("Nasi Goreng", 22000),
            new ItemMenu("Kentang Goreng", 16000),
            new ItemMenu("Mie Goreng", 18000)
        };

        JTabbedPane tab = new JTabbedPane();
        tab.addTab("Minuman", new PanelMenu(minuman, panelPesanan));
        tab.addTab("Makanan", new PanelMenu(makanan, panelPesanan));
        
        add(tab, BorderLayout.CENTER);
        add(panelPesanan, BorderLayout.EAST);
    }

}
