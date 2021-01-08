package com.cross.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;

import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cross.utils.ConnectionUtil;
import com.cross.utils.HibernateUtil;

public class HibernateUtilTest {
	
	@DisplayName("Simple hibernate connectivity test")
	@Test
	public void hibernateConnectivity() {
		HibernateUtil hu = HibernateUtil.getHibernateUtil();
		Session session  = hu.getSession();
		assertTrue( session != null );
		System.out.println(session);
	}
}
