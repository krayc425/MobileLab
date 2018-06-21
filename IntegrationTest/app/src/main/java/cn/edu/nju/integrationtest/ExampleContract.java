package cn.edu.nju.integrationtest;

import android.net.Uri;
import android.provider.BaseColumns;

public class ExampleContract {

    public static final String AUTHRITY = "cn.edu.nju.integrationtest";

    public static final class ExampleTable implements BaseColumns
    {

        //定义表的数据列字段
        public static final String _ID = "_id";
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String GENDER = "gender";

        //面向整个表的uri
        public static final Uri FULL_CONTENT_URI =
                Uri.parse("content://" + AUTHRITY + "/example");

        //面向单条记录数据的uri
        public static final Uri SINGLE_CONTENT_URI =
                Uri.parse("content://" + AUTHRITY + "/exampletable/");

        //表名
        public static final String TABLE_NAME = "exampletable";
    }
}
