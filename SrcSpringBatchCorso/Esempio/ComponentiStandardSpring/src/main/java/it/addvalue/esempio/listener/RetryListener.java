package it.addvalue.esempio.listener;


import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

public class RetryListener extends RetryListenerSupport {
	@Override
	public <T,E extends Throwable> void onError(RetryContext context, RetryCallback<T,E> callback,
							Throwable throwable) {
		System.out.println("Retry operation Number " + context.getRetryCount());
		
	}
}
