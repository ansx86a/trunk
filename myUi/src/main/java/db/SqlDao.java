package db;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlDao {
	private static SqlSessionFactory sqlSessionFactory;

	public static void main(String[] args) throws IOException {
		SqlDao dao = new SqlDao();
		dao.init();
		System.out.println(sqlSessionFactory);
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

	public void init() throws IOException {
		String resource = "db/sqlDao.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);

		// sqlSessionFactory 這邊會自動關掉inputStream
		// sqlSessionFactory只要一個instance就夠了
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

}
