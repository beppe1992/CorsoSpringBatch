package it.addvalue.example07.retry;

import org.springframework.batch.retry.RetryCallback;
import org.springframework.batch.retry.RetryContext;
import org.springframework.batch.retry.listener.RetryListenerSupport;

public class RetryListener extends RetryListenerSupport {
	@Override
	public <T> void onError(RetryContext context, RetryCallback<T> callback,
			Throwable throwable) {
		System.out.println("retried operation number " + context.getRetryCount());
		
	}
}
