package com.zyd.blog.core.schedule;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.zyd.blog.business.entity.ArticleLook;
import com.zyd.blog.business.service.BizArticleLookService;
import com.zyd.blog.business.service.BizArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://docs.zhyd.me
 * @date 2019/3/21 17:53
 * @since 1.8
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleLookTask {

    ExecutorService executorService = createExecutor();

    private final BizArticleService bizArticleService;

    private final BizArticleLookService articleLookService;

    //    private BlockingQueue<ArticleLook> queue = new ArrayBlockingQueue<>(1024);
    private static ExecutorService createExecutor() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().
                setNamePrefix("article-look").setDaemon(true).build();

//        return new ThreadPoolExecutor(1, 2, 10L, TimeUnit.SECONDS,
        ThreadPoolExecutor executor= new ThreadPoolExecutor(1, 2, 100L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory,
//                new ThreadPoolExecutor.AbortPolicy());
                new ThreadPoolExecutor.DiscardPolicy());
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    /**
     * 保存文章的浏览记录，先进先出
     */
    public void addLookRecordToQueue(ArticleLook articleLook) {
        if (null == articleLook) {
            return;
        }
//        queue.offer(articleLook);
        executorService.submit(() -> save(articleLook));
    }

    //    public void save() {
//        List<ArticleLook> bufferList = new ArrayList<>();
//        while (true) {
//            try {
//                bufferList.add(queue.take());
//                for (ArticleLook articleLook : bufferList) {
//                    if (!bizArticleService.isExist(articleLook.getArticleId())) {
//                        log.warn("{}-该文章不存在！", articleLook.getArticleId());
//                        continue;
//                    }
//                    articleLookService.insert(articleLook);
//                }
//            } catch (InterruptedException e) {
//                log.error("保存文章浏览记录失败--->[{}]", e.getMessage());
//                // 防止缓冲队列填充数据出现异常时不断刷屏
//                try {
//                    Thread.sleep(1000);
//                } catch (Exception err) {
//                    log.error(err.getMessage());
//                }
//            } finally {
//                bufferList.clear();
//            }
//        }
    private void save(ArticleLook articleLook) {
        if (!bizArticleService.isExist(articleLook.getArticleId())) {
            log.warn("{}-该文章不存在！", articleLook.getArticleId());
            return;
        }
        articleLookService.insert(articleLook);
    }

}
