package zyf.game.wordpuzzle;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

public class PuzzleView extends View {

	//private static final String TAG = "PuzzleView";
	private static final int ID = 42;
	private final PuzzleActivity puzzle;
	
	private final int puzzle_size;
	private float side;
    private final Rect selRect = new Rect();
	private int selX = -1;
	private int selY = -1;
	private boolean sel_latitude; // true -> x, false->y
	InputMethodManager input = null; 
	public PuzzleView(Context context) {
		super(context);
		this.puzzle = (PuzzleActivity) context;
		this.puzzle_size = puzzle.getPuzzleSize();
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		this.setId(ID);
		input = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		side = w < h ? w/(float)puzzle_size : h/(float)puzzle_size;
		getRect(selX, selY, selRect);
		super.onSizeChanged(w, h, oldw, oldh);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		
		int view_height = this.getHeight();
		int view_width = this.getWidth();
		int view_side = view_height < view_width ? view_height : view_width;
		
		// draw the board
		Paint background = new Paint();
		background.setColor(this.getResources().getColor(R.color.background));
		canvas.drawRect(0, 0, view_width, view_height, background);
		
		Paint dark = new Paint();
		dark.setColor(this.getResources().getColor(R.color.dark));
		
		Paint light = new Paint();
		light.setColor(this.getResources().getColor(R.color.light));
		
		Paint error = new Paint();
		error.setColor(this.getResources().getColor(R.color.error));
		
		int puzzle_size = puzzle.getPuzzleSize();
		for (int i = 0; i <= puzzle_size; i++) {
			canvas.drawLine(0, i*side, view_side, i*side, i==0 || i==puzzle_size ? dark : light);
			canvas.drawLine(i*side, 0, i*side, view_side, i==0 || i==puzzle_size ? dark : light);
		}
		
		dark.setTextSize(side * 0.70f);
		dark.setTextScaleX(1);
		dark.setTextAlign(Paint.Align.CENTER);
		dark.setStyle(Style.FILL);
		
		FontMetrics fm = dark.getFontMetrics();
		float x = side / 2;
		float y = side / 2 - (fm.ascent + fm.descent) / 2;
		for (int i = 0; i < puzzle_size; i++) {
			for (int j = 0; j < puzzle_size; j++) {
				char tile = this.puzzle.getTile(i, j);
				if (tile == '*') {
					canvas.drawRect(i*side+1, j*side+1, (i+1)*side-1, (j+1)*side-1, dark);
				} else {
					canvas.drawRect(i*side+1, j*side+1, (i+1)*side-1, (j+1)*side-1, puzzle.getTileFlag(i, j) == '+' ? error : background);
					canvas.drawText(String.valueOf(tile), i * side + x, j * side + y, dark);
				}
			}
		}
		
		// draw the selection
		if (selX >= 0 && selX < puzzle_size && selY >= 0 && selY < puzzle_size) {
			Paint horizontal = new Paint();
			horizontal.setColor(this.getResources().getColor(R.color.horizontal_selected));
			for (int i = selX-1; i >= 0; --i) {
				if (this.puzzle.isValidTile(i, selY)) {
					canvas.drawRect(i*side+1, selY*side+1, (i+1)*side-1, (selY+1)*side-1, horizontal);
				} else {
					break;
				}
			}
			for (int i = selX+1; i < puzzle_size; ++i) {
				if (this.puzzle.isValidTile(i, selY)) {
					canvas.drawRect(i*side+1, selY*side+1, (i+1)*side-1, (selY+1)*side-1, horizontal);
				} else {
					break;
				}
			}
			
			Paint vertical = new Paint();
			vertical.setColor(this.getResources().getColor(R.color.vertical_selected));
			for (int j = selY-1; j >= 0; --j) {
				if (this.puzzle.isValidTile(selX, j)) {
					canvas.drawRect(selX*side+1, j*side+1, (selX+1)*side-1, (j+1)*side-1, vertical);
				} else {
					break;
				}
			}
			for (int j = selY+1; j < puzzle_size; ++j) {
				if (this.puzzle.isValidTile(selX, j)) {
					canvas.drawRect(selX*side+1, j*side+1, (selX+1)*side-1, (j+1)*side-1, vertical);
				}
				else {
					break;
				}
			}
		}
	}
	
	public void getRect(int x, int y, Rect rect) {
		int top, left, bottom, right;
		if (x >= 0 && x < puzzle_size && y >= 0 && y < puzzle_size) {
			for (top = y; top >= 0; --top) {
				if (!puzzle.isValidTile(x, top) || top == 0)
					break;
			}
			for (bottom = y+1; bottom < puzzle_size; ++bottom) {
				if (!puzzle.isValidTile(x, bottom) || bottom == puzzle_size - 1)
					break;
			}
			for (left = x; left >= 0; --left) {
				if (!puzzle.isValidTile(left, y) || left == 0)
					break;
			}
			for (right = x+1; right < puzzle_size; ++right) {
				if (!puzzle.isValidTile(right, y) || right == puzzle_size - 1)
					break;
			}
			rect.set((int)(left*side), (int)(top*side), (int)(right*side+side), (int)(bottom*side+side));
		}
	}
	
	public void select(int x, int y) {
		invalidate(selRect);
		selX = x;
		selY = y;
		sel_latitude = puzzle.getLatitude(x, y);
		puzzle.setWordDesc(x, y);
		getRect(selX, selY, selRect);
		invalidate(selRect);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return super.onTouchEvent(event);
		puzzle.status_check();
		int x = (int)(event.getX()/side);
		int y = (int)(event.getY()/side);
		if (this.puzzle.isValidTile(x, y)) {
			select(x, y);
			input.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}
		return true;
	}
	
	public void getInputContent(String content) {
		puzzle.setTileContent(content, selX, selY, sel_latitude);
		input.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		invalidate();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DEL) {
			puzzle.setTileContent(" ", selX, selY, sel_latitude);
			invalidate();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
		return new PuzzleInputConnection(this, false); 
	}

	public void setSelectedTile(int x, int y) {
		this.selX = x;
		this.selY = y;
	}
}
