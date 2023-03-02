package vn.name.khongduytuan.thereader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.name.khongduytuan.thereader.adapter.TruyenTranhAdapter;
import vn.name.khongduytuan.thereader.api.ApiLayTruyen;
import vn.name.khongduytuan.thereader.interfaces.LayTruyenVe;
import vn.name.khongduytuan.thereader.object.TruyenTranh;

public class MainActivity extends AppCompatActivity implements LayTruyenVe {
GridView gdvDSTruyen;
TruyenTranhAdapter adapter;
ArrayList<TruyenTranh> truyenTranhArrayList;
EditText editTimKiem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        anhXa();
        setUp();
        setClick();
        new ApiLayTruyen(this).execute();

    }

    private void init(){
        truyenTranhArrayList = new ArrayList<>();
        adapter = new TruyenTranhAdapter(this, 0 , truyenTranhArrayList);
    }

    private void anhXa(){
        gdvDSTruyen = findViewById(R.id.gdvDSTruyen);
        editTimKiem = findViewById(R.id.edtTimKiem);

    }

    private void setUp(){
        gdvDSTruyen.setAdapter(adapter);
    }

    private void setClick(){
        editTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editTimKiem.getText().toString();
                adapter.sortTruyen(s);
            }
        });

        gdvDSTruyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TruyenTranh truyenTranh = truyenTranhArrayList.get(i);
                Bundle b = new Bundle();
                b.putSerializable("truyen", truyenTranh);

                Intent intent = new Intent(MainActivity.this, ChapActivity.class);
                intent.putExtra("data", b);
                startActivity(intent);
            }
        });
    }

    @Override
    public void batDau() {
        Toast.makeText(this, "Đang lấy dữ liệu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void keThuc(String data) {
        try {
            truyenTranhArrayList.clear();
            JSONArray arr = new JSONArray(data);
            for(int i = 0; i < arr.length(); i++){
                JSONObject o = arr.getJSONObject(i);
                truyenTranhArrayList.add(new TruyenTranh(o));
            }
            adapter = new TruyenTranhAdapter(this, 0 , truyenTranhArrayList);
            gdvDSTruyen.setAdapter(adapter);
        }
        catch (JSONException e){

        }

    }

    @Override
    public void biLoi() {
        Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
    }

    public void update(View view) {
        new ApiLayTruyen(this).execute();
    }
}