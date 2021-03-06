package apps.kaltengguide.rofiqoff.com.kaltengguide.fragmentspinner.gunungmas.fragmenttab.wisata.wisatakuliner;

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
import apps.kaltengguide.rofiqoff.com.kaltengguide.fragmentspinner.gunungmas.fragmenttab.wisata.wisatakuliner.data.ItemWisataKuliner;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class WisataKulinerFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private ArrayList<ItemWisataKuliner> itemWisataKuliners;

    private RecyclerView recyclerView;
    private MyWisataKulinerRecyclerViewAdapter recyclerViewAdapter;

    private AQuery aQuery;

    private Paint paint = new Paint();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WisataKulinerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wisatakuliner_list, container, false);

        itemWisataKuliners = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_wisatakuliner_barito);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        setData();
        return view;
    }

    private void setData() {
        String URL = Helper.BASE_URL + "tampil-data-wisata-budaya-gunung-mas.php";
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
                                    ItemWisataKuliner wisataKuliner = new ItemWisataKuliner();

                                    wisataKuliner.setNamaWisataKuliner(object1.getString("Nama"));
                                    wisataKuliner.setAlamatWisataKuliner(object1.getString("Alamat"));
                                    wisataKuliner.setGambarWisataKuliner(object1.getString("Gambar"));
                                    wisataKuliner.setDeskripsiWisataKuliner(object1.getString("Deskripsi"));
                                    wisataKuliner.setVideoWisataKuliner(object1.getString("Video"));
                                    wisataKuliner.setLatWisataKuliner(object1.getString("Lat"));
                                    wisataKuliner.setLangWisataKuliner(object1.getString("Lang"));
                                    wisataKuliner.setKategoriWisataKuliner(object1.getString("Kategori"));
                                    wisataKuliner.setWebsite(object1.getString("Website"));

                                    itemWisataKuliners.add(wisataKuliner);
                                    recyclerViewAdapter = new MyWisataKulinerRecyclerViewAdapter(getActivity(), itemWisataKuliners);
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
