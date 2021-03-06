package edu.gatech.seclass.sdpguessit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.gatech.seclass.sdpguessit.data.managers.PlayerManager;
import edu.gatech.seclass.sdpguessit.data.models.Player;

public class LoginActivity extends AppCompatActivity {
    @Inject PlayerManager playerManager;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.username) EditText username;

    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GuessItApplication.component(this).inject(this);

        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.login)
    void login() {
        String userName = username.getText().toString();
        Player player = playerManager.getPlayerByUsername(userName);

        if (player != null) {
            playerManager.setCurrentLoggedInPlayer(player);
            startActivity(MainActivity.newIntent(this));
            finish();
        } else {
            this.username.setError("User Doesn't Exist.");
        }
    }

    @OnClick(R.id.create_player)
    void CreatePlayer() {
        startActivity(CreatePlayerActivity.newIntent(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);

        // Add extras if needed

        return intent;
    }
}
