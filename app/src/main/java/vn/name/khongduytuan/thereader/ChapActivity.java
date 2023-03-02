package vn.name.khongduytuan.thereader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import vn.name.khongduytuan.thereader.adapter.ChapTruyenAdapter;
import vn.name.khongduytuan.thereader.api.ApiChapTruyen;
import vn.name.khongduytuan.thereader.interfaces.LayChapVe;
import vn.name.khongduytuan.thereader.object.ChapTruyen;
import vn.name.khongduytuan.thereader.object.TruyenTranh;

public class ChapActivity extends AppCompatActivity implements LayChapVe {
TextView txvTenTruyens;
ImageView imgAnhTruyens;
TruyenTranh truyenTranh;
ListView lsvDanhSachChap;
ArrayList<ChapTruyen> arrChap;
ChapTruyenAdapter chapTruyenAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap);
        init();
        anhXa();
        setUp();
        setClick();
        new ApiChapTruyen(this, truyenTranh.getId()).execute();

    }
    private void init(){
        Bundle b = getIntent().getBundleExtra("data");
        truyenTranh = (TruyenTranh) b.getSerializable("truyen");

        //tao du lieu ao
        arrChap = new ArrayList<>();
//        for(int i = 0; i< 20; i++){
//            arrChap.add(new ChapTruyen("Chap" + i, "01-12-2022"));
//        }
//        chapTruyenAdapter = new ChapTruyenAdapter(this, 0, arrChap);

    }
    private void anhXa(){
        txvTenTruyens = findViewById(R.id.txvTenTruyens);
        imgAnhTruyens = findViewById(R.id.imgAnhTruyens);
        lsvDanhSachChap = findViewById(R.id.lsvDanhSachChap);

    }
    private void setUp(){
        txvTenTruyens.setText(truyenTranh.getTenTruyen());
        Glide.with(this).load(truyenTranh.getLinkAnh()).into(imgAnhTruyens);

        //lsvDanhSachChap.setAdapter(chapTruyenAdapter);

    }
    private void setClick(){
        lsvDanhSachChap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle b = new Bundle();
                b.putString("idChap", arrChap.get(i).getId());
                Intent intent = new Intent(ChapActivity.this, DocTruyenActivity.class);
                intent.putExtra("data", b);
                startActivity(intent);


            }
        });

    }

    @Override
    public void batDau() {
        Toast.makeText(this, "Đang Lấy Các Chap Về!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void keThuc(String data) {
        try {
            JSONArray array = new JSONArray(data);
            for(int i = 0; i < array.length(); i++){
                ChapTruyen chapTruyen = new ChapTruyen(array.getJSONObject(i));
                arrChap.add(chapTruyen);
            }
            chapTruyenAdapter = new ChapTruyenAdapter(this, 0, arrChap);
            lsvDanhSachChap.setAdapter(chapTruyenAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void biLoi() {

    }
}