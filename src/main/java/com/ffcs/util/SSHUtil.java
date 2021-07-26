package com.ffcs.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * SSHUtil
 *
 * @author haopeiren
 * @since 2021/7/21
 */
@Slf4j
public class SSHUtil {
    private String DEFAULT_CHARSET = "utf-8";
    public Connection getConnection(String ip, String user, String pass) {
        Connection connection;
        boolean res = false;
        try {
            connection = new Connection(ip);
            connection.connect();
            res = connection.authenticateWithPassword(user, pass);
        } catch (IOException e) {
            log.error("failed to connect {}", ip);
            return null;
        }
        if (!res) {
            log.error("failed to connect {}, authenticate failed", ip);
            return null;
        }
        return connection;
    }

    public String executeCommand(Connection connection, String command) {
        if (Objects.isNull(connection)) {
            log.error("connection is null");
            return null;
        }
        String result;
        try {
            Session session = connection.openSession();
            session.execCommand(command, DEFAULT_CHARSET);
            String stdOut = processStdOut(session.getStdout());
            log.debug(stdOut);
            if (StringUtils.isEmpty(stdOut)) {
                String errOut = processStdOut(session.getStderr());
                log.error("failed to execute command, host : {}, command : {}", connection.getHostname(), command);
                log.error(errOut);
            }
        } catch (IOException e) {
            log.error("failed to execute command", e);
            return null;
        }
        return null;
    }

    private String processStdOut(InputStream is) {
        InputStream stdout = new StreamGobbler(is);
        StringBuilder buffer = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, DEFAULT_CHARSET));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
