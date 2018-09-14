package zyf.game.wordpuzzle;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class WordPuzzle extends TabActivity{
	
	//private static final String TAG = "WordPuzzle";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        
        final TabHost tab_host = getTabHost();
        View view_game = LayoutInflater.from(this).inflate(R.layout.tab_label, null);
        ImageView img_game = (ImageView) view_game.findViewById(R.id.img);
        img_game.setImageResource(R.drawable.ic_launcher);
        TextView text_game = (TextView) view_game.findViewById(R.id.text);
        text_game.setText(R.string.game_label);
        TabHost.TabSpec tab_game = tab_host.newTabSpec("game");
        tab_game.setIndicator(view_game);
        tab_game.setContent(new Intent(WordPuzzle.this, PuzzleList.class));
        tab_host.addTab(tab_game);
        
        View view_setting = LayoutInflater.from(this).inflate(R.layout.tab_label, null);
        ImageView img_setting = (ImageView) view_setting.findViewById(R.id.img);
        img_setting.setImageResource(R.drawable.ic_launcher);
        TextView text_setting = (TextView) view_setting.findViewById(R.id.text);
        text_setting.setText(R.string.setting_label);
        TabHost.TabSpec tab_setting = tab_host.newTabSpec("setting");
        tab_setting.setIndicator(view_setting);
        tab_setting.setContent(new Intent(WordPuzzle.this, SettingActivity.class));
        tab_host.addTab(tab_setting);
        
        View view_feedback = LayoutInflater.from(this).inflate(R.layout.tab_label, null);
        ImageView img_feedback = (ImageView) view_feedback.findViewById(R.id.img);
        img_feedback.setImageResource(R.drawable.ic_launcher);
        TextView text_feedback = (TextView) view_feedback.findViewById(R.id.text);
        text_feedback.setText(R.string.feedback_label);
        TabHost.TabSpec tab_feedback = tab_host.newTabSpec("feedback");
        tab_feedback.setIndicator(view_feedback);
        tab_feedback.setContent(new Intent(WordPuzzle.this, Feedback.class));
        tab_host.addTab(tab_feedback);
    }
}