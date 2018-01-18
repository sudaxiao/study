package com.dfire;

import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.*;

public class ThreadPoolTest {


	private ThreadPoolExecutor threadPoolExecutor;
	private Integer threadNum;

	@Before
	public void before() {
		threadPoolExecutor = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1), new ThreadPoolExecutor.AbortPolicy());

		System.out.println("---------------------初始化线程池----------------------");
	}

	@After
	public void after() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		threadPoolExecutor.shutdown();

		System.out.println("---------------------关闭线程池----------------------");
	}

	/**
	 * 执行一个无返回值的任务
	 */
	@Test
	public void executeTest() {


		threadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("执行一个无返回值的任务");
			}
		});

	}
	/**
	 * 执行一个有返回值的任务
	 */
	@Test
	public void submitTest() throws ExecutionException, InterruptedException {
		Future<String> submit = threadPoolExecutor.submit(new Callable<String>() {
			@Override
			public String call() {
				return "执行一个有返回值的任务";
			}
		});
		System.out.println(submit.get());
	}




	/**
	 * 中止策略
	 */
	@Test
	public void abortPolicyTest() {
		threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		for (int i = 0; i < 3; i++) {
			String taskName = "task-" + i;
			try {
				threadPoolExecutor.execute(() -> {
					//休眠100毫秒  模拟 任务的执行
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("任务执行了：" + taskName);
				});
			} catch (RejectedExecutionException e) {
				System.out.println("导致异常的任务是:" + taskName);
				e.printStackTrace();

			}
		}
	}

	/**
	 * 抛弃策略
	 */
	@Test
	public void discardPolicyTest() {
	//	threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
	//	threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		for (int i = 0; i < 4; i++) {
			String taskName = "task-" + i;
			threadPoolExecutor.execute(() -> {
				//休眠100毫秒  模拟 任务的执行
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(taskName + "执行了   执行线程名称：" + Thread.currentThread().getName());
			});
		}
		System.out.println(threadPoolExecutor.getTaskCount());
	}


}
