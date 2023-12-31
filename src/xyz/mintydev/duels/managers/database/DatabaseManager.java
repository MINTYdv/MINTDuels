package xyz.mintydev.duels.managers.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.zaxxer.hikari.HikariDataSource;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.DuelPlayer;

public class DatabaseManager {
	
	private final MINTDuels main;
	private static DatabaseManager instance;
	
    private RowSetFactory rowSetFactory;
	
	private HikariDataSource dataSource;
	
	public DatabaseManager(MINTDuels main) {
		this.main = main;
		instance = this;
		
		setup();
	}
	
	/** 
	 * Function called to setup the database/hikari database connection
	 * */
	private void setup() {
		try {
			dataSource = new DBDataSource().getNewDataSource();
		}catch(Exception e) {
			main.getLogger().log(Level.SEVERE, "Could not establish a connection to the MySQL Database. Stopping the plugin.");
			main.getPluginLoader().disablePlugin(main);
			return;
		}

		/* In case we can't connect to the MySQL Database, stop the plugin. */
		if(!isConnectionValid()) {
			main.getLogger().log(Level.SEVERE, "Could not establish a connection to the MySQL Database. Stopping the plugin.");
			main.getPluginLoader().disablePlugin(main);
			return;
		}
		
		/* Create the default tables in the DB */
		executeStatement(SQLQuery.CREATE_TABLE);
	}
	
	/** 
	 * Shutdown the database connection
	 * */
	public void shutdown() {
		if(dataSource == null) return; // in case of crash / no connection
		dataSource.close();
	}
	
    /**
     * Execute a sql statement without having to obtain any results.
     *
     * @param sql        the sql statement
     * @param parameters the parameters
     */
    public void executeStatement(SQLQuery query, Object... parameters) {
        executeStatement(query, false, parameters);
    }
	
    /**
     * Function to execute a sql statement.
     *
     * @param sql        the sql query
     * @param parameters the parameters
     * @return the result set
     */
	public ResultSet executeStatement(SQLQuery query, boolean result, Object...parameters) {
		return executeStatement(query.toString(), result, parameters);
	}
	
	public ResultSet executeResultStatement(SQLQuery query, Object...parameters) {
		return executeStatement(query, true, parameters);
	}
	
	public void insertPunishment() {
		
	}
	
	private synchronized ResultSet executeStatement(String sql, boolean result, Object... parameters) {
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

    		for (int i = 0; i < parameters.length; i++) {
    			statement.setObject(i + 1, parameters[i]);
    		}
    		if (result) {
    			CachedRowSet results = createCachedRowSet();
    			results.populate(statement.executeQuery());
    			return results;
    		}
   			statement.execute();
    	} catch (SQLException ex) {
    		ex.printStackTrace();
       	} catch (NullPointerException ex) {
       		ex.printStackTrace();
        }
        return null;
    }
	
	public DuelPlayer getProfileFromResultSet(ResultSet rs) throws SQLException {
		DuelPlayer res = new DuelPlayer(UUID.fromString(rs.getString("uuid")),
				rs.getString("name"),
				rs.getInt("wins"),
				rs.getInt("loss"),
				rs.getInt("kills"),
				rs.getInt("deaths"),
				rs.getInt("streak"));
		return res;
	}
	
    private CachedRowSet createCachedRowSet() throws SQLException {
    	if (rowSetFactory == null) {
    		rowSetFactory = RowSetProvider.newFactory();
    	}
    	return rowSetFactory.createCachedRowSet();
    }
	
	public void initPool() {
		setup();
	}
	
	public void closePool() {
		this.dataSource.close();
	}
	
	public Connection getConnection() {
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			main.getLogger().warning("ERROR : Could not connect to database.");
			e.printStackTrace();
			return null;
		}
	}
	
    /**
     * Check whether there is a valid connection to the database.
     *
     * @return whether there is a valid connection
     */
    public boolean isConnectionValid() {
    	if(dataSource == null) return false;
        return dataSource.isRunning();
    }

	/* 
	 * Getters & Setters
	 * */
	
	public static DatabaseManager get() {
		return instance;
	}
	
	
}
