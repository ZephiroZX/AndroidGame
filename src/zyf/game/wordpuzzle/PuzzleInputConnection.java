package zyf.game.wordpuzzle;

import android.view.View;
import android.view.inputmethod.BaseInputConnection;

public class PuzzleInputConnection extends BaseInputConnection {

	private PuzzleView puzzle_view = null;
	
	public PuzzleInputConnection(View targetView, boolean fullEditor) {
		super(targetView, fullEditor);
		puzzle_view = (PuzzleView) targetView;
	}

	@Override
	public boolean commitText(CharSequence text, int newCursorPosition) {
		puzzle_view.getInputContent((String) text);
		return super.commitText(text, newCursorPosition);
	}
	
}
