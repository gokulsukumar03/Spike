package com.shortcut.spike;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.Objects;
import java.util.Random;


@RequiresApi(api = Build.VERSION_CODES.M)
public class PinnedShortcutActivity extends AppCompatActivity implements View.OnClickListener {

    private Button createPinnedShortcutBtn;
    ShortcutManager shortcutManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned_shortcut);
        init();
        initPinnedShortCut();
        getShortCutManagerOldData();
    }

    private void init() {
        createPinnedShortcutBtn = findViewById(R.id.createPinnedShortcutBtn);
        createPinnedShortcutBtn.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createPinnedShortcutBtn:
                createPinnedShortcut();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initPinnedShortCut() {
        shortcutManager = getSystemService(ShortcutManager.class);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createPinnedShortcut() {
        String id = String.valueOf(new Random().nextInt());
        assert shortcutManager != null;
        if (shortcutManager.isRequestPinShortcutSupported()) {
            // Assumes there's already a shortcut with the ID "my-shortcut".
            // The shortcut must be enabled.
            ShortcutInfo pinShortcutInfo = new ShortcutInfo.Builder(PinnedShortcutActivity.this, id)
                    .setShortLabel("pinned short label")
                    .setLongLabel("long label")
                    .setIntent(new Intent(PinnedShortcutActivity.this, PinnedShortcutActivity.class).putExtra("ID", id)
                            .setAction("PINNED"))
                    .setIcon(Icon.createWithResource(PinnedShortcutActivity.this, R.mipmap.ic_launcher))
                    .build();
            Intent pinnedShortcutCallbackIntent = shortcutManager.createShortcutResultIntent(pinShortcutInfo);
            PendingIntent successCallback = PendingIntent.getBroadcast(PinnedShortcutActivity.this, /* request code */ 0,
                    pinnedShortcutCallbackIntent, /* flags */ 0);

            shortcutManager.requestPinShortcut(pinShortcutInfo, successCallback.getIntentSender());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private List<ShortcutInfo> getShortCutManagerOldData() {
        assert shortcutManager != null;
        List<ShortcutInfo> sList = shortcutManager.getPinnedShortcuts();
        if (!sList.isEmpty()) {
            for (int i = 0; i < sList.size(); i++) {
                Log.d("!!!! pinned", Objects.requireNonNull(sList.get(i).getShortLabel()).toString());
                Log.d("!!!! pinned", Objects.requireNonNull(sList.get(i).getLongLabel()).toString());
                Log.d("!!!! pinned", Objects.requireNonNull(Objects.requireNonNull(sList.get(i).getIntent()).getStringExtra("ID")));
                Log.d("!!!! pinned", Objects.requireNonNull(sList.get(i).getId()));
            }
        }
        return sList;
    }

    private void deletePinnedShortcut() {

    }

    private void deleteAllShortcut() {

    }

    private void deleteMarkedShortcut() {

    }


}
