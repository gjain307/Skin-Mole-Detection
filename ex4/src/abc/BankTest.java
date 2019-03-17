


/*
 * Copyright (c) 2007 Klaus Dorer
 * Hochschule Offenburg
 */
package abc;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit Tests for Bank class
 * @author klaus
 */
public class BankTest
{
	Bank testee;

	CheckingAccount checking1;

	CheckingAccount checking2;

	SavingAccount savings1;

	SavingAccount savings2;
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		testee = new Bank();

		Date created = new Date(3, 4, 2006);
		Person owner1 = new Person("Marc", 18);
		checking1 = new CheckingAccount(created, owner1, -2000.0f);
		testee.addAccount(checking1);

		Person owner2 = new Person("Nancy", 22);
		checking2 = new CheckingAccount(created, owner2, -1000.0f);
		testee.addAccount(checking2);

		// we need this extra instance to have two objects with same content
		String testName = new String("Marc");
		Person owner3 = new Person(testName, 18);
		savings1 = new SavingAccount(created, owner3);
		testee.addAccount(savings1);

		Date createdOld = new Date(1, 1, 1999);
		Person owner4 = new Person("Doreen", 55);
		savings2 = new SavingAccount(createdOld, owner4);
		testee.addAccount(savings2);
	}

	/**
	 * Test method for
	 * {@link bank.exercise2.Bank#transfer(java.lang.Long, java.lang.Long, float)}.
	 */
	@Test
	public void testTransferToCheckingsOk()
	{
		// positive test
		assertEquals("Transfer between two checking accounts should be possible",
				true, testee.transfer(checking1.getAccountID(), checking2
						.getAccountID(), 50));
		assertEquals("Unexpected balance", -50.0f, checking1.getBalance(), 0.0001);
		assertEquals("Unexpected balance", 50.0f, checking2.getBalance(), 0.0001);
	}

	/**
	 * Test method for
	 * {@link bank.exercise2.Bank#transfer(java.lang.Long, java.lang.Long, float)}.
	 */
	@Test
	public void testTransferFromCheckingsTooMuchMoney()
	{
		// negative test
		assertEquals("Do not transfer if minimum balance is violated", false,
				testee.transfer(checking1.getAccountID(), checking2.getAccountID(),
						2001.0f));
		assertEquals("Unexpected balance", 0.0f, checking1.getBalance(), 0.0001);
		assertEquals("Unexpected balance", 0.0f, savings1.getBalance(), 0.0001);
	}

	/**
	 * Test method for
	 * {@link bank.exercise2.Bank#transfer(java.lang.Long, java.lang.Long, float)}.
	 */
	@Test
	public void testTransferFromSavingsTooMuchMoney()
	{
		// negative test
		assertEquals("Do not transfer if minimum balance is violated", false,
				testee.transfer(savings1.getAccountID(), checking1.getAccountID(),
						1.0f));
		assertEquals("Unexpected balance", 0.0f, savings1.getBalance(), 0.0001);
		assertEquals("Unexpected balance", 0.0f, checking1.getBalance(), 0.0001);
	}

	/**
	 * Test method for
	 * {@link bank.exercise2.Bank#transfer(java.lang.Long, java.lang.Long, float)}.
	 */
	@Test
	public void testTransferToCheckingsInvalidAmount()
	{
		// negative test
		assertEquals("Do not transfer if amount is invalid", false, testee
				.transfer(checking1.getAccountID(), checking2.getAccountID(),
						-10000.0f));
		assertEquals("Unexpected balance", 0.0f, checking1.getBalance(), 0.0001);
		assertEquals("Unexpected balance", 0.0f, savings1.getBalance(), 0.0001);
	}

	/**
	 * Test method for
	 * {@link bank.exercise2.Bank#transfer(java.lang.Long, java.lang.Long, float)}.
	 */
	@Test
	public void testTransferFromSavingsSameOwner()
	{
		// positive test
		savings1.deposit(100);
		assertEquals(
				"Transfer from savings accounts should be possible for same owner",
				true, testee.transfer(savings1.getAccountID(), checking1
						.getAccountID(), 40));
		assertEquals("Unexpected balance", 60.0f, savings1.getBalance(), 0.0001);
		assertEquals("Unexpected balance", 40.0f, checking1.getBalance(), 0.0001);
	}

	/**
	 * Test method for
	 * {@link bank.exercise2.Bank#transfer(java.lang.Long, java.lang.Long, float)}.
	 */
	@Test
	public void testTransferFromSavingsOtherOwner()
	{
		// negative test
		savings1.deposit(100);
		assertEquals(
				"Transfer from savings accounts should not be possible for different owner",
				false, testee.transfer(savings1.getAccountID(), checking2
						.getAccountID(), 40));
		assertEquals("Unexpected balance", 100.0f, savings1.getBalance(), 0.0001);
		assertEquals("Unexpected balance", 0.0f, checking2.getBalance(), 0.0001);
	}

	/**
	 * Test method for
	 * {@link bank.exercise2.Bank#transfer(java.lang.Long, java.lang.Long, float)}.
	 */
	@Test
	public void testTransferFromSavingsFixed()
	{
		// negative test
		savings1.deposit(100);
		savings1.fixAccount(new Date(1, 1, 2010));
		assertEquals(
				"Transfer from savings accounts should not be possible if fixed",
				false, testee.transfer(savings1.getAccountID(), checking1
						.getAccountID(), 40));
		assertEquals("Unexpected balance", 100.0f, savings1.getBalance(), 0.0001);
		assertEquals("Unexpected balance", 0.0f, checking1.getBalance(), 0.0001);
	}

	/**
	 * Test method for
	 * {@link bank.exercise2.Bank#transfer(java.lang.Long, java.lang.Long, float)}.
	 */
	@Test
	public void testTransferToSavingsOtherOwner()
	{
		// negative test
		assertEquals(
				"Transfer to savings accounts should not be possible for different owner",
				false, testee.transfer(checking2.getAccountID(), savings1
						.getAccountID(), 40));
		assertEquals("Unexpected balance", 0.0f, checking2.getBalance(), 0.0001);
		assertEquals("Unexpected balance", 0.0f, savings1.getBalance(), 0.0001);
	}

	/**
	 * Test method for
	 * {@link bank.exercise2.Bank#transfer(java.lang.Long, java.lang.Long, float)}.
	 */
	@Test
	public void testTransferToSavingsFixed()
	{
		// negative test
		savings1.fixAccount(new Date(1, 1, 2010));
		assertEquals(
				"Transfer to savings accounts should not be possible if fixed",
				false, testee.transfer(checking1.getAccountID(), savings1
						.getAccountID(), 40));
		assertEquals("Unexpected balance", 0.0f, checking1.getBalance(), 0.0001);
		assertEquals("Unexpected balance", 0.0f, savings1.getBalance(), 0.0001);
	}

	/**
	 * Test method for
	 * {@link bank.exercise2.Bank#transfer(java.lang.Long, java.lang.Long, float)}.
	 */
	@Test
	public void testTransferToSavingsTooOld()
	{
		// negative test
		assertEquals(
				"Transfer to savings accounts should not be possible if too old",
				false, testee.transfer(checking1.getAccountID(), savings2
						.getAccountID(), 40));
		assertEquals("Unexpected balance", 0.0f, checking1.getBalance(), 0.0001);
		assertEquals("Unexpected balance", 0.0f, savings2.getBalance(), 0.0001);
	}

	/**
	 * Test method for
	 * {@link bank.exercise2.Bank#transfer(java.lang.Long, java.lang.Long, float)}.
	 */
	@Test
	public void testTransferFromSavingsOld()
	{
		// positive test
		savings2.deposit(100);
		Date created = new Date(1, 1, 2008);
		Person owner4 = new Person("Doreen", 55);
		SavingAccount savings3 = new SavingAccount(created, owner4);
		testee.addAccount(savings3);

		assertEquals(
				"Transfer from savings accounts should be possible even if old",
				true, testee.transfer(savings2.getAccountID(), savings3
						.getAccountID(), 40));
		assertEquals("Unexpected balance", 60.0f, savings2.getBalance(), 0.0001);
		assertEquals("Unexpected balance", 40.0f, savings3.getBalance(), 0.0001);
	}

	/**
	 * Test method for
	 * {@link bank.exercise2.Bank#transfer(java.lang.Long, java.lang.Long, float)}.
	 */
	@Test
	public void testTransferIdenticalAccounts()
	{
		// negative test
		savings1.deposit(100);
		assertEquals(
				"Transfer to savings accounts should not be possible if too old",
				false, testee.transfer(savings1.getAccountID(), savings1
						.getAccountID(), 40));
		assertEquals("Unexpected balance", 100.0f, savings1.getBalance(), 0.0001);
		assertEquals("Unexpected balance", 0.0f, savings2.getBalance(), 0.0001);
	}

	/**
	 * Test method for
	 * {@link bank.exercise2.Bank#transfer(java.lang.Long, java.lang.Long, float)}.
	 */
	@Test
	public void testTransferNonExistingAccounts()
	{
		// negative test
		assertEquals("Transfer should detect that accounts are not there", false,
				testee.transfer(-1l, 1000l, 40));
	}

	/**
	 * Check if number of accounts is properly returned
	 */
	@Test
	public void testNumberOfAccounts()
	{
		assertEquals("Unexpected number of accounts", 4, testee
				.getNumberOfAccounts());
	}

	/**
	 * Checks if someone uses static account list or counter
	 */
	@Test
	public void testWorksWith2Banks()
	{
		Bank bank2 = new Bank();
		Date created = new Date(3, 4, 2006);
		Person owner1 = new Person("Math", 19);
		checking1 = new CheckingAccount(created, owner1, -2000.0f);
		bank2.addAccount(checking1);
		created = new Date(4, 5, 2007);
		owner1 = new Person("Serena", 35);
		checking2 = new CheckingAccount(created, owner1, -2000.0f);
		bank2.addAccount(checking2);

		assertEquals("Unexpected number of accounts", 4, testee
				.getNumberOfAccounts());
		assertEquals("Unexpected number of accounts", 2, bank2
				.getNumberOfAccounts());

		// test a transfer to check if access to the accounts is proper
		assertEquals("Transfer between two checking accounts should be possible",
				true, bank2.transfer(checking1.getAccountID(), checking2
						.getAccountID(), 50));
		assertEquals("Unexpected balance", -50.0f, checking1.getBalance(), 0.0001);
		assertEquals("Unexpected balance", 50.0f, checking2.getBalance(), 0.0001);
	}
}
