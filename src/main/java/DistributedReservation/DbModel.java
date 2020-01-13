package DistributedReservation;

import com.datastax.driver.core.*;


public class DbModel {
    protected Session session;

    public DbModel(Session session) {
        this.session = session;
    }

    public void createTable(String tableName) {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(tableName).append("(")
                .append("id uuid, ")
                .append("tourCompanyId int,")
                .append("groupId int,")
                .append("groupSize int,")
                .append("timestamp bigint,")
                .append("PRIMARY KEY (tourCompanyId, id));");

        String query = sb.toString();
        session.execute(query);
    }

    public ResultSet execute(String query) {;
        return session.execute(query);
    }

    public ResultSet executeQuorum(String query) {
        Statement statement = new SimpleStatement(query).setConsistencyLevel(ConsistencyLevel.QUORUM);
        return session.execute(statement);
    }
}