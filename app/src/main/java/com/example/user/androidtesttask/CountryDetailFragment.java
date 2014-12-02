package com.example.user.androidtesttask;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.larvalabs.svgandroid.SVG;
import com.squareup.picasso.Picasso;

/**
 * Created by User on 30.11.2014.
 */
public class CountryDetailFragment extends Fragment implements View.OnClickListener {

    public interface OnViewSelected {
        public void onViewSelected(CountryDetail viewId);
    }

    private static final String COUNTRY_DETAIL_URL = "http://restcountries.eu/rest/v1/alpha/";
    private static final String FLAGS_URL = "http://www.geognos.com/api/en/countries/flag/";
    private CountryDetail countryDetail;
    private Country country;
    private ExpandableListView expandableListView;
    private ImageView imageViewFlag;
    private ImageView imageViewButton;
    private TextView textLatitude;
    private TextView textLongitude;
    private TextView textCapital;
    private TextView textRegion;
    private TextView textArea;
    private TextView textCallingCode;
    private Button button;
    private OnViewSelected mClickListener;

    public static CountryDetailFragment newInstance(Country code) {
        Bundle args = new Bundle();
        args.putSerializable("code", code);
        CountryDetailFragment countryDetailFragment = new CountryDetailFragment();
        countryDetailFragment.setArguments(args);
        return countryDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        countryDetail = new CountryDetail();
        country = (Country) getArguments().getSerializable("code");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.country_detail_fragment, container, false);

        imageViewFlag = (ImageView) view.findViewById(R.id.img_Flag);
        textLatitude = (TextView) view.findViewById(R.id.tv_Latitude);
        textLongitude = (TextView) view.findViewById(R.id.tv_Longitude);
        textCapital = (TextView) view.findViewById(R.id.tv_Capital);
        textRegion = (TextView) view.findViewById(R.id.tv_Region);
        textArea = (TextView) view.findViewById(R.id.tv_Area);
        textCallingCode = (TextView) view.findViewById(R.id.tv_CallingCode);
        button = (Button) view.findViewById(R.id.btn_Go_Map);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mClickListener = (OnViewSelected) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onViewSelected");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        CountryDetaiDownloadFromJSonAsynkTask countryDetaiDownloadFromJSonAsynkTask = new CountryDetaiDownloadFromJSonAsynkTask();
        countryDetaiDownloadFromJSonAsynkTask.execute(country);
        //button.setOnClickListener((View.OnClickListener) this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Fragment fragment = MyMapFragment.newInstance(countryDetail);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, fragment);
                ft.addToBackStack("tag");
                ft.commitAllowingStateLoss();
            }
        });
    }
    @Override
    public void onClick(View v) {
        mClickListener.onViewSelected(countryDetail);
    }

    private class CountryDetaiDownloadFromJSonAsynkTask extends AsyncTask<Country, Void, CountryDetail> {
        private Gson mGson;
        private String imgUrl;
        private SVG svg;

        public CountryDetaiDownloadFromJSonAsynkTask() {
            mGson = new GsonBuilder()
                    .registerTypeAdapter(CountryDetail.class, new JsonCaseDeserializer())
                    .create();
        }

        @Override
        protected CountryDetail doInBackground(Country... params) {
            Country item = params[0];
            CountryDetail countryDetail = null;
            HttpRequest request = HttpRequest.get(COUNTRY_DETAIL_URL + item.getmCode());
            if (request.code() == 200) {
                String response = request.body();
                countryDetail = mGson.fromJson(response, CountryDetail.class);
            }
            countryDetail.setFlagCode(item.getmCode());
            return countryDetail;
        }

        @Override
        protected void onPostExecute(CountryDetail result) {
            super.onPostExecute(result);
            countryDetail = result;
            getActivity().getActionBar().setTitle(country.getmName());
            imgUrl = FLAGS_URL + countryDetail.getFlagCode() + ".png";

            // imgUrl = "http://www.geognos.com/api/en/countries/flag/UA.png";
            // svg = SVGParser.getSVGFromString(imgUrl);
            // Drawable drawable = svg.createPictureDrawable();
            //  imageViewFlag.setImageDrawable(drawable);

            loadPicture(imgUrl, imageViewFlag);

            //imageViewFlag.setImageResource(R.drawable.ic_launcher);

            //loadPicture(imgUrl, imageViewFlag);
            textLatitude.setText("Latitude: " + countryDetail.getGeoPoints().get(0).toString());
            textLongitude.setText("Longitude: " + countryDetail.getGeoPoints().get(1).toString());
            textCapital.setText("Capital: " + String.valueOf(countryDetail.getmCapital()));
            textRegion.setText("Region: " + String.valueOf(countryDetail.getmRegion()));
            textArea.setText("Area: " + String.valueOf(countryDetail.getmArea()));
            textCallingCode.setText("CallingCode: " + String.valueOf(countryDetail.getmCallingCode()));

        }
    }

    private void loadPicture(String url, ImageView imageView) {
        Picasso.with(getActivity())
                .load(url)
                //.placeholder(R.drawable.ic_launcher)
                //.error(R.drawable.ic_launcher)
                //.resizeDimen(80, 80)
                //.centerInside()

                .into(imageView);
    }

}
