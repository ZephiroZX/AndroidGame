package zyf.game.wordpuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Feedback extends Activity implements OnClickListener{

	EditText feedback_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		feedback_text = (EditText) this.findViewById(R.id.feedback_content);
		Button send_btn = (Button) this.findViewById(R.id.feedback_submit);
		send_btn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent mail = new Intent(Intent.ACTION_SEND);
		mail.setType("message/rfc822");
		mail.putExtra(Intent.EXTRA_EMAIL, new String[]{"nankaihunter@gmail.com"});
		mail.putExtra(Intent.EXTRA_SUBJECT, "[填词游戏]建议");
		mail.putExtra(Intent.EXTRA_TEXT, feedback_text.getText().toString());
		this.startActivity(Intent.createChooser(mail, "Sending..."));
	}
}
