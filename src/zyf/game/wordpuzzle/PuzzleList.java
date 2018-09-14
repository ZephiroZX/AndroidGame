package zyf.game.wordpuzzle;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class PuzzleList extends ListActivity {

	private static final String TAG = "PuzzleList";
	private PuzzleData puzzles;
	private static String[] FROM = {PuzzleListDDL._ID, PuzzleListDDL.CREATED, PuzzleListDDL.DESCRIPTION, };
	private static int[] TO = {R.id.rowid, R.id.created_time, R.id.description, };
	private static String ORDER_BY = PuzzleListDDL._ID + " DESC";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puzzle_list);
		puzzles = new PuzzleData(this);
		try {
//			addRecord();
			showPuzzleList();
		} finally {
			puzzles.close();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		try {
			showPuzzleList();
		} finally {
			puzzles.close();
		}
	}
	
	private void showPuzzleList() {
		SQLiteDatabase db = puzzles.getReadableDatabase();
		Cursor cursor = db.query(PuzzleListDDL.TABLE_NAME, 
				FROM, null, null, null, null, ORDER_BY);
		startManagingCursor(cursor);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
				R.layout.puzzle_item, cursor, FROM, TO);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.d(TAG, "row id" + id);
		Intent intent = new Intent(PuzzleList.this, PuzzleActivity.class);
		intent.putExtra(PuzzleActivity.KEY_PUZZLE_ID, (int)id);
		startActivity(intent);
	}
	
	public void addRecord() {
		// temp function
		SQLiteDatabase db = puzzles.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PuzzleListDDL.CREATED, "2012-02-18 00:00:00");
		values.put(PuzzleListDDL.CONTENT, "* *   *  *    * ** ** ****        *  ** *** * *    *   ** ** ** **        ** **  * *   *  *    * * *");
		values.put(PuzzleListDDL.SIZE, 10);
		values.put(PuzzleListDDL.LANGUAGE, "Chinese");
		values.put(PuzzleListDDL.DESCRIPTION, "一种最常见的建筑材料，多呈现灰绿色或棕色的粉末");
		db.insertOrThrow(PuzzleListDDL.TABLE_NAME, null, values);
		
		SQLiteDatabase rdb = puzzles.getReadableDatabase();
		Cursor cursor = rdb.rawQuery("select max(_id) as max_id from " + PuzzleListDDL.TABLE_NAME, null);
		int id = 0;
		if (cursor.moveToLast()) {
			id = cursor.getInt(0);
			Log.d(TAG, String.valueOf(id));
		}
		String[] desc = {"癫痫病的通俗叫法",
				"一种最常见的建筑材料，多呈现灰绿色或棕色的粉末, 用石灰石、黏土等加工制成，加水搅拌，干燥后变得坚硬",
				"我国的象征性建筑，全长6300米",
				"成语。比喻知音或乐曲高妙",
				"成语。比喻不必要的忧虑。传说有一个生活在杞国的人，整天担心天塌下来，愁得吃不下饭，睡不好觉",
				"起伏很小，海拔较低的广大平地",
				"长篇叙事诗名，唐末韦庄作。诗篇通过一个少妇的自述，描写她在长安遇到黄巢起义军入城，以及逃到洛阳的情形",
				"我国现代象征派诗人，代表作有《雨巷》、《断指》、《我的记忆》等。",
				"天文学名词。太阳表面的气体漩涡，温度较周围地区地，从地球上看像是太阳表面上的黑斑",
				"一种用凹透镜做镜片的眼睛，它能使来自物体的光线略向外发散，让其透过眼的透镜时，可聚焦在视网膜上",
				"曲牌名。马致远曾用此曲牌咏春，该曲前两句为“画堂春暖绣帷重，宝篆香微动",
				"南北朝时江淹写的一篇关于离别的篇名",
				"散文篇名。 唐代柳宗元作，叙述永州蒋姓三世以捕蛇为业，用这种手段逃避税收，宁愿被蛇咬死也不愿改变职业",
				
				"指联络距离远的国家，进攻临近的国家，原是战国时期张仪为秦国提出的一种外交策略",
				"波兰物理学家，最先发现镭元素，曾两次获得诺贝尔奖",
				"毒蛇的一种。激怒时颈部膨胀变粗，上面有一对白边黑心的环状斑纹，像一副眼镜",
				"广州市的别称",
				"用来观测天体的望远镜",
				"一种叙事性的文学体裁。通过人物的塑造和情节、环境的描述来概括地表现社会生活的矛盾",
				"一种玩具名。在竹篾等做的骨架上糊上纸和绢，拉着系在上面的上线，趁风势可以飞上天空",
				"朝鲜的首都",
				"海拔较高，地形起伏较小的大片平地，一般海拔再500米以上",
				"一种落叶乔木。球果呈卵状长圆形或卵状圆柱形，成熟时为蓝紫色至灰褐色。中国特有品种，仅分布于陕西秦岭，在海拔2600～3500米处的高寒山地组成纯林或散布于针阔混交林内。",
				"和荆轲一起去刺秦王的人，传说他在十二三岁就杀过人",
				"一种自然灾害。山坡上大量泥沙，石块等山洪冲击而形成的突发性急流",
				"在郊区或者风景区建造的供休养用的园林住宅",
				"词牌名。因为李白诗有”笛奏龙吟水“，所以得名",
				"赋篇名。西汉司马相如作，文中写了三个假想人物：子虚，乌有先生、亡是公"};
		String[] word = {"羊癫疯","水泥","万里长城","高山流水","杞人忧天","平原","秦妇吟","戴望舒","太阳黑子","近视眼镜","小桃红","别赋","捕蛇者说",
					"远交近攻","居里夫人","眼镜蛇","羊城","天文望远镜","小说","风筝","平壤","高原","太白红杉","秦舞阳","泥石流", "别墅", "水龙吟","子虚赋"};
		int[] startx = {3, 7, 0, 6, 0, 5, 7, 2, 6, 0, 4, 8, 1, 
						0, 1, 2, 3, 3, 4, 5, 5, 6, 6, 7, 8, 8, 9, 9};
		int[] starty = {0, 0, 1, 2, 3, 3, 4, 5, 6, 7, 8, 8, 9,
						5, 0, 7, 0, 3, 8, 0, 3, 2, 6, 4, 0, 8, 2, 6};
		int[] length = {3, 2, 4, 4, 4, 2, 3, 3, 4, 4, 3, 2, 4,
						4, 4, 3, 2, 5, 2, 2, 2, 2, 4, 3, 3, 2, 3, 3};
		int[] latitude = {0, 1}; 
		int[] len = {13, 15};
		for (int j = 0; j < 2; ++j) {
			for (int i = 0; i < len[j]; ++i) {
				ContentValues pvalues = new ContentValues();
				pvalues.put(WordDescDDL.CONTENT, word[j*13 + i]);
				pvalues.put(WordDescDDL.DESCRIPTION, desc[j*13 + i]);
				pvalues.put(WordDescDDL.LATITUDE, latitude[j]);
				pvalues.put(WordDescDDL.LENGTH, length[j*13 + i]);
				pvalues.put(WordDescDDL.STARTX, startx[j*13 + i]);
				pvalues.put(WordDescDDL.STARTY, starty[j*13 + i]);
				pvalues.put(WordDescDDL.PUZZLEID, id);
				db.insertOrThrow(WordDescDDL.TABLE_NAME, null, pvalues);
			}
		}
		
	}
}
