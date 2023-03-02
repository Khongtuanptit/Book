package vn.name.khongduytuan.thereader.api;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import vn.name.khongduytuan.thereader.interfaces.LayChapVe;
import vn.name.khongduytuan.thereader.interfaces.LayTruyenVe;

public class ApiChapTruyen extends AsyncTask<Void, Void, Void> {
    String data;
    LayChapVe layChapVe;
    String idTruyen;

    public ApiChapTruyen(LayChapVe layChapVe, String idTruyen) {
        this.layChapVe = layChapVe;
        this.layChapVe.batDau();
        this.idTruyen = idTruyen;
    }
//https://api.jsonserve.com/wyiK1U
//https://api.jsonserve.com/cELzEZ
//https://edgier-tubs.000webhostapp.com/
    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://edgier-tubs.000webhostapp.com/layChap.php?id=" + idTruyen)
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
            this.layChapVe.biLoi();
        }
        else{
            this.layChapVe.keThuc(data);
        }

    }

}
