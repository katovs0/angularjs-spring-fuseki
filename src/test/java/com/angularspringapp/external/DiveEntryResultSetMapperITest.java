package com.angularspringapp.external;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angualrspringapp.beans.DiveEntry;
import com.angualrspringapp.external.DiveEntryResultSetMapper;
import com.hp.hpl.jena.query.ResultSet;
import com.mockrunner.mock.jdbc.MockResultSet;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:/test/resources/applicationContextTests-Integration.xml" })
//@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class DiveEntryResultSetMapperITest {

	private static Logger LOG = LoggerFactory.getLogger(DiveEntryResultSetMapperITest.class);
	
	@Ignore
	@Test
	public void test_map_dive_entry () {
		System.out.println("@@@");
		DiveEntryResultSetMapper mapper = DiveEntryResultSetMapper.getInstance();

		MockResultSet rs = new MockResultSet("myMock");
		rs.addColumn("p", new String[]{"diver"});
		rs.addColumn("o", new String[]{"diver Name"});
		
		rs.addColumn("p", new String[]{"diveName"});
		rs.addColumn("o", new String[]{"dive Name"});
		
		rs.addColumn("p", new String[]{"diveId"});
		rs.addColumn("o", new String[]{"diveId"});
		
		rs.addColumn("p", new String[]{"airTemp"});
		rs.addColumn("o", new Double[]{11.1});
		
		rs.addColumn("p", new String[]{"buddy"});
		rs.addColumn("o", new String[]{"buddy"});
		
		rs.addColumn("p", new String[]{"depth"});
		rs.addColumn("o", new Integer[]{10});
		
		rs.addColumn("p", new String[]{"location"});
		rs.addColumn("o", new String[]{"location"});
		
		rs.addColumn("p", new String[]{"locationExtLink"});
		rs.addColumn("o", new String[]{"http://external.link"});
		
		rs.addColumn("p", new String[]{"minutes"});
		rs.addColumn("o", new Double[]{12.2});
		
		rs.addColumn("p", new String[]{"waterTemp"});
		rs.addColumn("o", new Double[]{12.2});
		
		// make sure to move the cursor to the first row
		try
		{
		  rs.next();
		}
		catch (SQLException sqle)
		{
		  LOG.info("unable to move resultSet");
		}
		
		DiveEntry dive = mapper.mapDiveEntry("diveId", (ResultSet)rs);
		
		assertNotNull(dive);
		assertNotNull(dive.getAirTemp());
		assertNotNull(dive.getBuddy());
		assertNotNull(dive.getDepth());
		assertNotNull(dive.getDiver());
		assertNotNull(dive.getIdString());
		assertNotNull(dive.getLocation());
		assertNotNull(dive.getLocationExtLink());
		assertNotNull(dive.getMinutes());
		assertNotNull(dive.getName());
		assertNotNull(dive.getWaterTemp());
		
		assertEquals(dive.getIdString(), "diveId");
		assertEquals(dive.getDiver(), "diver");
		assertEquals(dive.getName(), "dive Name");
		assertTrue(dive.getAirTemp() == 11.1);
		assertEquals(dive.getBuddy(), "buddy");
		assertTrue(dive.getDepth() ==  10);
		assertEquals(dive.getLocation(), "location");
		assertEquals(dive.getLocationExtLink(), "http://external.link");
		assertEquals(dive.getLocationPhotoCollection(), "NONE");
		assertTrue(dive.getMinutes() == 12);
		assertTrue(dive.getWaterTemp() == 12.2);
		
		System.out.println(dive);
	}
}
