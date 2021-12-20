package com.act.r1_demo;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


public class MainActivity extends AppCompatActivity {

    FragmentTabHost mTabHost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Application.mainView = this;
        //Application.hexkbd = new HexKeyboard(this, R.id.keyboard_view, R.xml.hexkbd);
        initTabHost();
        setStatus(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.connect) {
            //startActivityForResult(new Intent(MainActivity.this, ConnectActivity.class), 1);
            ConnDialog dlg = new ConnDialog(this);
            dlg.setCloseListener( new ConnDialog.OnCloseListener() {
                @Override
                public void onClose(boolean isOk) {
                    if (isOk)
                        setStatus(true);
                }
            });

            dlg.show();
        } else if (id == R.id.disconnect) {
            try {
                Application.cr.disconnect();
                setStatus(false);
            } catch (Exception e) {
                Application.showError(e.getMessage(), MainActivity.this);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void initTabHost() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        Resources resources = getResources();
        addTab(mTabHost, resources.getString(R.string.base), BaseFragment.class);
        /*addTab(mTabHost, resources.getString(R.string.mf1_s50), Mf1Fragment.class);
        addTab(mTabHost, resources.getString(R.string.mf1_s70), Mf1Fragment.class);
        addTab(mTabHost, resources.getString(R.string.mf0), Mf0Fragment.class);
        addTab(mTabHost, resources.getString(R.string.cpu), CpuFragment.class);
        */

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Fragment frag = getSupportFragmentManager().findFragmentByTag(tabId);
                if (frag instanceof Mf1Fragment) {
                    boolean isS50 = tabId.equalsIgnoreCase(getResources().getString(R.string.mf1_s50));
                    ((Mf1Fragment) frag).mIsS50 = isS50;
                }
            }
        });
    }

    private void addTab(FragmentTabHost tabHost, String tag, Class<?> clss) {
        FragmentTabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(tag);
        tabHost.addTab(tabSpec, clss, null);
    }

    private void setStatus(boolean isConnected) {
        String title = getString(R.string.app_name);
        if (isConnected) {
            String status = getString(R.string.connected);
            title += "[" + status + "]";
        }
        setTitle(title);
    }
}
