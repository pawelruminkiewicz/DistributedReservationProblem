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

    public ResultSet execute(String query, boolean quorum) {
        Statement statement;
        if(quorum) {
            statement = new SimpleStatement(query).setConsistencyLevel(ConsistencyLevel.QUORUM);
        }
        else {
            return session.execute(query);
        }
        return session.execute(statement);
    }

    public ResultSet execute(String query) {
        return execute(query, false);
    }

}