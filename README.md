# Exploration of Graphical User Interface

Aplikasi **Kasir Kafe** sederhana berbasis GUI yang dibuat dengan Java Swing dan AWT. Proyek ini dibuat sebagai latihan Pemrograman Berorientasi Objek (PBO) untuk mempelajari komponen GUI, layout, dan *event handling* (khususnya event mouse).

Pengguna memilih menu minuman atau makanan dari tab yang tersedia, pesanan masuk ke daftar di sisi kanan, total harga dihitung otomatis, dan pesanan bisa dibayar, direset, atau dihapus per item.

---

## Fitur

- **Dua kategori menu** (Minuman dan Makanan) dalam tab terpisah.
- **Efek hover**: tombol menu berubah warna dan kursor menjadi tangan saat disorot mouse.
- **Klik kiri** pada tombol menu untuk menambahkan item ke pesanan.
- **Klik kanan** pada item di daftar pesanan untuk memunculkan menu **Hapus item**.
- **Total harga otomatis** diperbarui setiap pesanan ditambah atau dihapus, dengan format rupiah (`Rp 12.000`).
- **Tombol Bayar** menampilkan dialog total pembayaran lalu mengosongkan pesanan.
- **Tombol Reset** mengosongkan seluruh pesanan.

---

## Tampilan Aplikasi

