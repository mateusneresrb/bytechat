package dev.mateusneres.bytechat.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.mateusneres.bytechat.common.adapters.ListAdapter;
import dev.mateusneres.bytechat.common.model.UserInfo;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class GsonUtil {

    public static final Type typeOfUserInfoList = new TypeToken<List<UserInfo>>() {
    }.getType();

    public static Gson getBuilderList() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(Collection.class, new ListAdapter());

        return gsonBuilder.create();
    }

}
