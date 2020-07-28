package com.example.popularmovies;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.model.JsonModel;
import com.example.popularmovies.model.Data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {



    public MutableLiveData<Integer> page;
    public MutableLiveData<String> sort;
    MutableLiveData<List<Data>> arr= new MutableLiveData<>();
    String key="400225a1886f38d9cf3c934d6a756c4d";

    void getData(){
        if(page==null){
            page=new MutableLiveData<>();
            sort=new MutableLiveData<>();
            page.setValue(1);

        }
        try {
            Connection connection=RetrofitInstance.getInstance();
            Call<JsonModel> call=connection.getData(key,page.getValue(),sort.getValue());
            call.enqueue(new Callback<JsonModel>() {
                @Override
                public void onResponse(Call<JsonModel> call, Response<JsonModel> response) {
                    JsonModel jsonModel = response.body();
                    ArrayList<Data> list=new ArrayList<>();
                    for(int i=0;i<jsonModel.results.length;i++){
                        list.add(new Data(jsonModel.results[i].getVote_average()
                                ,jsonModel.results[i].getTitle()
                                ,jsonModel.results[i].getPoster_path()
                                ,jsonModel.results[i].getOriginal_language()
                                ,jsonModel.results[i].getOverview(),
                                jsonModel.results[i].getRelease_date()));
                    }
                    arr.setValue(list);
                }
                @Override
                public void onFailure(Call<JsonModel> call, Throwable t) {
                    //Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
           // Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
