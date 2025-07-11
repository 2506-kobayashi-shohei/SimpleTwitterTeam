package chapter6.dao;

import static chapter6.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import chapter6.beans.UserComment;
import chapter6.exception.SQLRuntimeException;

public class UserCommentDao {

	public List<UserComment> select(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append("    comments.id as id, ");
			sql.append("    users.name as name, ");
			sql.append("    comments.text as text, ");
			sql.append("    users.id as user_id, ");
			sql.append("    comments.message_id as message_id, ");
			sql.append("    comments.created_date as created_date ");
			sql.append("FROM comments ");
			sql.append("INNER JOIN users ");
			sql.append("ON comments.user_id = users.id ");
			sql.append("ORDER BY comments.created_date ASC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			List<UserComment> comments = toUserComments(rs);
			return comments;
		} catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }

	}

	private List<UserComment> toUserComments(ResultSet rs) throws SQLException {

		List<UserComment> comments = new ArrayList<UserComment>();
		try {
			while (rs.next()) {
				UserComment comment = new UserComment();
				comment.setId(rs.getInt("id"));
				comment.setName(rs.getString("name"));
				comment.setText(rs.getString("text"));
				comment.setUserId(rs.getInt("user_id"));
				comment.setMessageId(rs.getInt("message_id"));
				comment.setCreatedDate(rs.getTimestamp("created_date"));

				comments.add(comment);
			}
			return comments;
		} finally {
			close(rs);
		}
	}
}

