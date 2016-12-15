package com.bizdata.commons.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import com.bizdata.admin.domain.User;

/**
 * 客户密码加密工具类
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Component
public class PasswordHelper {

	/**
	 * @Fields randomNumberGenerator : 用于生成随机数，作为盐值
	 */
	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

	/**
	 * @Fields algorithmName : 设置加密算法，此处默认为"md5"
	 */
	private String algorithmName = "md5";

	/**
	 * @Fields hashIterations : 设置加密迭代次数
	 */
	private int hashIterations = 2;

	public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
		this.randomNumberGenerator = randomNumberGenerator;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}

	/**
	 * 针对密码进行加密，生成密码散列值。设置用户的盐值，存入数据库！ 此处我们使用MD5算法，“密码+盐（用户名+随机数）”的方式生成散列值
	 *
	 * @param user
	 *            User
	 */
	public void encryptPassword(User user) {
		// 设置用户盐值，盐值为随机数
		user.setSalt(randomNumberGenerator.nextBytes().toHex());

		// 加密密码，对应参数解释：0:md5算法，1:需要散列的对象，2:盐值(User对象里采用username+slat)，3:算法迭代次数，这里是两次
		// 此处相当于md5(md5(password+自定义slat))
		String mySalt = user.getCredentialsSalt();
		String newPassword = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(mySalt),
				hashIterations).toHex();
		
		user.setPassword(newPassword);
	}

	/**
	 * 判断明文密码与密文密码是否一致
	 *
	 * @param plaintext_password
	 *            明文密码
	 * @param ciphertext_password
	 *            密文密码
	 * @param salt
	 *            盐
	 * @return boolean
	 */
	public boolean checkPassword(String plaintext_password, String ciphertext_password, String salt) {
		String target_password = new SimpleHash(algorithmName, plaintext_password, ByteSource.Util.bytes(salt),
				hashIterations).toHex();
		if (ciphertext_password.equals(target_password)) {
			return true;
		} else {
			return false;
		}
	}
}
