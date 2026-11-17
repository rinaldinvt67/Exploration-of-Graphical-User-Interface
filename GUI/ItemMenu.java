package GUI;

public class ItemMenu {
    private String nama;
    private int harga;

    public ItemMenu(String nama, int harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }
    public int getHarga() {
        return harga;
    }

    public static String formatRupiah(int nilai) {
        return String.format("Rp %,d", nilai).replace(',', '.');
    }

    @Override 
    public String toString() {
        return nama + " - " + formatRupiah(harga);
    }
}
