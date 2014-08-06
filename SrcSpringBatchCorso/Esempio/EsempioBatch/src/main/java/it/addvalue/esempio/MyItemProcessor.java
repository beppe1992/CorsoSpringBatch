package it.addvalue.esempio;

import org.springframework.batch.item.ItemProcessor;

public class MyItemProcessor implements ItemProcessor<MyObject, MyObject> {

	int i = 0;
	
	private TestScope scope;

	public void setScope(TestScope scope) {
		this.scope = scope;
	}

	public MyObject process(MyObject item) throws Exception {

		System.out.println("");
		System.out.println("****************************");
		System.out.println("PROCESSOR");
		System.out.println("lo scope in entrata è :" + scope.getScope());
		System.out.println("****************************");
		System.out.println("");
		
		System.out.println("processor " + i);
		item.setNome("writer " + i);
		i++;

		return item;
	}

}
