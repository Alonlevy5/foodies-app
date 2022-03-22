package com.comas.foodies.model;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.comas.foodies.MyApplication;

@Database(entities = {Recipe.class}, version = 15)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract RecipeDao recipeDao();
}
public class AppLocalDb{
    static public AppLocalDbRepository db =
          Room.databaseBuilder(MyApplication.getContext(),
                AppLocalDbRepository.class,
             "dbFileName.db")
           .fallbackToDestructiveMigration()
          .build();
}
