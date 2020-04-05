package com.saifi.shoppurchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.saifi.shoppurchase.adapter.ModelAdapter;
import com.saifi.shoppurchase.constants.Url;
import com.saifi.shoppurchase.model.BrandSpinner;
import com.saifi.shoppurchase.model.Model_Model;
import com.saifi.shoppurchase.model.SeriesModel;
import com.saifi.shoppurchase.util.NoScanResultException;
import com.saifi.shoppurchase.util.ScanFragment;
import com.saifi.shoppurchase.util.ScanResultReceiver;
import com.saifi.shoppurchase.util.Views;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, ScanResultReceiver {

    RadioGroup radioShop, radioWarranty;
    CheckBox chkExchange;
    EditText editTextExchange,edt_imei;
    TextInputLayout textInputExchange;
    LinearLayout layoutAccesother, layoutMobTab;
    Spinner Warranty_spinner, spinnerSeries, spinnerBrandMobile, condition_spinner;
    AutoCompleteTextView model_autocompleteTv;
    Button finalSubmitButton;
    ImageView imgBacktoMAin;
    String warrenty_month = "", productCategory = "", conditon_Mobile = "";
    String brand_id = "", brandName = "", series_id = "", seriesName = "", idmodel = "", modelName = "";
    ModelAdapter modelAdapter;
    Views views = new Views();
    ImageView scanNow;

    ArrayList<BrandSpinner> brand_list = new ArrayList<>();
    final ArrayList<String> brand_list_datamobile = new ArrayList();
    ArrayList<SeriesModel> series_list = new ArrayList<>();
    final ArrayList<String> series_list_dataSeries = new ArrayList();
    ArrayList<Model_Model> model_list = new ArrayList<>();

    String Warranty_data[] = {
            "0 - 1 month old",
            "1 - 3 month old",
            "3 - 6 month old",
            "6 - 9 month old",
            "9 - 12 month old",
    };

    String condition[] = {"Excellent", "Very Good", "Good", "Average"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        getSupportActionBar().hide();

        init();
        click();

    }

    private void click() {
        radioShop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton = radioGroup.findViewById(i);
                boolean checked = checkedRadioButton.isChecked();
                if (checked) {
                    if (checkedRadioButton.getText().toString().equals("Mobile")) {
                        layoutMobTab.setVisibility(View.VISIBLE);
                        layoutAccesother.setVisibility(View.GONE);
                        productCategory = "Mobile";
                        hitSpinnerBrandMobile();
                    }
                    if (checkedRadioButton.getText().toString().equals("Tablet")) {
                        layoutMobTab.setVisibility(View.VISIBLE);
                        layoutAccesother.setVisibility(View.GONE);
                        productCategory = "Tablet";
                        hitSpinnerBrandMobile();

                    }
                    if (checkedRadioButton.getText().toString().equals("Accessories")) {
                        layoutMobTab.setVisibility(View.GONE);
                        layoutAccesother.setVisibility(View.VISIBLE);
                        productCategory = "Accessories";
                    }
                }
            }
        });

        radioWarranty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton = radioGroup.findViewById(i);
                boolean checked = checkedRadioButton.isChecked();
                if (checked) {
                    if (checkedRadioButton.getText().toString().equals("In")) {
                        Warranty_spinner.setVisibility(View.VISIBLE);

                        ArrayAdapter ab = new ArrayAdapter(ShopActivity.this, android.R.layout.simple_spinner_dropdown_item, Warranty_data);
                        Warranty_spinner.setAdapter(ab);
                        Warranty_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {

                                warrenty_month = Warranty_data[position];

                                Log.d("fasfafas", warrenty_month);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                    }
                    if (checkedRadioButton.getText().toString().equals("Out")) {
                        Warranty_spinner.setVisibility(View.GONE);
                        warrenty_month = "";
                    }

                }
            }
        });

        chkExchange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                b = chkExchange.isChecked();
                if (b) {
                    textInputExchange.setVisibility(View.VISIBLE);
                } else {
                    textInputExchange.setVisibility(View.GONE);
                }

            }
        });
        imgBacktoMAin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finishAffinity();
            }
        });

        finalSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShopImageActivity.class));
            }
        });


        spinnerBrandMobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //int position = adapterView.getSelectedItemPosition();
                brand_id = String.valueOf(brand_list.get(i).getBrandId());
                brandName = brand_list.get(i).getBrand_name();

                Log.d("fsdklj", brand_id + brandName);

                hitSpinnerSeries(brand_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerSeries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int position = adapterView.getSelectedItemPosition();
                series_id = String.valueOf(series_list.get(position).getSeries_id());
                seriesName = series_list.get(i).getSeries_name();
                hitModelApi(series_id);
                Log.d("sepo", seriesName + " " + series_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        model_autocompleteTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Model_Model modelObject = (Model_Model) parent.getItemAtPosition(position);
                modelName = modelObject.getModelName();
                idmodel = modelObject.getModel_id();
            }
        });

        condition_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                conditon_Mobile = condition[position];
                Log.d("sasfzczZ", conditon_Mobile);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        scanNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScanFragment scanFragment = new ScanFragment();
                fragmentTransaction.add(R.id.scan_fragment,scanFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void init() {
        radioShop = findViewById(R.id.radioShop);
        radioWarranty = findViewById(R.id.radioWarranty);
        layoutAccesother = findViewById(R.id.layoutAccesother);
        layoutMobTab = findViewById(R.id.layoutMobTab);
        Warranty_spinner = findViewById(R.id.Warranty_spinner);
        finalSubmitButton = findViewById(R.id.finalSubmitButton);
        chkExchange = findViewById(R.id.chkExchange);
        textInputExchange = findViewById(R.id.textInputExchange);
        editTextExchange = findViewById(R.id.editTextExchange);
        edt_imei = findViewById(R.id.edt_imei);
        imgBacktoMAin = findViewById(R.id.imgBacktoMAin);
        spinnerBrandMobile = findViewById(R.id.spinnerBrandMobile);
        spinnerSeries = findViewById(R.id.spinnerSeries);
        scanNow = findViewById(R.id.scanNow);
        model_autocompleteTv = findViewById(R.id.model_autocompleteTv);
        model_autocompleteTv.setThreshold(1);


        //////////////////// condtion spinner //////////////////
        condition_spinner = findViewById(R.id.condition_spinner);
        ArrayAdapter cndnAdapter = new ArrayAdapter(ShopActivity.this, android.R.layout.simple_spinner_dropdown_item, condition);
        condition_spinner.setAdapter(cndnAdapter);
    }


    //////////////////////////////////////////Brand Spinner /////////////////////////////////////

    private void hitSpinnerBrandMobile() {
        views.showProgress(ShopActivity.this);

        RequestQueue requestQueueMobile = Volley.newRequestQueue(ShopActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, Url.getBrand, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Loginresponse", response);
                views.hideProgress();

                brand_list.clear();
                brand_list_datamobile.clear();
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("code").equals("200")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            BrandSpinner brandSpinner = new BrandSpinner();
                            brandSpinner.setBrandId(jsonObject1.getInt("id"));
                            brandSpinner.setBrand_name(jsonObject1.getString("brand_name"));
                            brand_list.add(brandSpinner);
                            brand_list_datamobile.add(brand_list.get(i).getBrand_name());
                        }

                        Log.d("jhkljk", brand_list_datamobile.toString());

                        ArrayAdapter brandAdapter = new ArrayAdapter(ShopActivity.this, android.R.layout.simple_spinner_dropdown_item, brand_list_datamobile);
                        spinnerBrandMobile.setAdapter(brandAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                views.hideProgress();

                Toast.makeText(ShopActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                Log.d("errodfr", error.getMessage() + "errorr");

            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Url.key);
                hashMap.put("type", "Mobile");
                return hashMap;
            }
        };
        requestQueueMobile.getCache().clear();
        requestQueueMobile.add(request);
    }

    ////////////////////////////////////////// Series Spinner //////////////////////////////////

    private void hitSpinnerSeries(final String brand) {

        views.showProgress(ShopActivity.this);
        series_list_dataSeries.clear();
        series_list.clear();

        RequestQueue requestQueueMobile = Volley.newRequestQueue(ShopActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, Url.getseries, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Logdfgonse", response);
                views.hideProgress();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("code").equals("200")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            SeriesModel seriesModel = new SeriesModel();
                            seriesModel.setSeries_id(jsonObject1.getString("id"));
                            seriesModel.setSeries_name(jsonObject1.getString("series_name"));
                            series_list.add(seriesModel);
                            series_list_dataSeries.add(series_list.get(i).getSeries_name());
                        }

                        Log.d("jhkljasdk", series_list_dataSeries.toString());

                        ArrayAdapter brandAdapter = new ArrayAdapter(ShopActivity.this, android.R.layout.simple_spinner_dropdown_item, series_list_dataSeries);
                        spinnerSeries.setAdapter(brandAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                views.hideProgress();
                Toast.makeText(ShopActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                Log.d("errodfr", error.getMessage() + "errorr");

            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Url.key);
                hashMap.put("brand_id", brand);
                Log.d("brandidd", brand_id);

                return hashMap;

            }

        };
        requestQueueMobile.getCache().clear();
        requestQueueMobile.add(request);
    }

    /////////////////////////////////////////// Model Spinner //////////////////////////////////

    void hitModelApi(final String series) {
        views.showProgress(ShopActivity.this);

        RequestQueue requestQueue = Volley.newRequestQueue(ShopActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, Url.getModel, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response_model", response);
                views.hideProgress();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("code").equals("200")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        // model_list_dataModel.clear();
                        model_list.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Model_Model model = new Model_Model();
                            model.setModel_id(jsonObject1.getString("id"));
                            model.setModelName(jsonObject1.getString("model_name"));
                            model_list.add(model);


                        }

                        modelAdapter = new ModelAdapter(getApplicationContext(), R.layout.activity_shop,
                                R.id.lbl_name, model_list);
                        model_autocompleteTv.setAdapter(modelAdapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                views.hideProgress();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("key", Url.key);
                hashMap.put("series_id", series);
                return hashMap;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(request);


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void scanResultData(String codeFormat, String codeContent) {
        edt_imei.setText(codeContent);
    }

    @Override
    public void scanResultData(NoScanResultException noScanData) {

    }
}
