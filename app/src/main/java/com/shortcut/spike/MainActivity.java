package com.shortcut.spike;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.N_MR1)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button createBtn, deleteAllBtn, pinnedShortcutBtn;
    ShortcutManager shortcutManager;
    ShortcutInfo shortcut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createBtn = findViewById(R.id.createBtn);
        deleteAllBtn = findViewById(R.id.deleteAllBtn);
        pinnedShortcutBtn = findViewById(R.id.pinnedShortcutBtn);
        createBtn.setOnClickListener(this);
        deleteAllBtn.setOnClickListener(this);
        pinnedShortcutBtn.setOnClickListener(this);

        if (null != getIntent() && getIntent().hasExtra("ID")) {
            String test = getIntent().getStringExtra("ID");
            Toast.makeText(MainActivity.this, test, Toast.LENGTH_LONG).show();
        }

        getShortCutManagerOldData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createBtn:
                setShortCut(MainActivity.this);
                break;

            case R.id.deleteAllBtn:
                deleteShortCut();
                break;

            case R.id.pinnedShortcutBtn:
                startActivity(new Intent(MainActivity.this, PinnedShortcutActivity.class));
                break;
        }
    }

    private void setShortCut(Context context) {
        int id = new Random().nextInt();
        assert shortcutManager != null;
        shortcutManager = getSystemService(ShortcutManager.class);
        shortcut = new ShortcutInfo.Builder(context, "id" + id)
                .setShortLabel("SC " + id)
                .setLongLabel("ShortCut Test Example")
                .setIcon(Icon.createWithResource(context, R.mipmap.ic_launcher))
                .setIntent(new Intent(Intent.ACTION_VIEW, null, MainActivity.this, MainActivity.class)
                        .putExtra("ID", "id" + id))
                .build();
        shortcutManager.addDynamicShortcuts(Collections.singletonList(shortcut));
    }

    private List<ShortcutInfo> getShortCutManagerOldData() {
        shortcutManager = getSystemService(ShortcutManager.class);
        assert shortcutManager != null;
        List<ShortcutInfo> sList = shortcutManager.getDynamicShortcuts();
        if (!sList.isEmpty()) {
            for (int i = 0; i < sList.size(); i++) {
                Log.d("!!!! list", Objects.requireNonNull(sList.get(i).getShortLabel()).toString());
                Log.d("!!!! list", Objects.requireNonNull(sList.get(i).getLongLabel()).toString());
                Log.d("!!!! list", Objects.requireNonNull(Objects.requireNonNull(sList.get(i).getIntent()).getStringExtra("ID")));
                Log.d("!!!! list", Objects.requireNonNull(sList.get(i).getId()));
            }
        }
        return sList;
    }

    private void deleteShortCut() {
        List<ShortcutInfo> sList = shortcutManager.getDynamicShortcuts();
        List<String> shortcutIds = new ArrayList<>();
        shortcutIds.add(sList.get(0).getId());
        shortcutManager.removeDynamicShortcuts(shortcutIds);
    }

    private void removeAllShortCut() {
        shortcutManager.removeAllDynamicShortcuts();
    }

}
