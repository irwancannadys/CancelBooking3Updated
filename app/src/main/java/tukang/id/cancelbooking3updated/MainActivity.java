package tukang.id.cancelbooking3updated;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity{


    Activity activity;

    public static final String SERVER_URL = "";

//    String value;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        appCompatActivity = this;

        final ListView listView = (ListView) findViewById(R.id.list_view);
        // set the adapter to fill the data in ListView
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        final CustomListAdapter customAdapter = new CustomListAdapter(getApplicationContext(), keluhan, appCompatActivity);
        listView.setAdapter(customAdapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               btnCancel.setBackgroundColor(Color.parseColor("#FF9801"));

               for(int i=0; i< customAdapter.getCount(); i++){

                   View v = getViewByPosition(i,listView);
                   CheckedTextView checkedTextViewRow = (CheckedTextView) v.findViewById(R.id.simpleCheckedTextView);
                   checkedTextViewRow.setCheckMarkDrawable(R.drawable.ic_check_blank);
                   checkedTextViewRow.setChecked(false);
                   checkedTextViewRow.setTextColor(Color.parseColor("#000000"));
               }


               final CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.simpleCheckedTextView);

               if(checkedTextView.isChecked()) {
                   checkedTextView.setCheckMarkDrawable(R.drawable.ic_check_blank);
                   checkedTextView.setChecked(false);
                   checkedTextView.setTextColor(Color.parseColor("#000000"));
               }else{
                   checkedTextView.setCheckMarkDrawable(R.drawable.ic_check);
                   checkedTextView.setChecked(true);
                   checkedTextView.setTextColor(Color.parseColor("#ff9801"));
               }

               if (position == 5 ){
                   LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                   final View other_reason = layoutInflater.inflate(R.layout.other_reason, null);
                   final EditText et_other_reason = (EditText) other_reason.findViewById(R.id.et_other_reason);

                   Drawable drawable = et_other_reason.getBackground(); // get current EditText drawable
                   drawable.setColorFilter(Color.parseColor("#cdcdcd"), PorterDuff.Mode.SRC_ATOP); // change the drawable color

                   et_other_reason.setBackground(drawable); // set the new drawable to EditText

                   final String otherReasonString = checkedTextView.getText().toString();
                   if(!otherReasonString.equals(getString(R.string.lainnya)) && otherReasonString.length() > 32){
                       et_other_reason.setText(otherReasonString.substring(9,otherReasonString.length()-23));
                   }

                   final AlertDialog.Builder dialogMasukan = new AlertDialog.Builder(appCompatActivity
                           , R.style.AppCompatAlertDialogStyle);
                   dialogMasukan.setTitle("Alasan lainnya");
                   dialogMasukan.setView(other_reason);
                   dialogMasukan.setPositiveButton("SELESAI", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           String otherReasonStringNew = et_other_reason.getText().toString();

                           if(!otherReasonStringNew.trim().equals(""))
                                checkedTextView.setText("Lainnya: "+otherReasonStringNew+" (ketuk untuk mengubah)");
                           else
                               checkedTextView.setText(getString(R.string.lainnya));
                           dialogInterface.dismiss();
                       }
                   });
                   dialogMasukan.show();
               }
           }
       });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isListViewChecked = false;

                for(int i=0; i< customAdapter.getCount(); i++){

                    View v = getViewByPosition(i,listView);
                    CheckedTextView checkedTextViewRow = (CheckedTextView) v.findViewById(R.id.simpleCheckedTextView);
                    checkedTextViewRow.setCheckMarkDrawable(R.drawable.ic_check_blank);

                    if(checkedTextViewRow.isChecked()) {
                        isListViewChecked = true;
                        break;
                    }
                }

                if(isListViewChecked){
                    final AlertDialog.Builder feedBack = new AlertDialog.Builder(appCompatActivity
                            , R.style.AppCompatAlertDialogStyle);
                    feedBack.setTitle("Pesanan dibatalkan");
                    feedBack.setMessage("Terima kasih atas feedback anda");
                    feedBack.setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                        }
                    });
                    feedBack.show();
                    //Toast.makeText(getApplicationContext(),"tes",Toast.LENGTH_SHORT).show();
                }else{
                    final AlertDialog.Builder feedBack = new AlertDialog.Builder(appCompatActivity
                            , R.style.AppCompatAlertDialogStyle);
                    feedBack.setMessage("Harap pilih alasan pembatalan dahulu");
                    feedBack.setPositiveButton("TUTUP", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    feedBack.show();
                }
            }
        });
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;
        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition; return listView.getChildAt(childIndex);

        }
    }

    private void prosesCancel(FormEncodingBuilder formEncodingBuilder){

        RequestBody requestBody = formEncodingBuilder
                .add("validator","afe13Rg78#*Agy")
                .add("version_code",String.valueOf(BuildConfig.VERSION_CODE))
                .build();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(SERVER_URL)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        toggleLoadingVisible(false,"");

                        String errMessage = e.toString();

                        Log.e("c3--", "Error onFailure#" + e.toString());

                        if(errMessage.contains("Timeout")){
                            errMessage = CustomApplication.getContext().getString(R.string.cant_connect_to_server);
                        }else if(errMessage.contains("UnknownHostException")){
                            errMessage = CustomApplication.getContext().getString(R.string.please_check_your_internet_connection);
                        }else{
                            errMessage = "Terjadi kesalahan. Kode 1";
                        }

                        Toast.makeText(getApplicationContext(), errMessage, Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {

                final String response1 = response.body().string();

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        toggleLoadingVisible(false,"");

                        String responseStr;
                        responseStr = response1;

                        Log.e("c4--", "responseStr#" + responseStr);

                        String msgToTrack = "";
                        if (response.isSuccessful()) {
                            //alert responseStr
                        } else {


                            Log.e("c5--","Can't process#"+responseStr);
                        }

                    }

                });

            }
        });
    }

    private void toggleLoadingVisible(boolean b, String loading_text_str) {

    }
}
