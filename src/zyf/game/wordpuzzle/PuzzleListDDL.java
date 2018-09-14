package zyf.game.wordpuzzle;

import android.provider.BaseColumns;

public interface PuzzleListDDL extends BaseColumns {
	public static final String TABLE_NAME = "puzzle_list";
	
	// Columns in table PuzzleList
	public static final String CREATED = "created";
	public static final String CONTENT = "content";
	public static final String SIZE = "size";
	public static final String LANGUAGE = "language";
	public static final String DESCRIPTION = "description";
	public static final String SELECTX = "selx";
	public static final String SELECTY = "sely";
}
