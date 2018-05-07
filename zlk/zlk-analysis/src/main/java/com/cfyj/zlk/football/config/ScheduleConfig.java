package com.cfyj.zlk.football.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 使用自定义的线程池
 * @author ls
 * @2018年1月23日
 */
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		/**
		 * scheduledThread线程的特点： 1.支持定时以及周期性执行任务的需求 2.线程池大小固定 3.创建的线程不会被销毁。（其实是因为内部使用的队列和执行策略造成了不同的线程池）
		 */
		return Executors.newScheduledThreadPool(45); 
	}

}
