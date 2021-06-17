package by.marina.service;

import by.marina.DB.DataProvider;
import by.marina.DB.DemoEntity;
import by.marina.exception.DeleteException;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DataService {
    private final DataProvider dataProvider;
    private final Gson gson;

    public DataService() {
        dataProvider = new DataProvider();
        gson = new Gson();
    }

    public String getAllEntitiesForResponse() throws IOException {
        List<DemoEntity> entities = new ArrayList<>();
        try {
            entities = dataProvider.getAllEntities();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return gson.toJson(entities);
    }

    public void addEntity(String entityString){
        DemoEntity entity = gson.fromJson(entityString, DemoEntity.class);
        dataProvider.addEntity(entity);
    }

    public void deleteEntity(String id) throws DeleteException {
        try {
            int intId = Integer.parseInt(id);
            dataProvider.deleteEntityById(intId);
        } catch (NumberFormatException e) {
            throw new DeleteException(400, "Invalid id value");
        }
    }
}
