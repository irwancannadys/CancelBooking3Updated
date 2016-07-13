package tukang.id.cancelbooking3updated;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by user on 11/07/16.
 */
public class FrCancelBooking extends DialogFragment {

    String[] keluhan = {
            "Mau mengganti alamat",
            "Saya menunggu terlalu lama",
            "Sudah pesan ditempat lain",
            "Tidak mendapat konfirmasi sama sekali",
            "Aplikasi ini membingungkan",
            CustomApplication.getContext().getString(R.string.lainnya)
    };

    Button btnCancel;

    AppCompatActivity appCompatActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);

        return view;
    }
}
