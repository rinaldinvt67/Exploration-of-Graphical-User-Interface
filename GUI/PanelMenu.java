package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelMenu extends JPanel{
    private static final Color Warna_normal = new Color(240, 240, 240);
    private static final Color Warna_hover = new Color(255, 224, 160);
    
    public PanelMenu(ItemMenu[] daftarMenu, PesananListener listener) {
        setLayout(new GridLayout(0, 2, 8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for(int i = 0; i < daftarMenu.length; i++) {
            ItemMenu item = daftarMenu[i];
            JButton tombol = new JButton(item.toString());
            tombol.setBackground(Warna_normal);
            tombol.setFocusPainted(false);

            tombol.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    tombol.setBackground(Warna_hover);
                    tombol.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override 
                public void mouseExited(MouseEvent e) {
                    tombol.setBackground(Warna_normal);
                }

                @Override 
                public void mouseClicked(MouseEvent e) {
                    if(SwingUtilities.isLeftMouseButton(e)) {
                        listener.onMenuDipilih(item);
                    }
                }
            });

            add(tombol);
        }
    }
}
