package apps.kaltengguide.rofiqoff.com.kaltengguide.fragmentspinner.kualakapuas;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import apps.kaltengguide.rofiqoff.com.kaltengguide.fragmentspinner.kualakapuas.fragmenttab.map.MapFragment;
import apps.kaltengguide.rofiqoff.com.kaltengguide.fragmentspinner.kualakapuas.fragmenttab.penginapan.PenginapanActivity;
import apps.kaltengguide.rofiqoff.com.kaltengguide.fragmentspinner.kualakapuas.fragmenttab.travel.view.data.TravelFragment;
import apps.kaltengguide.rofiqoff.com.kaltengguide.fragmentspinner.kualakapuas.fragmenttab.wisata.BottomBarBaritoActivity;

/**
 * Created by rofiqoff on 4/25/17.
 */

public class SectionsPagerAdapterKualaKapuas extends FragmentPagerAdapter {
    String[] tittle = new String[]{
            "Wisata", "Penginapan", "Travel", "Map"
    };

    public SectionsPagerAdapterKualaKapuas(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new BottomBarBaritoActivity();
                break;
            case 1:
                fragment = new PenginapanActivity();
                break;
            case 2:
                fragment = new TravelFragment();
                break;
            case 3:
                fragment = new MapFragment();
            default:
                return fragment;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return tittle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tittle[position];
    }
}
