package com.exist.ecc.limyu_exercise8.infra.persistence;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        runSqlScript("com/exist/ecc/limyu_exercise8/infra/buildtools/20240926_1.sql");
        runSqlScript("com/exist/ecc/limyu_exercise8/infra/buildtools/20240926_2.sql");
    }

    private void runSqlScript(String scriptPath) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(scriptPath);
        if (inputStream == null) {
            throw new IllegalArgumentException("Script not found: " + scriptPath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
            jdbcTemplate.execute(sql.toString());
        } catch (IOException e) {
            System.out.println("Error reading SQL file '" + scriptPath + "'\n" + e.getMessage());
        }
    }
}
