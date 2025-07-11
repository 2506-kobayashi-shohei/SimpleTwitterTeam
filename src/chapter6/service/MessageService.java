package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.beans.UserMessage;
import chapter6.dao.MessageDao;
import chapter6.dao.UserMessageDao;

public class MessageService {

    public void insert(Message message) {

        Connection connection = null;
        try {
            connection = getConnection();
            new MessageDao().insert(connection, message);
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

    public List<UserMessage> select(String userId, String start, String end) {
        final int LIMIT_NUM = 1000;

        Connection connection = null;
        try {
            connection = getConnection();
            Integer id = null;
            if(!StringUtils.isEmpty(userId)) {
              id = Integer.parseInt(userId);
            }

            //絞り込み(startが入力されていたら)
            if(!StringUtils.isBlank(start)) {
            	start += " 00:00:00";
            }else {
            	start = "2020-01-01 00:00:00";
            }
            //絞り込み(endが入力されていたら)
            if(!StringUtils.isBlank(end)) {
            	end += " 23:59:59";
            }else {
            	Calendar cl = Calendar.getInstance();
            	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	end = dateTimeFormat.format(cl.getTime());
            }

            List<UserMessage> messages = new UserMessageDao().select(connection, id, start, end, LIMIT_NUM);
            return messages;
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
            new MessageDao().delete(connection, id);
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

    public void update(Message record) {

        Connection connection = null;
        try {
            connection = getConnection();
            new MessageDao().update(connection, record);
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

    public Message select(Integer messageId) {
    	Connection connection = null;
    	try {
    		connection = getConnection();
    		Message message = new MessageDao().select(connection, messageId);
            commit(connection);

            return message;
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