package db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlDao {
	private static SqlSessionFactory sqlSessionFactory;
	private static SqlDao dao;

	public static void main(String[] args) throws IOException {
		SqlDao dao = get();
		System.out.println(sqlSessionFactory);

		HashMap map = new HashMap();
		map.put("postid", 2233);
		// dao.新增一筆moePost資料(map);
		// dao.test();
		dao.撈取moePost資料(map);
	}

	public void test() {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ISqlDao mapper = session.getMapper(ISqlDao.class);
			int test = mapper.test();
			System.out.println(test);
			test = mapper.test();
			System.out.println(test);
			test = mapper.test();
			System.out.println(test);
			test = mapper.test();
			System.out.println(test);
		}
	}

	public List<HashMap> 撈取moePost資料(HashMap map) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ISqlDao mapper = session.getMapper(ISqlDao.class);
			List<HashMap> list = mapper.撈取moePost資料(map);
			return list;
			// System.out.println("result=" + list);
		}
	}

	public void 新增一筆moePost資料(HashMap map) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ISqlDao mapper = session.getMapper(ISqlDao.class);
			mapper.新增一筆moePost資料(map);
			session.commit();
			// System.out.println("insert ok:" + map);
		}
	}

	/**
	 * 初始化，每個物件固定一個sqlSessionFactory
	 * @throws IOException
	 */
	public void init() throws IOException {
		String resource = "db/sqlDao.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);

		// sqlSessionFactory 這邊會自動關掉inputStream
		// sqlSessionFactory只要一個instance就夠了
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	/**
	 * 取得靜態實體物件，getInstance太長，改用get取代
	 * @return
	 */
	public static SqlDao get() {
		if (dao == null) {
			dao = new SqlDao();
			try {
				dao.init();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dao;
	}

}
