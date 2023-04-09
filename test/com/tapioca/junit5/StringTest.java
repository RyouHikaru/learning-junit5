package com.tapioca.junit5;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class StringTest {
	private String str;

	/**
	 * Called before all test cases are initialized
	 */
	@BeforeAll
	static void beforeAll() {
		System.out.println("Initialize connection to Database");
	}

	/**
	 * Called after all test cases are completed
	 */
	@AfterAll
	static void afterAll() {
		System.out.println("Close connection to Database");
	}

	/**
	 * Called before each tests
	 * @param info
	 */
	@BeforeEach
	void beforeEach(TestInfo info) {
		System.out.println("Initialize Test Data for " + info.getDisplayName());
	}

	/**
	 * Called after each tests
	 * @param info
	 */
	@AfterEach
	void afterEach(TestInfo info) {
		System.out.println("Clean up Test Data for " + info.getDisplayName());
	}

	@Test
	@Disabled // Ignore test case
	void lengthBasic() {
		// Write test code
		// Invoke method
		// Checks in place (Assertions)
		int actualLength = "ABCD".length();
		int expectedLength = 4;
		assertEquals(expectedLength, actualLength);
	}
	
	/**
	 * Time the execution
	 */
	@Test
	void performanceTest() {
		assertTimeout(Duration.ofSeconds(5), () -> {
			for (int i = 0; i < 100000; i++) {
				int j = i;
				System.out.println(j);
			}
		});
	}
	
	@Test
	void lengthGreaterThanZero() {
		assertTrue("ABCD".length() > 0);
		assertTrue("ABC".length() > 0);
		assertTrue("A".length() > 0);
		assertTrue("DEF".length() > 0);
	}
	
	/**
	 * Parameterized test using Value source
	 * @param str
	 */
	@ParameterizedTest
	@ValueSource(strings = {"ABC", "ABC", "A", "DEF"})
	void lengthGreaterThanZeroParameterizedValue(String str) {
		assertTrue(str.length() > 0);
	}
	
	/**
	 * Parameterized test using CSV source
	 * @param word
	 * @param capitalizedWord
	 */
	@ParameterizedTest
	@CsvSource(value = {"abcd, ABCD", "abc, ABC", "'',''", "abcde, ABCDE"})
	void toUpperCaseParameterizedCsv(String word, String capitalizedWord) {
		assertEquals(capitalizedWord, word.toUpperCase());
	}

	/**
	 * Testing exceptions and specifying name to display in execution
	 */
	@Test
	@DisplayName("When string is null, throw an exception")
	void lengthException() {
		String str = null;
		assertThrows(NullPointerException.class, () -> {
			str.length();
		});
	}
	
	/**
	 * Parameterized test with arguments
	 * @param word
	 * @param expectedLength
	 */
	@ParameterizedTest(name = "{0} length is {1}")
	@CsvSource(value = {"abcd, 4", "abc, 3", "'', 0", "abcde, 5"})
	void lengthCsv(String word, int expectedLength) {
		assertEquals(expectedLength, word.length());
	}

	/**
	 * Boolean test
	 */
	@Test
	void toUpperCase() {
		String str = "abcd";
		String result = str.toUpperCase();
		assertNotNull(result);
//		assertNull(result);
		assertEquals("ABCD", result);
	}

	/**
	 * Repeated test given n times
	 */
	@Test
	@RepeatedTest(2)
	void contains() {
		assertFalse("abcdefgh".contains("ijk"));
	}

	/**
	 * Array test
	 */
	@Test
	void split() {
		String str = "abc def ghi";
		String[] actualResult = str.split(" ");
		String[] expectedResult = new String[] { "abc", "def", "ghi" };
		assertArrayEquals(expectedResult, actualResult);
	}
	
	@Nested // Used for grouping related test cases
	class EmptyStringTest {
		
		@BeforeEach
		void setToEmpty() {
			str = "";
		}
		
		@Test
		@DisplayName("Length should be zero")
		void lengthIsZero() {
			assertEquals(0, str.length());
		}
		
		@Test
		@DisplayName("Upper case should be empty")
		void uppercaseIsEmpty() {
			assertEquals("", str.toUpperCase());
		}
	}

}
