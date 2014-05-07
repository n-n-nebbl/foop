package at.tuwien.foop.labyrinth.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import at.tuwien.foop.labyrinth.gui.LabyrinthView;
import at.tuwien.foop.labyrinth.model.Door;

public class SpringTest {

	ClassPathXmlApplicationContext ctx;
	
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}

	@After
	public void tearDown() throws Exception {
		ctx.close();
	}

	@Test
	public void testAutoWire() {
		LabyrinthView gui = ctx.getBean(LabyrinthView.class);
		assertNotNull(gui.getEventFactory());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testLists() {
		List<Door> doors = (List<Door>) ctx.getBean("doorList");
		doors.add(new Door());
		doors.add(new Door());
		doors.add(new Door());
		
		List<Door> testDoors = (List<Door>) ctx.getBean("doorList");
		assertEquals(3, testDoors.size());
	}
}
