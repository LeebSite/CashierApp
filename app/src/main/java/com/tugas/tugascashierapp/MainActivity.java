package com.tugas.tugascashierapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.HashMap;
import android.widget.FrameLayout;
import java.util.Map;
import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalHarga;
    private EditText etNama, etCariMenu, etMenu1Jumlah, etMenu2Jumlah, etMenu3Jumlah, etMenu4Jumlah;
    private RadioGroup radioGroup;
    private RadioButton rbGold, rbSilver, rbBronze, rbNone;
    private Button btnBeli, btnReset, btnCariMenu;
    private int selectedFrameId = -1;

    private HashMap<String, Integer> menuFrameMap = new HashMap<>();

    private int totalHarga = 0;

    private int getQuantity(EditText editText) {
        String quantityStr = editText.getText().toString().trim();
        if (quantityStr.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(quantityStr);
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalHarga = findViewById(R.id.tvTotalHarga);
        etNama = findViewById(R.id.etNama);
        etCariMenu = findViewById(R.id.etCariMenu);
        etMenu1Jumlah = findViewById(R.id.etMenu1Jumlah);
        etMenu2Jumlah = findViewById(R.id.etMenu2Jumlah);
        etMenu3Jumlah = findViewById(R.id.etMenu3Jumlah);
        etMenu4Jumlah = findViewById(R.id.etMenu4Jumlah);
        radioGroup = findViewById(R.id.radioGroup);
        rbGold = findViewById(R.id.rbGold);
        rbSilver = findViewById(R.id.rbSilver);
        rbBronze = findViewById(R.id.rbBronze);
        rbNone = findViewById(R.id.rbNone);
        btnBeli = findViewById(R.id.btnBeli);
        btnCariMenu = findViewById(R.id.btnCariMenu);
        btnReset = findViewById(R.id.btnReset);

        // Simpan daftar nama menu beserta frame ID nya
        HashMap<String, Integer> menuFrameMap = new HashMap<>();
        menuFrameMap.put("Beef Burger", R.id.frmMenu1_container);
        menuFrameMap.put("beef burger", R.id.frmMenu1_container);
        menuFrameMap.put("Lemon Cola", R.id.frmMenu2_container);
        menuFrameMap.put("lemon cola", R.id.frmMenu2_container);
        menuFrameMap.put("Kentucky", R.id.frmMenu3_container);
        menuFrameMap.put("kentucky", R.id.frmMenu3_container);
        menuFrameMap.put("French Fries", R.id.frmMenu4_container);
        menuFrameMap.put("french fries", R.id.frmMenu4_container);

        btnCariMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = etCariMenu.getText().toString().trim();
                if (!keyword.isEmpty()) {
                    for (Map.Entry<String, Integer> entry : menuFrameMap.entrySet()) {
                        if (entry.getKey().equalsIgnoreCase(keyword)) {
                            int frameId = entry.getValue();
                            FrameLayout selectedFrame = findViewById(frameId);
                            selectedFrame.setBackgroundResource(R.drawable.selected_frame_background);
                            selectedFrameId = frameId; //utk simpan Id frame yg udh dipilih
                        }
                    }
                }
            }
        });

        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengubah input menjadi 0 jika tidak diisi
                if (etMenu1Jumlah.getText().toString().isEmpty()) {
                    etMenu1Jumlah.setText("0");
                }
                if (etMenu2Jumlah.getText().toString().isEmpty()) {
                    etMenu2Jumlah.setText("0");
                }
                if (etMenu3Jumlah.getText().toString().isEmpty()) {
                    etMenu3Jumlah.setText("0");
                }
                if (etMenu4Jumlah.getText().toString().isEmpty()) {
                    etMenu4Jumlah.setText("0");
                }
                hitungTotalHarga();
                tampilDeskripsiBeli();
            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
            }
        });
    }

    private void hitungTotalHarga() {
        int menu1Price = Integer.parseInt(etMenu1Jumlah.getText().toString()) * 20000;
        int menu2Price = Integer.parseInt(etMenu2Jumlah.getText().toString()) * 12000;
        int menu3Price = Integer.parseInt(etMenu3Jumlah.getText().toString()) * 26000;
        int menu4Price = Integer.parseInt(etMenu4Jumlah.getText().toString()) * 18000;

        totalHarga = menu1Price + menu2Price + menu3Price + menu4Price;

        int discount = 0;

        totalHarga -= discount;

        // Diskon jika total harga melebihi 45000
        if (totalHarga > 45000) {
            totalHarga -= 8000;
        }
        // Menghitung diskon berdasarkan membership
        if (radioGroup.getCheckedRadioButtonId() == R.id.rbGold) {
            discount = 10000;
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbSilver) {
            discount = 6000;
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbBronze) {
            discount = 3000;
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbNone) {
            discount = 0;
        }

        // Update dari tvTotalHarga
        tvTotalHarga.setText("Total Harga : Rp." + totalHarga);
    }

    private void tampilDeskripsiBeli() {
        // Tampilan Deskripsi Pembelian
        StringBuilder summary = new StringBuilder("============================\n");
        String namaPembeli = etNama.getText().toString().trim();
        summary.append("Nama Pembeli: ").append(namaPembeli).append("\n");
        if (Integer.parseInt(etMenu1Jumlah.getText().toString()) > 0) {
            summary.append("- Cheese Burger x").append(etMenu1Jumlah.getText().toString())
                    .append(" (Rp. ").append(Integer.parseInt(etMenu1Jumlah.getText().toString()) * 20000).append(")\n");
        }
        if (Integer.parseInt(etMenu2Jumlah.getText().toString()) > 0) {
            summary.append("- Lemon Cola x").append(etMenu2Jumlah.getText().toString())
                    .append(" (Rp. ").append(Integer.parseInt(etMenu2Jumlah.getText().toString()) * 12000).append(")\n");
        }
        if (Integer.parseInt(etMenu3Jumlah.getText().toString()) > 0) {
            summary.append("- Kentucky x").append(etMenu3Jumlah.getText().toString())
                    .append(" (Rp. ").append(Integer.parseInt(etMenu3Jumlah.getText().toString()) * 26000).append(")\n");
        }
        if (Integer.parseInt(etMenu4Jumlah.getText().toString()) > 0) {
            summary.append("- Potato Fries x").append(etMenu4Jumlah.getText().toString())
                    .append(" (Rp. ").append(Integer.parseInt(etMenu4Jumlah.getText().toString()) * 18000).append(")\n");
        }

        summary.append("Membership: ");
        if (radioGroup.getCheckedRadioButtonId() == R.id.rbGold) {
            summary.append("Gold\n");
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbSilver) {
            summary.append("Silver\n");
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbBronze) {
            summary.append("Platinum\n");
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbNone) {
            summary.append("Tidak Ada\n");
        }

        int diskon = 0;

        // Menampilkan diskon jika total harga melebihi 45000
        if (totalHarga > 45000) {
            diskon += 8000;
            summary.append("Diskon (total harga > 45000): -Rp 8000\n");
        } else {
            summary.append("Diskon (total harga > 45000): -\n");
        }

        // diskon Membership
        if (radioGroup.getCheckedRadioButtonId() == R.id.rbGold) {
            diskon += 10000;
            summary.append("Diskon Membership Gold: -Rp 10000\n");
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbSilver) {
            diskon += 6000;
            summary.append("Diskon Membership Silver: -Rp 6000\n");
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbBronze) {
            diskon += 3000;
            summary.append("Diskon Membership Bronze: -Rp 3000\n");
        } else {
            summary.append("Diskon Membership: -\n");
        }

        // Menampilkan total harga untuk nota
        summary.append("\nTotal Harga: Rp ").append(totalHarga).append("\n");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nota Pembelian")
                .setMessage(summary.toString())
                .setPositiveButton("Beli", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Reset data
                        resetData();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void resetData() {
        etNama.setText("");
        etCariMenu.setText("");
        etMenu1Jumlah.setText("");
        etMenu2Jumlah.setText("");
        etMenu3Jumlah.setText("");
        etMenu4Jumlah.setText("");
        radioGroup.clearCheck();
        tvTotalHarga.setText("Total Harga : Rp.");
        if (selectedFrameId != -1) {
            FrameLayout selectedFrame = findViewById(selectedFrameId);
            selectedFrame.setBackground(ContextCompat.getDrawable(MainActivity.this, android.R.color.transparent));
            selectedFrameId = -1; // Reset ID frame yg terpilih
        }
        if (menuFrameMap != null) {
            // Untuk reset frame menu
            for (int frameId : menuFrameMap.values()) {
                FrameLayout frameLayout = findViewById(frameId);
                frameLayout.setBackground(ContextCompat.getDrawable(MainActivity.this, android.R.color.transparent));
            }
        }
    }
}