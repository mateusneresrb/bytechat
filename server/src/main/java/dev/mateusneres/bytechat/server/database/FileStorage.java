package dev.mateusneres.bytechat.server.database;

import com.google.gson.reflect.TypeToken;
import dev.mateusneres.bytechat.common.model.Message;
import dev.mateusneres.bytechat.common.utils.GsonUtil;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class FileStorage {

    private final UUID uuid;
    private static final String DATA_FOLDER = "data";
    private final String FILE_EXTENSION = ".json";

    public void addMessageToData(Message message) throws IOException {
        List<Message> messages = new ArrayList<>();
        if (isExists()) {
            messages.addAll(getData());
        }

        messages.add(message);
        saveData(messages);
    }

    public boolean isExists() {
        return new File(DATA_FOLDER, uuid + FILE_EXTENSION).exists();
    }

    public void deleteFile() {
        new File(DATA_FOLDER, uuid + FILE_EXTENSION).delete();
    }

    private void saveData(List<Message> messages) throws IOException {
        String fileName = uuid + FILE_EXTENSION;

        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File file = new File(folder, fileName);
        try (Writer writer = new FileWriter(file)) {
            GsonUtil.getBuilderList().toJson(messages, writer);
        }
    }

    public List<Message> getData() throws IOException {
        File file = new File(DATA_FOLDER, uuid + FILE_EXTENSION);
        try (Reader reader = new FileReader(file)) {
            return GsonUtil.getBuilderList().fromJson(reader, new TypeToken<List<Message>>() {
            }.getType());
        }
    }

}