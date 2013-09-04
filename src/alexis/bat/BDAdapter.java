package alexis.bat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BDAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String _descrip = "detalle";
	public static final String _foto = "foto";

	public static final String TAG = "DBAdapter";
	
	public static final String DATABASE_NAME= "TerminalDavid.db";
	public static final String DATABASE_TABLE = "rutas";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE =
			"create table rutas (_id INTEGER primary key autoincrement, name TEXT not null, foto TEXT not null, detalle TEXT not null)";
	
	
	private final Context context;
	private static DatabaseHelper DBHelper;
	private static SQLiteDatabase db;
	
	public BDAdapter(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			try {
					db.execSQL(DATABASE_CREATE);
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS rutas");
			onCreate(db);
		}
	}
		
		//---opens the database---
		public BDAdapter open() throws SQLException
		{
			db = DBHelper.getWritableDatabase();
			return this;
		}
		//---closes the database---
		public void close()
		{
			DBHelper.close();
		}
		//---insert a contact into the database---
		public long insertRutas(String name)
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_NAME, name);
			//initialValues.put(KEY_EMAIL, email);
			return db.insert(DATABASE_TABLE, null, initialValues);
		}
		public long insertarAll(String name, String descrip, String foto)
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_NAME, name);
			initialValues.put(_descrip, descrip);
			initialValues.put(_foto, foto);
			
			return db.insert(DATABASE_TABLE, null, initialValues);
		}
		
		//---deletes a particular contact---
		public boolean deleteRutas(long rowId)
		{
			return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
		}
		//---retrieves all the contacts---
		public Cursor getAllRutas()
		{
			return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME}, null, null, null, null, null);
		}
		//---retrieves a particular contact---
		public Cursor getRutas(long rowId) throws SQLException
		{
			Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
			if (mCursor != null) 
			{
				mCursor.moveToFirst();
			}
			return mCursor;
		}
		public String getRutasCh(String name) throws SQLException
		{
			return "a";
		}
		//---updates a contact---
		public boolean updateRutas(long rowId, String name, String email)
		{
			ContentValues args = new ContentValues();
			args.put(KEY_NAME, name);
			//args.put(KEY_EMAIL, email);
			return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
		}
	
	
}
