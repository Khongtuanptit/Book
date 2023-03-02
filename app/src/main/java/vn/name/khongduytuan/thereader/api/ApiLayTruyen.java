package vn.name.khongduytuan.thereader.api;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import vn.name.khongduytuan.thereader.interfaces.LayTruyenVe;

public class ApiLayTruyen extends AsyncTask<Void, Void, Void> {
    String data;
    LayTruyenVe layTruyenVe;

    public ApiLayTruyen(LayTruyenVe layTruyenVe) {
        this.layTruyenVe = layTruyenVe;
        this.layTruyenVe.batDau();
    }
//https://api.jsonserve.com/wyiK1U
//https://api.jsonserve.com/cELzEZ
//https://edgier-tubs.000webhostapp.com/
    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://edgier-tubs.000webhostapp.com/layTruyen.php")
                .build();
        data = null;
        try {

            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            data =  body.string();
        }
        catch (IOException e){
            data = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        if (data == null){
            this.layTruyenVe.biLoi();
        }
        else{
            this.layTruyenVe.keThuc(data);
        }

    }

}
