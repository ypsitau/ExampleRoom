package io.github.ypsitau.exampleroom;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface UserDao {
	@Query("SELECT * FROM user")
	List<User> getAll();

	@Query("SELECT * FROM user WHERE uid IN (:userIds)")
	List<User> loadAllByIds(int[] userIds);

	@Query("SELECT * FROM user WHERE first_name LIKE :first AND "
			+ "last_name LIKE :last LIMIT 1")
	User findByName(String first, String last);

	@Query("SELECT COUNT(*) FROM user")
	int countAll();

	@Insert
	void insert(User user);

	@Insert
	void insertMulti(User... users);

	@Delete
	void delete(User user);
}
