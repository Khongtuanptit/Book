package vn.name.khongduytuan.thereader;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import vn.name.khongduytuan.thereader.api.ApiLayAnh;
import vn.name.khongduytuan.thereader.interfaces.LayAnhVe;
import vn.name.khongduytuan.thereader.object.TruyenTranh;

public class DocTruyenActivity extends AppCompatActivity implements LayAnhVe {
ImageView imgAnh;
ArrayList<String> arrUrlAnh;
int soTrang, soTrangDangDoc;
TextView txvSoTrang;
String idChap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_truyen);
        init();
        anhXa();
        setUp();
        setClick();
        new ApiLayAnh(this, idChap).execute();
    }

    private void init(){
        Bundle b = getIntent().getBundleExtra("data");
        idChap = b.getString("idChap");

    }
    private void anhXa(){
        imgAnh = findViewById(R.id.imgAnh);
        txvSoTrang = findViewById(R.id.txvSoTrang);
    }
    private void setUp(){
//        docTheoTrang(0);

    }
    private void setClick(){

    }



    public void left(View view) {
        docTheoTrang(-1);
    }

    public void right(View view) {
        docTheoTrang(1);
    }

    private void docTheoTrang(int i){
        soTrangDangDoc = soTrangDangDoc + i;
        if(soTrangDangDoc == 0){
            soTrangDangDoc = 1;
        }
        if(soTrangDangDoc > soTrang){
            soTrangDangDoc = soTrang;
        }
        txvSoTrang.setText(soTrangDangDoc + " / " + soTrang);
        Glide.with(this).load(arrUrlAnh.get(soTrangDangDoc-1)).into(imgAnh);
    }

    @Override
    public void batDau() {

    }

    @Override
    public void keThuc(String data) {
        arrUrlAnh = new ArrayList<>();
        try{
            JSONArray arr = new JSONArray(data);
            for(int i = 0; i < arr.length(); i++){
                arrUrlAnh.add(arr.getString(i));
            }
            soTrangDangDoc = 1;
            soTrang = arrUrlAnh.size();
            docTheoTrang(0);

        }catch (JSONException e){

        }


    }

    @Override
    public void biLoi() {

    }
}