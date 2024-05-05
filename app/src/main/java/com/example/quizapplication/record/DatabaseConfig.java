package com.example.quizapplication.record;

public class DatabaseConfig {
    private String createTableURL;
    private String insertJapaneseKanjiDataURL;
    private String readJapaneseKanjiDataURL;
    private String insertNewUsersURL;
    private String checkIfUserExistURL;
    private String usernameCheckerURL;

    private DatabaseConfig(String createTableURL, String insertJapaneseKanjiDataURL,
                           String insertNewUsersURL, String checkIfUserExistURL,
                           String readJapaneseKanjiDataURL, String usernameCheckerURL) {
        this.createTableURL = createTableURL;
        this.insertJapaneseKanjiDataURL = insertJapaneseKanjiDataURL;
        this.insertNewUsersURL = insertNewUsersURL;
        this.checkIfUserExistURL = checkIfUserExistURL;
        this.readJapaneseKanjiDataURL = readJapaneseKanjiDataURL;
        this.usernameCheckerURL = usernameCheckerURL;
    }

    public static DatabaseConfig createWithDefaults() {
        return new DatabaseConfig(
                "http://192.168.254.101/Php-MySQL-Connector/create.table.php",
                "http://192.168.254.101/Php-MySQL-Connector/insert.JP.kanji.data.php",
                "http://192.168.254.101/Php-MySQL-Connector/register.user.php",
                "http://192.168.254.101/Php-MySQL-Connector/user.checker.php",
                "http://192.168.254.101/Php-MySQL-Connector/read.JP.kanji.data.php",
                "http://192.168.254.101/Php-MySQL-Connector/username.checker.php"
        );
    }

    public String getCreateTableURL() {
        return createTableURL;
    }

    public String getInsertJapaneseKanjiDataURL() {
        return insertJapaneseKanjiDataURL;
    }

    public String getInsertNewUsersURL() {
        return insertNewUsersURL;
    }

    public String getCheckIfUserExistURL() {
        return checkIfUserExistURL;
    }

    public String getReadJapaneseKanjiDataURL() {
        return readJapaneseKanjiDataURL;
    }
    public String getUsernameCheckerURL() {
        return usernameCheckerURL;
    }
}
