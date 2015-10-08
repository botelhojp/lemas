package lemas.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import jade.core.AID;
import lemas.agent.LemasAgent;

public class LemasDB {

	private static LemasDB instance = new LemasDB();
	private Connection conn = null;

	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LemasDB getInstance() {
		return instance;
	}

	public void connect() {
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/lemas", "postgres", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		LemasDB db = LemasDB.getInstance();
		db.connect();
		db.save(new AID("sergio", false), new LemasAgent());
		db.close();
	}

	private void save(AID aid, LemasAgent lemasAgent) {
		try {
			if (!exist(aid)) {
				System.out.println("criar");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean exist(AID aid) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM agent WHERE aid = '" + aid.getName() + "'");
			boolean r = rs.next();
			rs.close();
			st.close();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
