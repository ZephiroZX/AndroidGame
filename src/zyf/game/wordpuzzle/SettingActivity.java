package zyf.game.wordpuzzle;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingActivity extends PreferenceActivity {
	
	private static final String OPT_HINTS = "hints";
	private static final boolean OPT_HINTS_DEF = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.layout.setting);
	}
	
	public static boolean getHints(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(OPT_HINTS, OPT_HINTS_DEF);
	}
}
