package fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.onaopemipodimowo.apptest.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {
    EditText etLocation,etDestination;
    Button btTrack;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //list = view.findViewById(R.id.list);
        etLocation = view.findViewById(R.id.et_location);
        etDestination = view.findViewById(R.id.et_destination);
        btTrack = view.findViewById(R.id.bt_track);

        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get value from edit text
                String sLocation = etLocation.getText().toString().trim();
                String sDestination = etDestination.getText().toString().trim();

                // Checkk condtion
                if (sLocation.equals("") && sDestination.equals("")){
                    // when both value are blank
                    Toast.makeText(getContext(), "Enter both Location", Toast.LENGTH_SHORT).show();
                }else {
                    // when both value are filled
                    // Show Location
                    ShowLocation(sLocation,sDestination);
                }
            }
        });
    }

    private void ShowLocation(String sLocation, String sDestination) {
        // redirect to playstore if map not installed
        try {
            //When google map is installed
            //Intialize uri
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + sLocation + "/"  + sDestination);
            //Initialize intent with aaction view
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            //Set package
            intent.setPackage("com.google.android.apps.maps");
            // set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //Start ACTIVITY
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            // when google map is not installed
            // intitialize uri
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            // set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // start activity
            startActivity(intent);

        }
    }
}