package it.addvalue.esempio.listener;

import it.addvalue.esempio.generics.InputObject;

import org.springframework.batch.core.listener.SkipListenerSupport;
import org.springframework.batch.retry.RetryCallback;
import org.springframework.batch.retry.RetryContext;
import org.springframework.batch.retry.listener.RetryListenerSupport;

public class SkipListener implements
		org.springframework.batch.core.SkipListener<InputObject, InputObject> {

	public void onSkipInProcess(InputObject arg0, Throwable arg1) {
		System.out.println("Elemento Skipped in processor");

	}

	public void onSkipInRead(Throwable arg0) {
		System.out.println("Elemento Skipped in reader");

	}

	public void onSkipInWrite(InputObject arg0, Throwable arg1) {
		System.out.println("Elemento Skipped in writer");

	}

}
