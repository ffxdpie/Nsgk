package com.fx.nsgk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "ns.db";
    private static final int DATABASE_VERSION = 1;


    // 数据库文件名
    @SuppressLint("SdCardPath")
    private static final String DB_PATH = "/data/data/com.fx.nsgk/databases/";

    public void copyDatabaseFromAssets(Context context) {
        // 获取数据库文件的目标路径
        String dbPath = DB_PATH + DATABASE_NAME;

        // 检查目标路径是否存在，如果不存在则创建它
        File dbFile = new File(dbPath);
//        if (!dbFile.exists()) {
        try {
            // 创建目标文件夹
            File directory = new File(DB_PATH);
            directory.mkdirs();
            // 从 assets 文件夹中复制数据库文件
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(DATABASE_NAME);
            OutputStream outputStream = Files.newOutputStream(dbFile.toPath());
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            //Toast.makeText(context, "创建数据成功!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "复制数据库出错: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
//    }
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 在这里创建数据库表，如果需要的话
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 如果需要升级数据库，可以在这里实现
    }

    // 查询数据库中与给定值对应的 ID
    @SuppressLint("Range")
    public List<Integer> queryIdsForWorkingDistance(double distance, String angle, double weight) {
        List<Integer> ids = new ArrayList<>();
        // 将 angle 转换为最近的有效值
        int[] validAngles = {0, 2, 5, 10, 15, 20, 25, 30, 45, 60, 90};
        int angleValue = Integer.parseInt(angle);
        int roundedAngle = getClosestAngle(angleValue, validAngles);
        double distancemath;
        if (distance <= 24.5 & distance > 24) {
            distancemath = 24.5;
        } else {
            distancemath = Math.ceil(distance);
        }

        // 使用 try-with-resources 确保资源自动关闭
        try (SQLiteDatabase db = this.getReadableDatabase();
             @SuppressLint("DefaultLocale")
             Cursor cursor = db.rawQuery("SELECT id FROM NSgk WHERE distance = ? AND angle" + roundedAngle + " >= ?",
                     new String[]{String.valueOf(distancemath), String.valueOf(weight)})
        ) {

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    ids.add(id);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "数据查询错误: " + e.getMessage());
        }
        return ids;
    }


    // 获取最接近的有效 angle 值
    private int getClosestAngle(int angle, int[] validAngles) {
        for (int validAngle : validAngles) {
            if (angle <= validAngle) {
                return validAngle;
            }
        }
        return validAngles[validAngles.length - 1]; // 返回最大值
    }


    // 根据 id 获取 Working, distance 和 angle0 的值
    @SuppressLint("Range")
    public WorkingData getWorkingDistanceAngle0ById(int id) {
        SQLiteDatabase db = null;
        WorkingData result = null;  // 返回 null 表示未找到数据
        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM NSgk WHERE id = ?", new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                int working = cursor.getInt(cursor.getColumnIndex("Working"));
                double distance = cursor.getDouble(cursor.getColumnIndex("distance"));
                double angle0 = cursor.getDouble(cursor.getColumnIndex("angle0"));
                result = new WorkingData(working, distance, angle0);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "数据查询错误: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }


    // 查询数据库中根据给的条件查找适应的工况
    // rcdistance 配重伸出距离
    // Otype 支腿类型
    // Cwtype 配重类型
    // Rtype  回转类型
    @SuppressLint("Range")
    public List<Integer> queryWork(int rcdistance, int oType, int cwType, int rType) {
        SQLiteDatabase db = null;
        List<Integer> gkValues = new ArrayList<>();
        WorksElectTyp result = null;  // 返回 null 表示未找到数据
        try {
            db = this.getReadableDatabase();
            // 构建 SQL 查询，考虑所有参数都可能为空的情况
            StringBuilder queryBuilder = new StringBuilder("SELECT gk FROM gk WHERE ");
            List<String> conditions = new ArrayList<>();
            List<String> args = new ArrayList<>();
            result = new WorksElectTyp(rcdistance, oType, cwType, rType);

            if (rcdistance > 0) {
                conditions.add("rcdistance = ?");
                args.add(String.valueOf(result.rcdistancetype));
            }
            if (oType > 0) {
                conditions.add("Otype = ?");
                args.add(String.valueOf(result.otypetpye));
            }
            if (cwType > 0) {
                conditions.add("Cwtype = ?");
                args.add(String.valueOf(result.cwtypetype));
            }
            if (rType > 0) {
                conditions.add("Rtype= ?");
                args.add(String.valueOf(result.rtypetype));
            }

            queryBuilder.append(TextUtils.join(" AND ", conditions));

            Cursor cursor = db.rawQuery(queryBuilder.toString(), args.toArray(new String[0]));
            if (cursor.moveToFirst()) {
                do {
                    int gkValue = cursor.getInt(cursor.getColumnIndex("gk"));
                    gkValues.add(gkValue);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "Error querying database: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return gkValues;
    }


    //获取工况的信息
    @SuppressLint("Range")
    public Workingconditiontype WorkingType(String gk) {
        SQLiteDatabase db = null;
        Workingconditiontype result = null;  // 返回 null 表示未找到数据
        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM gk WHERE gk = ?", new String[]{gk});
            if (cursor.moveToFirst()) {
                int rcdistance = cursor.getInt(cursor.getColumnIndex("rcdistance"));
                int otype = cursor.getInt(cursor.getColumnIndex("Otype"));
                int cwtype = cursor.getInt(cursor.getColumnIndex("Cwtype"));
                int rtype = cursor.getInt(cursor.getColumnIndex("Rtype"));

                result = new Workingconditiontype(rcdistance, otype, cwtype, rtype);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "数据查询错误: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }


    // 根据角度和吊臂距离得到每个角度起重量
    @SuppressLint("Range")
    public ArrayList<Double> getAngularWeight(int angle, int working) {
        SQLiteDatabase db = null;
        ArrayList<Double> angleValues = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String angleColumn = "angle" + angle;
            String query = "SELECT " + angleColumn + " FROM NSgk_Max WHERE Working = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(working)});

            while (cursor.moveToNext()) {
                if (!cursor.isNull(cursor.getColumnIndex(angleColumn))) {
                    double angleValue = cursor.getDouble(cursor.getColumnIndex(angleColumn));
                    angleValues.add(angleValue);
                } else {
                    // 提供一个默认值，如 0.0 或其他合适的值
                    angleValues.add(0.0);
                }
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "查询错误: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return angleValues;
    }

    public ArrayList<Double> getDistancesWeight(double distances, int working) {

        SQLiteDatabase db = null;
        ArrayList<Double> distancesValues = new ArrayList<>();
        db = this.getReadableDatabase();

        String query = "SELECT angle0, angle1, angle2, angle3, angle4, angle5, angle6, angle7, angle8, angle9, "
                + "angle10, angle11, angle12, angle13, angle14, angle15, angle16, angle17, angle18, angle19, "
                + "angle20, angle21, angle22, angle23, angle24, angle25, angle26, angle27, angle28, angle29, "
                + "angle30, angle31, angle32, angle33, angle34, angle35, angle36, angle37, angle38, angle39, "
                + "angle40, angle41, angle42, angle43, angle44, angle45, angle46, angle47, angle48, angle49, "
                + "angle50, angle51, angle52, angle53, angle54, angle55, angle56, angle57, angle58, angle59, "
                + "angle60, angle61, angle62, angle63, angle64, angle65, angle66, angle67, angle68, angle69, "
                + "angle70, angle71, angle72, angle73, angle74, angle75, angle76, angle77, angle78, angle79, "
                + "angle80, angle81, angle82, angle83, angle84, angle85, angle86, angle87, angle88, angle89, angle90 "
                + "FROM NSgk_Max WHERE Working = ? AND distance = ?";

//        String query = "SELECT angle0,angle2,angle5,angle10,angle15,angle20,angle25,angle30,angle45,angle60, angle90 FROM NSgk WHERE Working = ? AND distance = ?";
        Cursor cursor = db.rawQuery(query, new String[]{ String.valueOf(working),String.valueOf(distances)});

        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                distancesValues.add(cursor.getDouble(i));
                Log.d("dis", "getDistancesWeight: "+ distancesValues);
            }
        }
        cursor.close();
        return distancesValues;
    }

    @SuppressLint("Range")
    public double Anglelengthliftingweight(int angle,double distances, int working ) {
        SQLiteDatabase db = null;
        double weight = 0;
        try {
            db = this.getReadableDatabase();
            String angleColumn = "angle" + angle;
            String query = "SELECT "+ angleColumn +" FROM NSgk_Max WHERE Working = ? AND distance =? ";
            Cursor cursor = db.rawQuery(query, new String[]{ String.valueOf(working),String.valueOf(distances)});
            if (cursor.moveToFirst()) {
                 weight = cursor.getDouble(cursor.getColumnIndex(angleColumn));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "数据查询错误: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        Log.d(TAG, "Anglelengthliftingweight: "+ weight);
        return weight;

    }


    //查询各种车体的信息
    @SuppressLint("Range")
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM vehicles",null);

        if (cursor.moveToFirst()) {
            do {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(cursor.getInt(cursor.getColumnIndex("id")));
                vehicle.setName(cursor.getString(cursor.getColumnIndex("name")));
                vehicle.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
                vehicle.setLength(cursor.getString(cursor.getColumnIndex("length")));
                vehicle.setHeight(cursor.getString(cursor.getColumnIndex("height")));
                vehicleList.add(vehicle);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return vehicleList;
    }



}
