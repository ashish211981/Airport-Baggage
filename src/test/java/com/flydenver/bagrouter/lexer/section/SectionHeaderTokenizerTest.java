

package com.flydenver.bagrouter.lexer.section;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.flydenver.bagrouter.lexer.section.SectionHeaderTokenizer.SectionHeaderToken;

/**
 * Tests for section header tokens.
 */
public class SectionHeaderTokenizerTest {
	private final static String sectionStart = "#";
	private final static String sectionHeader = "Section";
	private final static String sectionSplit = ":";

	private final static String conveyorSystem = "# Section: Conveyor System";
	private final static String departures = "# Section: Departures";
	private final static String bags = "# Section: Bags";

	//=[ good tests ]==========================================================

	@Test
	public void testGoodSectionTokens() {
		assertEquals( SectionHeaderToken.fromIdentifier( sectionStart ), SectionHeaderToken.SECTION_START );
		assertEquals( SectionHeaderToken.fromIdentifier( sectionHeader ), SectionHeaderToken.SECTION_HEADER );
		assertEquals( SectionHeaderToken.fromIdentifier( sectionSplit ), SectionHeaderToken.SECTION_SPLIT );
	}

	@Test
	public void testGoodSectionHeader() {
		SectionHeaderTokenizer tokenizer = SectionHeaderTokenizer.checkLineForSectionHeader( conveyorSystem );
		assertNotNull( tokenizer );
		assertEquals( tokenizer.getSectionHeader(), "Conveyor System" );

		tokenizer = SectionHeaderTokenizer.checkLineForSectionHeader( departures );
		assertNotNull( tokenizer );
		assertEquals( tokenizer.getSectionHeader(), "Departures" );

		tokenizer = SectionHeaderTokenizer.checkLineForSectionHeader( bags );
		assertNotNull( tokenizer );
		assertEquals( tokenizer.getSectionHeader(), "Bags" );

		tokenizer = SectionHeaderTokenizer.checkLineForSectionHeader( "# Section  : Another Longer Section Header With  More   Spaces    " );
		assertNotNull( tokenizer );
		assertEquals( tokenizer.getSectionHeader(), "Another Longer Section Header With  More   Spaces" );
	}

	@Test
	public void testLeadingAndTrailingSpace() {
		assertEquals( SectionHeaderTokenizer.checkLineForSectionHeader( " " + conveyorSystem + " " ).getSectionHeader(), "Conveyor System" );
		assertEquals( SectionHeaderTokenizer.checkLineForSectionHeader( " " + departures + " " ).getSectionHeader(), "Departures" );
		assertEquals( SectionHeaderTokenizer.checkLineForSectionHeader( " " + bags + " " ).getSectionHeader(), "Bags" );
	}

	//=[ bad arguments tests ]==========================================================
	
	@Test
	public void testBadHeaders() {
		assertNull( SectionHeaderTokenizer.checkLineForSectionHeader( null ) );
		assertNull( SectionHeaderTokenizer.checkLineForSectionHeader( "" ) );
		assertNull( SectionHeaderTokenizer.checkLineForSectionHeader( " " ) );
		assertNull( SectionHeaderTokenizer.checkLineForSectionHeader( "A random string" ) );
		assertNull( SectionHeaderTokenizer.checkLineForSectionHeader( "Section: Conveyor System" ) );
		assertNull( SectionHeaderTokenizer.checkLineForSectionHeader( "Section:" ) );
	}
	
	@Test
	public void testTypeNull() {
		assertEquals( SectionHeaderToken.fromIdentifier( null ), SectionHeaderToken.UNKNOWN );
		assertEquals( SectionHeaderToken.fromIdentifier( "" ), SectionHeaderToken.UNKNOWN );
	}

	//=[ id tests ]==========================================================

	@Test
	public void testBadTokens() {
		assertEquals( SectionHeaderToken.fromIdentifier( "&" ), SectionHeaderToken.UNKNOWN );
		assertEquals( SectionHeaderToken.fromIdentifier( "*" ), SectionHeaderToken.UNKNOWN );
		assertEquals( SectionHeaderToken.fromIdentifier( "%" ), SectionHeaderToken.UNKNOWN );
	}

}
