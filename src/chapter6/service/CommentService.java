package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import chapter6.beans.Comment;
import chapter6.beans.UserComment;
import chapter6.dao.CommentDao;
import chapter6.dao.UserCommentDao;

public class CommentService {

	public void insert(Comment comment) {

        Connection connection = null;
        try {
            connection = getConnection();
            new CommentDao().insert(connection, comment);
            commit(connection);
        } catch (RuntimeException e) {
            rollback(connection);
            throw e;
        } catch (Error e) {
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }
    }

    public List<UserComment> select() {
        final int LIMIT_NUM = 1000;

        Connection connection = null;
        try {
            connection = getConnection();
            List<UserComment> comments = new UserCommentDao().select(connection, LIMIT_NUM);
            return comments;
        } catch (RuntimeException e) {
            rollback(connection);
            throw e;
        } catch (Error e) {
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }
    }

    /**つぶやきを削除します
     * @param id つぶやきID
     */
    public void delete(Integer id) {

        Connection connection = null;
        try {
            connection = getConnection();
            new CommentDao().delete(connection, id);
            commit(connection);
        } catch (RuntimeException e) {
            rollback(connection);
            throw e;
        } catch (Error e) {
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }
    }

    public void update(Comment record) {

        Connection connection = null;
        try {
            connection = getConnection();
            new CommentDao().update(connection, record);
            commit(connection);
        } catch (RuntimeException e) {
            rollback(connection);
            throw e;
        } catch (Error e) {
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }
    }

    public Comment selectTarget(Comment condition) {

        Connection connection = null;
        try {
            connection = getConnection();

            Comment comment = new CommentDao().selectTarget(connection, condition);
            return comment;
        } catch (RuntimeException e) {
            rollback(connection);
            throw e;
        } catch (Error e) {
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }
    }
}
