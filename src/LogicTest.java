import static org.junit.Assert.*;

import org.junit.Test;

public class LogicTest {
	private final int x = 10;
	private final int y = 10;
	@Test
	public void testInitialStates() {
		
		LifeSimulation simulation = new LifeSimulation(x, y);
		
		assert(simulation.getRow() == x);
		assert(simulation.getColumn() == y);
		
		assertFalse(simulation.getLife(4, 4));
		assertFalse(simulation.getLife(7, 4));
		assertFalse(simulation.getLife(0, 0));
		assertFalse(simulation.getLife(9, 9));
	}
	
	@Test
	public void testSettingLife() {
		LifeSimulation sim = new LifeSimulation(x, y);
		
		sim.setLife(0, 0, true);
		sim.setLife(1, 9, true);
		sim.setLife(5, 7, true);
		
		assertTrue(sim.getLife(0, 0));
		assertTrue(sim.getLife(1, 9));
		assertTrue(sim.getLife(5, 7));
		
		sim.setLife(8, 6, false);
		sim.setLife(5, 7, false);
		
		assertFalse(sim.getLife(8, 6));
		assertFalse(sim.getLife(5, 7));
		
		assertFalse(sim.getLife(7, 6));
		assertFalse(sim.getLife(8, 5));
	}
	
	@Test
	public void testLifeByString() {
		LifeSimulation sim = new LifeSimulation(x, y);
		
		sim.setLifeByString("9,8", true);
		sim.setLifeByString("8,8", true);
		sim.setLifeByString("7,8", true);
		sim.setLifeByString("3,5", true);
		
		assertTrue(sim.getLife(9, 8));
		assertTrue(sim.getLife(8, 8));
		assertTrue(sim.getLife(7, 8));
		assertTrue(sim.getLife(3, 5));
		
		sim.setLifeByString("3,5", false);
		sim.setLifeByString("7,8", false);
		sim.setLifeByString("3,8", false);
		sim.setLifeByString("2,5", false);
		
		assertFalse(sim.getLife(3, 5));
		assertFalse(sim.getLife(7, 8));
		assertFalse(sim.getLife(3, 8));
		assertFalse(sim.getLife(2, 5));
	}
	
	@Test
	public void testEvolution() {
		LifeSimulation sim = new LifeSimulation(x, y);
		
		sim.setLifeByString("1,1", true);
		sim.setLifeByString("1,2", true);
		sim.setLifeByString("1,3", true);
		
		sim.setLifeByString("3,4", true);
		sim.setLifeByString("4,4", true);
		sim.setLifeByString("5,4", true);
		
		sim.evolve();
		
		assertTrue(sim.getLife(0, 2));
		assertTrue(sim.getLife(1, 2));
		assertTrue(sim.getLife(2, 2));
		
		assertTrue(sim.getLife(4, 3));
		assertTrue(sim.getLife(4, 4));
		assertTrue(sim.getLife(4, 5));
		
		assertFalse(sim.getLife(1, 1));
		assertFalse(sim.getLife(2, 1));
		assertFalse(sim.getLife(5, 4));
	}
}
