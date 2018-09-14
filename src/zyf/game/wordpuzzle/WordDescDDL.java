package zyf.game.wordpuzzle;

import android.provider.BaseColumns;

public interface WordDescDDL extends BaseColumns {

	public static final String TABLE_NAME = "word_description";
	
	// Columns in table PuzzleList
	public static final String PUZZLEID = "puzzle_id";
	public static final String LATITUDE = "latitude"; //  0 means horizontal, 1 means vertical
	public static final String STARTX= "start_x";
	public static final String STARTY = "start_y";
	public static final String LENGTH = "length";
	public static final String CONTENT = "content";
	public static final String DESCRIPTION = "description";
}
