        package com.hl.afchelper.entity.db;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Environment;


        import com.hl.afchelper.R;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.InputStream;

        /**
         * 用户获取SQLiteDatabase
         *
         * <br>
         *
         * <strong>
         *  app_info.db 数据库中含 t_city表
         * </strong>
         *
         * @since 2014年11月25日 上午11:17:40
         * @author sunny
         */
        public class DBManager {

            public static final String DATABASE_FILENAME = "app.db";

            public static final String PACKAGE_NAME = "com.hl.afchelper";

            public static final String DATABASE_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME+"/databases";

           public static SQLiteDatabase openDatabase(Context context) {
                try {
                    String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
                    File file = new File(databaseFilename);
                    if (!file.exists()) {
                        File dir = new File(DATABASE_PATH);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        InputStream is = context.getResources().openRawResource(R.raw.app);
                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] buffer = new byte[8192];
                        int count = 0;
                        while ((count = is.read(buffer)) > 0) {
                            fos.write(buffer, 0, count);
                        }
                        fos.close();
                        is.close();
                    }
                    SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase (databaseFilename, null);
                    return database;
                } catch (Exception e) {
                }
                return null;
            }
        }
