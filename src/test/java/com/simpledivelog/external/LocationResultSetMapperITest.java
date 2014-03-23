package com.simpledivelog.external;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.query.ResultSet;
import com.mockrunner.mock.jdbc.MockResultSet;
import com.simpledivelog.external.LocationsResultSetMapper;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:/test/resources/applicationContextTests-Integration.xml" })
//@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class LocationResultSetMapperITest {

	private static Logger LOG = LoggerFactory.getLogger(LocationResultSetMapperITest.class);
	
	@Ignore
	@Test
	public void test_map_dive_entry () {
		System.out.println("@@@");
		LocationsResultSetMapper mapper = LocationsResultSetMapper.getInstance();

		MockResultSet rs = new MockResultSet("myMock");
		rs.addColumn("country_name", new String[]{"country_name"});
		rs.addColumn("name", new String[]{"Location Name"});
		
		rs.addColumn("country_name", new String[]{"country_name"});
		rs.addColumn("name", new String[]{"Different Location Name"});
		
		// make sure to move the cursor to the first row
		try
		{
		  rs.next();
		}
		catch (SQLException sqle)
		{
		  LOG.info("unable to move resultSet");
		}
		
		List<String> locs = mapper.mapStringLocationsList((ResultSet)rs);
		
		assertNotNull(locs);
		assertEquals(locs.size(), 2);
		assertEquals(locs.get(0), "Location Name");
		assertEquals(locs.get(1), "Different Location Name");
		assertNotSame(locs.get(0), locs.get(1));
		
		System.out.println(locs);
	}
}
