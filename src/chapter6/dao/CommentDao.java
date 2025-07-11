package chapter6.dao;

import static chapter6.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import chapter6.beans.Comment;
import chapter6.exception.NoRowsUpdatedRuntimeException;
import chapter6.exception.SQLRuntimeException;

public class CommentDao {

	public void insert(Connection connection, Comment comment) {

        PreparedStatement ps = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO comments ( ");
            sql.append("    text, ");
            sql.append("    user_id, ");
            sql.append("    message_id, ");
            sql.append("    created_date, ");
            sql.append("    updated_date ");
            sql.append(") VALUES ( ");
            sql.append("    ?, ");
            sql.append("    ?, ");
            sql.append("    ?, ");
            sql.append("    CURRENT_TIMESTAMP, ");
            sql.append("    CURRENT_TIMESTAMP ");
            sql.append(")");

            ps = connection.prepareStatement(sql.toString());

            ps.setString(1, comment.getText());
            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getMessageId());

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
            sql.append("delete from comments ");
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

    public void update(Connection connection, Comment record) {

        PreparedStatement ps = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("update comments set text = ? where id = ? ");

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

    public Comment selectTarget(Connection connection, Comment record) {

        PreparedStatement ps = null;
        try {
            String sql = "SELECT * FROM comments where id = ?";

            ps = connection.prepareStatement(sql.toString());

            ps.setString(1, String.valueOf(record.getId()));

            ResultSet rs = ps.executeQuery();

            Comment comment = toComment(rs);
            return comment;
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    private Comment toComment(ResultSet rs) throws SQLException {

    	Comment comment = new Comment();
        try {
        	while (rs.next()) {
	            comment.setId(rs.getInt("id"));
	            comment.setText(rs.getString("text"));
	            comment.setUserId(rs.getInt("user_id"));
        	}
        	return comment;
        } finally {
            close(rs);
        }
    }
}
