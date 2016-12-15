package com.bizdata.framework.extension;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bizdata.admin.domain.Init;
import com.bizdata.admin.repository.InitRepository;
import com.bizdata.commons.utils.ExecSqlUtils;
import com.bizdata.commons.utils.MessageUtils;
import com.bizdata.framework.extension.log.Loggable;

/**
 * 初始化加载数据执行类,在spring boot启动完成后执行<br>
 * 如果是第一次运行,加载对应数据至数据库,并生成init文件用于标识已经初始化过<br>
 * 之后发现init文件存在,则无需再次执行初始化
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Component
public class InitDataRunner implements CommandLineRunner {

	@Loggable
	private Logger logger;

	@Autowired
	private MessageUtils messageUtils;

	@Autowired
	private ExecSqlUtils execSqlUtils;

	@Autowired
	private InitRepository initRepository;

	@Override
	public void run(String... arg0) throws Exception {
		if (!checkDataIsInited()) {
			// 如果未初始化,执行初始化导入
			initData();
		}
	}

	/**
	 * 初始化数据
	 *
	 */
	private void initData() {
		// 如果数据未初始化,执行相关基础数据导入
		logger.info("\n" + messageUtils.getMessage("data.importing"));
		try {
			execSqlUtils.execSqlFile();
			initSuccess();
			logger.info("\n" + messageUtils.getMessage("data.imported"));
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("初始化数据导入失败...");
		}
	}

	/**
	 * 判断数据是否已经初始化
	 *
	 */
	private boolean checkDataIsInited() {
		boolean state = false;
		List<Init> list = initRepository.findAll();
		if (list.size() > 0 && list.get(0).isState()) {
			// 已初始化
			state = true;
		} else {
			// 未初始化
			state = false;
		}
		return state;
	}

	/**
	 * 数据初始化成功,写入init表一条初始化记录
	 *
	 * @return boolean状态
	 */
	private boolean initSuccess() {
		boolean state = false;
		try {
			Init init = new Init();
			init.setState(true);
			initRepository.save(init);
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}
}
