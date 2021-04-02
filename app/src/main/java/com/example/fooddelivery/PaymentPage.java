package com.example.fooddelivery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PaymentPage extends AppCompatActivity {

    private TextView idTransaksi, totalHarga;
    private Button btnUpload , btnBayar;
    private ImageView buktiPembayaran;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uri;

    private FirebaseDatabase databaseReference1;
    private StorageReference storageReference;

    private String userUID, idTransaction;
    private int total = 0;

    private HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        idTransaksi = findViewById(R.id.noTrans);
        totalHarga = findViewById(R.id.hargapayment);
        buktiPembayaran = findViewById(R.id.imgPayment);
        btnBayar = findViewById(R.id.btnPayment);
        btnUpload = findViewById(R.id.btnUploadPayment);

        userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference1 = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("payment");

        Intent intent = getIntent();
        Map<String, String> trans = new HashMap<>();
        idTransaction = intent.getStringExtra("idTransaksi");
        total = intent.getIntExtra("totalHarga",0);
        hashMap = (HashMap<String, String>) intent.getSerializableExtra("hashmap");

        idTransaksi.setText(idTransaction);
        totalHarga.setText(String.valueOf(total));

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBukti();
            }
        });

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buktiPembayaran.getDrawable() != null) {
                    bayarCheckout();
                } else {
                    Toast.makeText(PaymentPage.this, "Silahkan Upload Bukti Pembayaran, Terima Kasih", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void bayarCheckout() {
        StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getExtensionImage(uri));
        fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri downloadedUri = uriTask.getResult();
                hashMap.replace("buktiPembayaran", downloadedUri.toString());
                databaseReference1.getReference("transaction").child(idTransaction).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            databaseReference1.getReference("cart").orderByChild("idCustomer").equalTo(userUID).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot item : snapshot.getChildren()){
                                        HashMap<String , Object> hashMap = (HashMap<String, Object>) item.getValue();
                                        databaseReference1.getReference("history").child(hashMap.get("idCart").toString()).setValue(hashMap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        databaseReference1.getReference("cart").child(hashMap.get("idCart").toString()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(PaymentPage.this, "Payment Success", Toast.LENGTH_LONG).show();
                                                                startActivity(new Intent(PaymentPage.this, NavigasiTab.class));
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(PaymentPage.this, "Payment Success", Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(PaymentPage.this, "All Checkout Fail", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PaymentPage.this, "Gagal Bayar", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void uploadBukti() {
        openFile();
    }

    private void openFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getExtensionImage(Uri uri) {
        ContentResolver Cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(Cr.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
        }
        Picasso.with(this).load(uri).into(buktiPembayaran);
    }
}