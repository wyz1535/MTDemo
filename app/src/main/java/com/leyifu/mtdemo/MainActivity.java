
package com.leyifu.mtdemo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";
    private LinearLayout ll_mei_food;
    private ImageView iv_down;
    private LinearLayout ll_line;
    private ListView lv_first;
    private ListView lv_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_mei_food = ((LinearLayout) findViewById(R.id.ll_mei_food));
        iv_down = ((ImageView) findViewById(R.id.iv_down));
        ll_line = ((LinearLayout) findViewById(R.id.ll_line));

        ll_mei_food.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        //必须返回true 否则menu将不显示
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Toast.makeText(MainActivity.this, "add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove:
                Toast.makeText(MainActivity.this, "remove", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_mei_food:
                iv_down.setImageResource(R.drawable.up);
                Log.e(TAG, "onClick: ");
                View inflate = View.inflate(MainActivity.this, R.layout.activity_pop, null);

                lv_first = ((ListView) inflate.findViewById(R.id.lv_first));
                lv_second = ((ListView) inflate.findViewById(R.id.lv_second));

                PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, 1000);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                backgroundAlpha(0.5f);
                popupWindow.showAsDropDown(ll_line);

                final ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, initData());
                lv_first.setAdapter(adapter);

                lv_first.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        lv_second.setAdapter(adapter);
                    }
                });

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        iv_down.setImageResource(R.drawable.down);
                        backgroundAlpha(1.0f);
                    }
                });

                break;
            default:
                break;
        }
    }

    private List<String> initData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            data.add("火锅 " + i);
        }
        return data;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }
}
