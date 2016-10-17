package db;

import java.util.HashMap;
import java.util.List;

public interface ISqlDao {
	int test();

	void 新增一筆moePost資料(HashMap map);

	List<HashMap> 撈取moePost資料(HashMap map);
}
