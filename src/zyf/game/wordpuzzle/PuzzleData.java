package zyf.game.wordpuzzle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PuzzleData extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "puzzles.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TAG = "PuzzleData";
	public PuzzleData(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String puzzleListTable = "CREATE TABLE " + PuzzleListDDL.TABLE_NAME + " (" 
				+ PuzzleListDDL._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ PuzzleListDDL.CREATED + " DATETIME, " 
				+ PuzzleListDDL.CONTENT + " NTEXT NOT NULL, "
				+ PuzzleListDDL.SIZE + " INTEGER, "
				+ PuzzleListDDL.LANGUAGE + " NVARCHAR(16), "
				+ PuzzleListDDL.DESCRIPTION + " NVARCHAR(128), "
				+ PuzzleListDDL.SELECTX + " INTEGER DEFAULT -1, "
				+ PuzzleListDDL.SELECTY + " INTEGER DEFAULT -1);";
		Log.d(TAG, puzzleListTable);
		db.execSQL(puzzleListTable);
		
		String wordDescTable = "CREATE TABLE " + WordDescDDL.TABLE_NAME + " ("
				+ WordDescDDL._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ WordDescDDL.PUZZLEID + " INTEGER, "
				+ WordDescDDL.LATITUDE + " TINYINT, "
				+ WordDescDDL.STARTX + " INTEGER, "
				+ WordDescDDL.STARTY + " INTEGER, "
				+ WordDescDDL.LENGTH + " INTEGER, "
				+ WordDescDDL.CONTENT + " NVARCHAR(24), "
				+ WordDescDDL.DESCRIPTION + " NTEXT NOT NULL);";
		Log.d(TAG, wordDescTable);
		db.execSQL(wordDescTable);
				
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + PuzzleListDDL.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + WordDescDDL.TABLE_NAME);
		onCreate(db);
	}

}
