package apps.kaltengguide.rofiqoff.com.kaltengguide.fragmentspinner.lamnadau.fragmenttab.wisata.wisatabelanja;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import apps.kaltengguide.rofiqoff.com.kaltengguide.R;
import apps.kaltengguide.rofiqoff.com.kaltengguide.database.Helper;
import apps.kaltengguide.rofiqoff.com.kaltengguide.fragmentspinner.lamnadau.fragmenttab.wisata.wisatabelanja.data.ItemWisataBelanja;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class WisataBelanjaFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private ArrayList<ItemWisataBelanja> itemWisataBelanjas;

    private RecyclerView recyclerView;
    private MyWisataBelanjaRecyclerViewAdapter recyclerViewAdapter;

    private AQuery aQuery;

    private Paint paint = new Paint();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WisataBelanjaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wisatabelanja_list, container, false);

        // Set the adapter
        itemWisataBelanjas = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_wisatabelanja_barito);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        setData();
        return view;
    }

    private void setData() {
        String URL = Helper.BASE_URL + "tampil-data-wisata-belanja-lamandau.php";
        Map<String, String> param = new HashMap<>();

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setInverseBackgroundForced(false);
        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setTitle("Info");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading . . .");
        progressDialog.show();

        aQuery = new AQuery(getContext());
        try {
            aQuery.progress(progressDialog).ajax(URL, param, String.class, new AjaxCallback<String>() {
                @Override
                public void callback(String url, String object, AjaxStatus status) {

                    if (object != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(object);
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
//                                Helper.pesan(getContext(), msg);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int a = 0; a < jsonArray.length(); a++) {
                                    JSONObject object1 = jsonArray.getJSONObject(a);
                                    ItemWisataBelanja wisataBelanja = new ItemWisataBelanja();

                                    wisataBelanja.setNamaWisataBelanja(object1.getString("Nama"));
                                    wisataBelanja.setAlamatWisataBelanja(object1.getString("Alamat"));
                                    wisataBelanja.setGambarWisataBelanja(object1.getString("Gambar"));
                                    wisataBelanja.setDeskripsiWisataBelanja(object1.getString("Deskripsi"));
                                    wisataBelanja.setVideoWisataBelanja(object1.getString("Video"));
                                    wisataBelanja.setLatWisataBelanja(object1.getString("Lat"));
                                    wisataBelanja.setLangWisataBelanja(object1.getString("Lang"));
                                    wisataBelanja.setKategoriWisataBelanja(object1.getString("Kategori"));
                                    wisataBelanja.setWebsite(object1.getString("Website"));

                                    itemWisataBelanjas.add(wisataBelanja);
                                    recyclerViewAdapter = new MyWisataBelanjaRecyclerViewAdapter(getActivity(), itemWisataBelanjas);
                                    recyclerView.setAdapter(recyclerViewAdapter);
                                }
                            } else {
                                Helper.pesan(getContext(), msg);
                            }
                        } catch (JSONException e) {
//                            Helper.pesan(getContext(), "Data belum ada");
                        }
                    }
                }
            });

        } catch (Exception e) {
            Helper.pesan(getContext(), "Gagal mengembil data");
        }
    }
}
