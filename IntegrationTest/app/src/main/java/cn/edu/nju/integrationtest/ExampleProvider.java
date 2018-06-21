package cn.edu.nju.integrationtest;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


public class ExampleProvider extends ContentProvider {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "example.db";
    private static final String TAG = ExampleProvider.class.getSimpleName();
    private static UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DatabaseHelper mOpenHelper;

    private static final int FULL = 1;
    private static final int SINGLE = 2;
    static {
        sMatcher.addURI(ExampleContract.AUTHRITY, "example", FULL);
        sMatcher.addURI(ExampleContract.AUTHRITY, "exampletable/#", SINGLE);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (sMatcher.match(uri)) {
            case FULL:
                long rowId = db.insert(ExampleContract.ExampleTable.TABLE_NAME, null, contentValues);
                // 如果插入成功返回uri
                if (rowId > 0) {
                    // 在已有的 Uri的后面追加ID
                    Uri memberUri = ContentUris.withAppendedId(uri, rowId);
                    // 通知数据已经改变
                    getContext().getContentResolver().notifyChange(memberUri, null);
                    return memberUri;
                }
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        // 定义创建表的SQL语句
        final String CREATE_TABLE_SQL = "CREATE TABLE "+ ExampleContract.ExampleTable.TABLE_NAME + " ("
                + ExampleContract.ExampleTable._ID + " INTEGER PRIMARY KEY,"
                + ExampleContract.ExampleTable.NAME + " TEXT,"
                + ExampleContract.ExampleTable.AGE + " INTEGER,"
                + ExampleContract.ExampleTable.GENDER + " TEXT"
                + ");";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // 建表
            db.execSQL(CREATE_TABLE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");

            // Kills the table and existing data
            db.execSQL("DROP TABLE IF EXISTS example;");

            // Recreates the database with a new version
            onCreate(db);

        }
    }
}
