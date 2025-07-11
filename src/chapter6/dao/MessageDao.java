package chapter6.dao;

import static chapter6.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import chapter6.beans.Message;
import chapter6.exception.NoRowsUpdatedRuntimeException;
import chapter6.exception.SQLRuntimeException;

public class MessageDao {

    public void insert(Connection connection, Message message) {

        PreparedStatement ps = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO messages ( ");
            sql.append("    user_id, ");
            sql.append("    text, ");
            sql.append("    created_date, ");
            sql.append("    updated_date ");
            sql.append(") VALUES ( ");
            sql.append("    ?, ");                  // user_id
            sql.append("    ?, ");                  // text
            sql.append("    CURRENT_TIMESTAMP, ");  // created_date
            sql.append("    CURRENT_TIMESTAMP ");   // updated_date
            sql.append(")");

            ps = connection.prepareStatement(sql.toString());

            ps.setInt(1, message.getUserId());
            ps.setString(2, message.getText());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    public void delete(Connection connection, Integer id) {

        PreparedStatement ps = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("delete from messages ");
            sql.append("    where ");
            sql.append("    id = " + id);

            ps = connection.prepareStatement(sql.toString());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    public void update(Connection connection, Message record) {

        PreparedStatement ps = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("update messages set text = ? where id = ? ");

            ps = connection.prepareStatement(sql.toString());

            ps.setString(1, record.getText());
            ps.setInt(2, record.getId());

            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NoRowsUpdatedRuntimeException();
            }
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    public Message select(Connection connection, Integer messageId) {
    	PreparedStatement ps = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM messages WHERE id = ? ");
            ps = connection.prepareStatement(sql.toString());
   		 	ps.setInt(1, messageId);

	   		ResultSet rs = ps.executeQuery();
	        List<Message> messages = toMessage(rs);

	        if (messages.isEmpty()) {
                return null;
            } else {
                return messages.get(0);
            }
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    private List<Message> toMessage(ResultSet rs) throws SQLException {

    	 List<Message> messages = new ArrayList<Message>();
         try {
             while (rs.next()) {
                 Message message = new Message();
                 message.setId(rs.getInt("id"));
                 message.setText(rs.getString("text"));
                 message.setUserId(rs.getInt("user_id"));
                 message.setCreatedDate(rs.getTimestamp("created_date"));
                 message.setCreatedDate(rs.getTimestamp("updated_date"));

                 messages.add(message);
             }
             return messages;
         } finally {
             close(rs);
         }
    }
}