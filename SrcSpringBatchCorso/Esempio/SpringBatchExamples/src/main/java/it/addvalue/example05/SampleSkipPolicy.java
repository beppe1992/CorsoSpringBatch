package it.addvalue.example05;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class SampleSkipPolicy implements SkipPolicy{

	public boolean shouldSkip(Throwable t, int skipCount) throws SkipLimitExceededException {
		// TODO Auto-generated method stub
		return SampleSkipException.class.isAssignableFrom(t.getClass());
	}

}

