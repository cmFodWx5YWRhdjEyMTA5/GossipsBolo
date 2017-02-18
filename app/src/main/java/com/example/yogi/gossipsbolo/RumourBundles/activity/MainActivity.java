package com.example.yogi.gossipsbolo.RumourBundles.activity;


import android.content.Context;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.yogi.gossipsbolo.BaseActivity;
import com.example.yogi.gossipsbolo.R;
import com.example.yogi.gossipsbolo.RumourBundles.fragment.AudioVideoCallsFragment;
import com.example.yogi.gossipsbolo.RumourBundles.fragment.GossipsContactsFragment;
import com.example.yogi.gossipsbolo.RumourBundles.fragment.RecentGossipsFragment;

//import static com.example.yogi.gossipsbolo.R.id.featuresLogo;
//import static com.example.yogi.gossipsbolo.R.mipmap.ic_launcher;

public class MainActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener{

    private ListView listView;
    private DrawerLayout drawerLayout;

    private MyDrawerCustomAdapter myDrawerCustomAdapter;

    ViewPager viewPager = null;
    TabLayout tabLayout;

    private ActionBarDrawerToggle drawerListener;
    private static  String POSITION = "POSITION";
    private String[] extras;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.rumourbundle_toolbar);
        setSupportActionBar(toolbar);


               //   Swipe TabLayout
        viewPager = (ViewPager) findViewById(R.id.pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new MyAdatper(fragmentManager));

        tabLayout = (TabLayout) findViewById(R.id.swipeTabs);
        tabLayout.setupWithViewPager(viewPager);


        //DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.rumourbundle_DrawerLayout);
        listView = (ListView) findViewById(R.id.rumourbundle_ListView);

        myDrawerCustomAdapter = new MyDrawerCustomAdapter(this);
        listView.setAdapter(myDrawerCustomAdapter);
          listView.setOnItemClickListener(this);


           //drawerListener = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_closed,R.string.drawer_opened)
        drawerListener = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_closed,R.string.drawer_opened)
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                //Toast.makeText(MainActivity.this,"R.string.drawer_closed",Toast.LENGTH_SHORT).show();
            }

            @Override
             public void onDrawerOpened(View drawerView) {
                //Toast.makeText(MainActivity.this,"R.string.drawer_opened",Toast.LENGTH_SHORT).show();
            }
        };


        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        //<!---------onOptionSItemSelected is called when we click at HomeUpButton(open,close)
        if(drawerListener.onOptionsItemSelected(menuItem))
        {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    //DrawerList ListItem onClick
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        selectItem(i);

    }

    public void selectItem(int position)

    {
        Toast.makeText(this,extras[position] + "was selected",Toast.LENGTH_SHORT).show();
        listView.setItemChecked(position,true);

        setTitle(extras[position]);

    }
    public void setTitle(String title)
    {
      getSupportActionBar().setTitle(title);
    }
    //DrawerList ListItem onClick//

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(POSITION,tabLayout.getSelectedTabPosition());
    }




    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }


}



/**
 * Created by Yogi on 08-01-2017.
 */
class MyAdatper extends FragmentStatePagerAdapter {


    public MyAdatper(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        if(position == 0)
        {
            fragment = new AudioVideoCallsFragment();
        }
        else if(position == 1)
        {
            fragment = new RecentGossipsFragment();
        }
        else if(position == 2)
        {
            fragment = new GossipsContactsFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch(position)
        {
            case 0:
                return "calls";
            case 1:
                return "Gossips";
            case 2:
                return "Contacts";
            default:
                return null;

        }

    }


}





class MyDrawerCustomAdapter extends BaseAdapter {

    //private ImageView featuresLogo;
    //private TextView featuresName;
    //private ListView listView;
    String extras[];
    private Context context;
    int[] extrasImages ={R.drawable.ic_action_account_plus,R.drawable.ic_action_account_plus,R.drawable.ic_action_account_plus,R.drawable.ic_action_account_plus,R.drawable.ic_action_account_plus,R.drawable.ic_action_account_plus};


    public MyDrawerCustomAdapter(Context context)
    {
        this.context =context;
        extras = context.getResources().getStringArray(R.array.extraFeatures);
    }

    @Override
    public int getCount() {

        return extras.length;

    }

    @Override
    public Object getItem(int i) {

        return extras[i];

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        View row = null;

        if(convertview == null)
        {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.customrow_drawer,viewGroup,false);

        }
        else
        {
            row=convertview;
        }
       TextView featuresName = (TextView) row.findViewById(R.id.featuresName);

        ImageView featuresImages = (ImageView) row.findViewById(R.id.featuresLogo);

        featuresName.setText(extras[i]);
      featuresImages.setImageResource(extrasImages[i]);


        return row;
    }



}
