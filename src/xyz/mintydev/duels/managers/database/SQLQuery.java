package xyz.mintydev.duels.managers.database;

public enum SQLQuery {
	
	CREATE_TABLE(
        "CREATE TABLE IF NOT EXISTS `duels_profiles` ("+
        "`uuid` VARCHAR(255) NULL DEFAULT NULL," +
        "`name` VARCHAR(60) NULL DEFAULT NULL," +
        "`wins` BIGINT(255) NULL DEFAULT 0," +
        "`loss` BIGINT(255) NULL DEFAULT 0," +
        "`kills` BIGINT(255) NULL DEFAULT 0," +
        "`deaths` BIGINT(255) NULL DEFAULT 0," +
        "`streak` BIGINT(255) NULL DEFAULT 0," +
        "PRIMARY KEY (`uuid`))"
	),
    INSERT_PROFILE(
            "INSERT INTO `duels_profiles` " +
                    "(`uuid`, `name`, `wins`, `loss`, `kills`, `deaths`, `streak`) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)"
    ),
    UPDATE_PROFILE("UPDATE `duels_profiles` SET `name` = ?, `wins` = ?, `loss` = ?, `kills` = ?, `deaths` = ?, `streak` = ? WHERE `duels_profiles`.`uuid` = ?"),
	SELECT_PROFILE("SELECT * FROM `duels_profiles` WHERE `uuid` = ?"),
	DELETE_PROFILE("DELETE FROM `duels_profiles` WHERE `uuid` = ?");
	
	private final String mySqlQuery;
	
	SQLQuery(String mySqlQuery){
		this.mySqlQuery = mySqlQuery;
	}
	
	/*
	 * Getters & Setters
	 * */
	
	@Override
	public String toString() {
		return mySqlQuery;
	}
	
}
