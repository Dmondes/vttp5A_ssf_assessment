package vttp.batch5.ssf.noticeboard.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeRepository {
	@Autowired
	// @Qualifier("notice")
	RedisTemplate<String, Object> template;

	// TODO: Task 4
	// You can change the signature of this method by adding any number of
	// parameters
	// and return any type
	//
	/*
	 * Write the redis-cli command that you use in this method in the comment.
	 * For example if this method deletes a field from a hash, then write the
	 * following
	 * redis-cli command
	 * hdel myhashmap a_key
	 *
	 *
	 */

	public void insertNotices(String key, String mapkey, Object value) {
		template.opsForHash().put(key, mapkey, value);

	}

	// public Object retrieveNotices(String key, Object value) {
	// 	return template.opsForHash().get(key, value);
	// }

	// public boolean hasKey(String key, String mapkey) {
	// 	return template.opsForHash().hasKey(key, mapkey);
	// }

	// public Set<Object> allKeys(String key) {
	// 	return template.opsForHash().keys(key);
	// }

	// public List<Object> allValues(String key) {
	// 	return template.opsForHash().values(key);
	// }

	// public Long sizeOfNotices(String key) {
	// 	return template.opsForHash().size(key);
	// }

	// public void deleteMap(String key, Object value) {
	// 	template.opsForHash().delete(key, value);
	// }

}
