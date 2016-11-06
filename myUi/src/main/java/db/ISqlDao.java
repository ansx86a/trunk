package db;

import java.util.HashMap;
import java.util.List;

public interface ISqlDao {
	int test();

	void 新增一筆moePost資料(HashMap map);

	void 新增一筆紳士comic資料(HashMap map);

	void 新增一筆ex資料(HashMap map);

	void 更新紳士comic資料(HashMap map);

	void 更新ex資料(HashMap map);

	List<HashMap> 撈取moePost資料(HashMap map);

	List<HashMap> 撈取紳士comic資料(HashMap map);

	List<HashMap> 撈取ex資料(HashMap map);

}
