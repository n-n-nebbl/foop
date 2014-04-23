package at.tuwien.foop.labyrinth;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextHolder {

	private static ClassPathXmlApplicationContext ctx;
	
	static {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
		ctx.registerShutdownHook();
	}
	
	public static ClassPathXmlApplicationContext getContext() {
		return ctx;
	}
}