| | |
|---|---|
| ![Tampilan Menu Tab Minuman](GUI/Screenshot/Screenshot%202026-11-17%20191806.png) | ![Tampilan Menu Tab Makanan(GUI/Screenshot/Screenshot%202026-11-17%20191817.png) |
| ![Tampilan Pesanan Masuk](GUI/Screenshot/Screenshot%202026-11-17%20191831.png) | ![Tampilan Tombol Bayar Ditekan](GUI/Screenshot/Screenshot%202026-11-17%20191903.png) |
| ![Tampilan Klik Kanan Nama Pesanan Untuk Hapus Item](GUI/Screenshot/Screenshot%202026-11-17%20191934.png) | ![Tampilan Handling Pesanan Yang Kosong](GUI/ScreenshotScreenshot%202026-11-18%20070506.png) |
| ![Tampilan Scrollbar](GUI/Screenshot/Screenshot%202026-11-17%20192120.png) | |

---

## Struktur Proyek

```
.
├── README.md
└── GUI/
    ├── Main.java
    ├── FrameKasir.java
    ├── PanelMenu.java
    ├── PanelPesanan.java
    ├── PesananListener.java
    ├── ItemMenu.java
    └── Screenshot/        <- gambar tampilan aplikasi
```

Semua kelas berada di package `GUI`, sehingga file-nya harus berada di dalam folder `GUI`.

---

## Penjelasan Tiap File

| File | Jenis | Peran |
|---|---|---|
| `Main.java` | Class | Titik masuk program. Membuat dan menampilkan `FrameKasir` lewat `SwingUtilities.invokeLater`. |
| `FrameKasir.java` | `JFrame` (top-level container) | Jendela utama. Menyiapkan data menu, merakit tab dan panel, lalu menghubungkan `PanelMenu` dengan `PanelPesanan`. |
| `PanelMenu.java` | `JPanel` | Menampilkan tombol-tombol menu dalam kisi dua kolom dan menangani event mouse (hover dan klik). |
| `PanelPesanan.java` | `JPanel` + `implements PesananListener` | Menampilkan daftar pesanan, total, tombol Bayar dan Reset, serta menu klik kanan untuk menghapus item. |
| `PesananListener.java` | Interface | Kontrak penghubung: siapa pun yang ingin menerima pilihan menu harus memiliki method `onMenuDipilih`. |
| `ItemMenu.java` | Class model | Menyimpan data satu menu (`nama`, `harga`) dan memformat harga ke rupiah. |

### `Main.java`
Berisi method `main`. Pembuatan GUI dijalankan di *Event Dispatch Thread* melalui `SwingUtilities.invokeLater(...)` karena Swing tidak *thread-safe*.

### `FrameKasir.java`
Mewarisi `JFrame`. Tugasnya:
1. Mengatur judul, ukuran, posisi tengah layar, dan perilaku saat ditutup.
2. Membuat `PanelPesanan` dan dua `PanelMenu` (Minuman dan Makanan).
3. Memasukkan kedua `PanelMenu` ke `JTabbedPane`.
4. Menata dengan `BorderLayout`: tab di `CENTER`, panel pesanan di `EAST`.

Dengan `new PanelMenu(minuman, panelPesanan)`, `panelPesanan` dikirim sebagai `PesananListener`. Jadi hanya `FrameKasir` yang mengetahui bahwa kedua panel saling terhubung.

### `PanelMenu.java`
Menerima array `ItemMenu[]` dan sebuah `PesananListener`. Untuk setiap menu dibuat satu `JButton` yang memiliki `MouseAdapter` dengan tiga reaksi:

| Event | Reaksi |
|---|---|
| `mouseEntered` | Warna tombol berubah dan kursor menjadi tangan |
| `mouseExited` | Warna tombol kembali normal |
| `mouseClicked` (klik kiri) | Memanggil `listener.onMenuDipilih(item)` |

`PanelMenu` tidak mengenal `PanelPesanan`. Ia hanya tahu ada `PesananListener` yang harus diberi tahu.

### `PanelPesanan.java`
Mengimplementasikan `PesananListener`. Komponen dan tugasnya:

| Bagian | Fungsi |
|---|---|
| `DefaultListModel<ItemMenu>` | Menyimpan data pesanan |
| `JList<ItemMenu>` | Menampilkan isi model; otomatis diperbarui saat model berubah |
| `JLabel labelTotal` | Menampilkan total harga |
| Tombol **Bayar** | Menampilkan dialog total pembayaran lalu mengosongkan pesanan |
| Tombol **Reset** | Mengosongkan pesanan |
| `JPopupMenu` | Muncul saat klik kanan pada item, berisi aksi **Hapus item** |

### `PesananListener.java`
```java
public interface PesananListener {
    void onMenuDipilih(ItemMenu item);
}
```
Interface ini membuat `PanelMenu` bergantung pada kontrak, bukan pada kelas konkret. Penerima lain dapat ditambahkan tanpa mengubah `PanelMenu`.

### `ItemMenu.java`
Menyimpan `nama` dan `harga` (field `private` dengan getter), serta:
- `formatRupiah(int)` mengubah `12000` menjadi `Rp 12.000`.
- `toString()` menghasilkan teks seperti `Kopi Hitam - Rp 10.000`, dipakai oleh tombol menu dan `JList`.

---

## Alur Kerja Program

```
Main
 └─> FrameKasir
       ├─ membuat PanelPesanan
       └─ membuat PanelMenu (Minuman, Makanan) <── menerima PanelPesanan sebagai PesananListener

Pengguna mengklik tombol menu di PanelMenu
 └─> listener.onMenuDipilih(item)
       └─> PanelPesanan: model.addElement(item) + perbarui total
```

1. `Main` membuat `FrameKasir` di Event Dispatch Thread.
2. `FrameKasir` merakit seluruh tampilan dan menyambungkan panel.
3. Pengguna menyorot tombol menu, lalu warnanya berubah (`mouseEntered`).
4. Pengguna mengklik kiri, lalu `PanelMenu` memanggil `onMenuDipilih(item)`.
5. `PanelPesanan` menambahkan item ke model, `JList` menggambar ulang dirinya, dan total diperbarui.
6. Pengguna dapat mengklik kanan item untuk menghapusnya, atau menekan **Bayar** / **Reset**.

---

## Konsep GUI yang Dipelajari

### Top-level container
Wadah paling atas yang berdiri sendiri sebagai jendela.
- `JFrame` pada `FrameKasir`
- `JOptionPane` untuk dialog pembayaran

### Intermediate container
Wadah perantara untuk mengelompokkan komponen.
- `JPanel` (`PanelMenu`, `PanelPesanan`, dan panel kecil di dalamnya)
- `JTabbedPane` untuk tab Minuman dan Makanan
- `JScrollPane` yang membungkus `JList`

### Atomic component
Komponen terkecil yang berinteraksi langsung dengan pengguna.
- `JButton` (tombol menu, Bayar, Reset)
- `JLabel` (total harga)
- `JList` (daftar pesanan)
- `JMenuItem` (Hapus item)

### Layout manager

| Layout | Digunakan di | Hasil |
|---|---|---|
| `BorderLayout()` | `FrameKasir` | Tab di tengah, panel pesanan di kanan |
| `GridLayout(0, 2, 8, 8)` | `PanelMenu` | Tombol menu dalam dua kolom |
| `BorderLayout(5, 5)` | `PanelPesanan` | Daftar di tengah, total dan tombol di bawah |
| `GridLayout(2, 1, 5, 5)` | `PanelPesanan` (panel `bawah`) | Label total di atas, tombol di bawahnya |
| `GridLayout(1, 2, 5, 5)` | `PanelPesanan` (panel `panelTombol`) | Bayar dan Reset berdampingan |

Format `GridLayout(baris, kolom, jarakHorizontal, jarakVertikal)`. Angka `0` pada baris berarti jumlah baris menyesuaikan banyaknya komponen.

### Event handling

| Listener | Digunakan di | Fungsi |
|---|---|---|
| `MouseAdapter` (`mouseEntered`, `mouseExited`, `mouseClicked`) | `PanelMenu` | Efek hover dan klik kiri untuk menambah pesanan |
| `MouseAdapter` (`mousePressed`, `mouseReleased`) | `PanelPesanan` | Klik kanan memunculkan `JPopupMenu` (`isPopupTrigger()`) |
| `ActionListener` | `PanelPesanan` | Tombol Bayar, Reset, dan menu Hapus item |
| `PesananListener` (buatan sendiri) | `PanelMenu` ke `PanelPesanan` | Mengirim item yang dipilih |

`isPopupTrigger()` dicek di `mousePressed` dan `mouseReleased` karena sistem operasi berbeda memicu klik kanan pada saat yang berbeda.

---

## Konsep PBO yang Diterapkan

| Konsep | Penerapan |
|---|---|
| **Pewarisan** | `FrameKasir extends JFrame`, `PanelMenu` dan `PanelPesanan extends JPanel` |
| **Interface** | `PesananListener` diimplementasikan oleh `PanelPesanan` |
| **Enkapsulasi** | Field `private` pada `ItemMenu` dengan getter, serta field `private` pada `PanelPesanan` |
| **Polimorfisme** | `PanelPesanan` dikirim ke `PanelMenu` sebagai tipe `PesananListener` |
| **Overriding** | `onMenuDipilih`, `toString`, dan method event pada `MouseAdapter` ditandai `@Override` |
| **Lambda expression** | `e -> bayar()`, `e -> reset()`, dan `() -> new FrameKasir().setVisible(true)` |
| **Pemisahan tanggung jawab** | Data (`ItemMenu`), tampilan (`PanelMenu`, `PanelPesanan`), dan perakitan (`FrameKasir`) dipisah per kelas |

---

## Daftar Menu

### Minuman

| Menu | Harga |
|---|---|
| Kopi Hitam | Rp 10.000 |
| Matcha | Rp 20.000 |
| Latte | Rp 22.000 |
| Teh Tarik | Rp 12.000 |
| Jus Jeruk | Rp 15.000 |

### Makanan

| Menu | Harga |
|---|---|
| Roti Bakar | Rp 14.000 |
| Nasi Goreng | Rp 22.000 |
| Kentang Goreng | Rp 16.000 |
| Mie Goreng | Rp 18.000 |

---
