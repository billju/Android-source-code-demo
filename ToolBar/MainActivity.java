package com.example.chuboy.toolbar;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    List<Map<String, Object>> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mDrawerList = (ListView)findViewById(R.id.navigation_list);
        mList = new ArrayList<Map<String,Object>>();
        String[] listFromResource = getResources().getStringArray(R.array.items);
        for(int i=0;i<listFromResource.length;i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("imageView",R.mipmap.ic_item);
            item.put("txtView",listFromResource[i]);
            mList.add(item);
        }
        String [] from = {"imageView","txtView"};
        int[] to = {R.id.image, R.id.txt};
        SimpleAdapter adapter = new SimpleAdapter(this, mList, R.layout.list_layout, from, to);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Navigation Item" + position ,Toast.LENGTH_SHORT).show();
            }
        });

        //要製作icon的話到res右鍵點取image asset，然後將shape調成none
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_home);
        toolbar.setNavigationOnClickListener(new Toolbar.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Title");
        toolbar.setSubtitle("Subtitle");
        toolbar.inflateMenu((R.menu.menu_main));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                int menuItemId = item.getItemId();
                if(menuItemId == R.id.action_search){
                    Toast.makeText(MainActivity.this,"Search",Toast.LENGTH_SHORT).show();
                }else if(menuItemId == R.id.action_notification){
                    Toast.makeText(MainActivity.this,"Notification",Toast.LENGTH_SHORT).show();
                }else if(menuItemId == R.id.action_item1){
                    Toast.makeText(MainActivity.this,"item1",Toast.LENGTH_SHORT).show();
                }else if(menuItemId == R.id.action_item2){
                    Toast.makeText(MainActivity.this,"item2",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
}
