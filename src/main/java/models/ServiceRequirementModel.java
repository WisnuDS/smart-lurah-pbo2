package models;

import helper.DBConnection;
import helper.Model;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceRequirementModel extends Model {
    private String id;
    private String requirementId;
    private String nameRequirement;
    private String serviceId;
    private String typeService;
    private static final ServiceRequirementModel model = new ServiceRequirementModel();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    public String getNameRequirement() {
        return nameRequirement;
    }

    public void setNameRequirement(String nameRequirement) {
        this.nameRequirement = nameRequirement;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public static List<ServiceRequirementModel> getDetailRequirement(String id) {
        List<ServiceRequirementModel> models = new ArrayList<>();
        for (Object o : model.getAllData("JOIN api_services " +
                "on api_detailrequirements.service_id = api_services.id JOIN api_servicerequirements" +
                " on api_servicerequirements.id = api_detailrequirements.requirement_id WHERE service_id = " + id)) {
            models.add((ServiceRequirementModel) o);
        }
        return models;
    }

    public boolean delete(String clause){
        StringBuilder sql = new StringBuilder("DELETE FROM "+table());
        sql.append(" WHERE ").append(clause);
        System.out.println(sql);
        try {
            DBConnection connection = new DBConnection();
            Statement statement = connection.connection().createStatement();
            return statement.executeUpdate(String.valueOf(sql)) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String table() {
        return "api_detailrequirements";
    }

    @Override
    public String[] columns() {
        return new String[]{"id", "requirement_id", "service_id", "name_requirement", "type_service"};
    }
}
