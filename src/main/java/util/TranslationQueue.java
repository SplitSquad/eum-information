package util;

import com.information.service.TranslationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@RequiredArgsConstructor
public class TranslationQueue {
    private final BlockingQueue<TranslationJob> queue = new LinkedBlockingQueue<>();

    private final TranslationService translationService;

    @PostConstruct
    public void startWorker(){
        new Thread(() -> {
            while(true) {
                try {
                    TranslationJob job = queue.take();
                    translationService
                            .translateInformation(job.getInformation(), job.getInformationReqDto(), job.getInformationId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void enqueue(TranslationJob job) {
        queue.offer(job);
    }
}
