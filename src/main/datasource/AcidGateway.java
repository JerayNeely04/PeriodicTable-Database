package datasource;

import java.sql.Connection;


public class AcidGateway {
    private long id;
    private String name;
    private long solute;
    private final Connection connection;

    public AcidGateway(String name, long solute) {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
}
