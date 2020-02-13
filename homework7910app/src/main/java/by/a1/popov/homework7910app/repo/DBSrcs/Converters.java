package by.a1.popov.homework7910app.repo.DBSrcs;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;

public class Converters {

    @TypeConverter
    public static String fileToString(File file){
        return file.getAbsolutePath();
    }

    @TypeConverter
    public static File stringToFile(String file){
        return new File(file);
    }



}
