  package fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onaopemipodimowo.apptest.Home;
import com.onaopemipodimowo.apptest.HomeAdapter;
import com.onaopemipodimowo.apptest.MainActivity;
import com.onaopemipodimowo.apptest.R;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment {
      private HomeAdapter adapter;

      private List<Home> homes;
      private String TAG = "HomeFragment";

      private RecyclerView list;
      private TextView mTextViewResult;
      private String myResponse;



    public HomeFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

      @Override
      public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
          super.onViewCreated(view, savedInstanceState);
          list = view.findViewById(R.id.list);
          homes = new ArrayList<>();
          //create adapter
//          HomeAdapter homeAdapter = new HomeAdapter(requireContext(),homes);
          // Set the adapter on the recycler view
          adapter = new HomeAdapter(getContext(),homes);
          list.setAdapter(adapter);
          //Set a layout Manager on the recycler view
          list.setLayoutManager(new LinearLayoutManager(getContext()));
          // recyclerView.setAdapter(new city(arrayList));
          mTextViewResult = view.findViewById(R.id.text_view_result);
          getData();

          OkHttpClient client = new OkHttpClient();

          String url = "https://us-real-estate.p.rapidapi.com/v2/for-rent?city=Detroit&state_code=MI";
          String myResponse = "";
          boolean apikey = false;
          try {
              DB snappydb = DBFactory.open(requireContext());
              apikey = snappydb.exists(url);
              if (apikey){
                  myResponse=snappydb.get(url);

                  Log.i(TAG,"fromcache");
              }

          } catch (SnappydbException e) {
              e.printStackTrace();
          }
          if (apikey){
              return;
          }
          Request request = new Request.Builder()
                  .url(url)
                  .get()
                  .addHeader("X-RapidAPI-Key", "b9b6e83a73msh284bf2ee0af9f72p1b3302jsnbce6e1f38d7c")
                  .addHeader("X-RapidAPI-Host", "us-real-estate.p.rapidapi.com")
                  .build();
          client.newCall(request).enqueue(new Callback() {
              @Override
              public void onFailure(@NonNull Call call, @NonNull IOException e) {
                  e.printStackTrace();
              }


              @Override
              public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                  if (response.isSuccessful()){
                      //  response.body().string();
                      String myResponse = response.body().string();
                      Log.i(TAG,"JSON" + myResponse);

                      HomeFragment.this.getActivity().runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              // mTextViewResult.setText(myResponse);
                              Log.i(TAG,"fromApi");
                          }
                      });
                      try {
                          DB snappydb = DBFactory.open(requireContext().getApplicationContext());
                          snappydb.put(url,myResponse);
                          snappydb.close();

                      } catch (SnappydbException e) {
                          e.printStackTrace();
                      }
                  }

              }
          });
      }

      //JSON Array object class to get data
      public void getData(){
          String url = "https://us-real-estate.p.rapidapi.com/v2/for-rent?city=Detroit&state_code=MI";

          OkHttpClient client = new OkHttpClient();

          Request request = new Request.Builder()
                  .url(url)
                  .get()
                  .addHeader("X-RapidAPI-Key", "b9b6e83a73msh284bf2ee0af9f72p1b3302jsnbce6e1f38d7c")
                  .addHeader("X-RapidAPI-Host", "us-real-estate.p.rapidapi.com")
                  .build();
          client.newCall(request).enqueue(new Callback() {
              @Override
              public void onFailure(@NonNull Call call, @NonNull IOException e) {
                  e.printStackTrace();
                  getActivity().runOnUiThread(new Runnable() {
                      public void run() {
                          Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                      }
                  });
              }


              @Override
              public void onResponse(Call call, Response response) throws IOException {

                  final String myResponse = response.body().string();

                  HomeFragment.this.getActivity().runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          //for (int i = 0; i <= myResponse.length(); i++) {
                          try {
                              JSONObject jsonObject = new JSONObject(myResponse);

                              JSONObject dataObject = jsonObject.getJSONObject("data");
                              JSONObject homeSearchObject = dataObject.getJSONObject("home_search");

                              JSONArray dataArr2 = homeSearchObject.getJSONArray("results");

                              for (int i = 0; i <= dataArr2.length(); i++) {
                                  JSONObject dataObject2 = dataArr2.getJSONObject(i);

                                  JSONObject dataObject3 = dataObject2.getJSONObject("location");
                                  JSONObject dataObject4 = dataObject3.getJSONObject("address");

                                  JSONObject dataObject5 = dataObject2.getJSONObject("description");

                                  //  Log.d("data", String.valueOf(dataObject5.getDouble("baths_min")));

                                  homes.add(new Home(
                                          dataObject5.getString("name"),
                                          dataObject4.getString("city"),
                                          dataObject4.getString("state_code"),
                                          dataObject4.getString("line"),
                                          dataObject5.getString("type"),
                                          dataObject5.getInt("baths_min"),
                                          dataObject5.getInt("baths_max"),
                                          dataObject5.getInt("beds_min"),
                                          dataObject5.getInt("beds_max")
                                  ));


                              }


                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                          //}
                          adapter=new HomeAdapter(requireContext(),homes);
                          list.setAdapter(adapter);
                          adapter.notifyDataSetChanged();
                          Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                      }
                  });

              }
          });

      }
}